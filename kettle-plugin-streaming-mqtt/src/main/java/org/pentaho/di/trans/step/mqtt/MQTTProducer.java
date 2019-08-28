/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2018 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.step.mqtt;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.util.serialization.BaseSerializingMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Optional.ofNullable;
import static org.pentaho.di.i18n.BaseMessages.getString;

public class MQTTProducer extends BaseStep implements StepInterface {
  private static Class<?> PKG = MQTTProducer.class;

  private MQTTProducerMeta meta;

  Supplier<MqttClient> client = Suppliers.memoize( this::connectToClient );

  /**
   * This is the base step that forms that basis for all steps. You can derive from this class to implement your own
   * steps.
   *
   * @param stepMeta          The StepMeta object to run.
   * @param stepDataInterface the data object to store temporary data, database connections, caches, result sets,
   *                          hashtables etc.
   * @param copyNr            The copynumber for this step.
   * @param transMeta         The TransInfo of which the step stepMeta is part of.
   * @param trans             The (running) transformation to obtain information shared among the steps.
   */
  public MQTTProducer( StepMeta stepMeta,
                       StepDataInterface stepDataInterface, int copyNr,
                       TransMeta transMeta, Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override
  public boolean init( StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface ) {
    boolean isInitalized = super.init( stepMetaInterface, stepDataInterface );
    BaseSerializingMeta serializingMeta = (BaseSerializingMeta) stepMetaInterface;
    meta = (MQTTProducerMeta) serializingMeta.withVariables( this ); // handle variable substitution up-front

    List<CheckResultInterface> remarks = new ArrayList<>();
    meta.check(
      remarks, getTransMeta(), meta.getParentStepMeta(),
      null, null, null, null, //these parameters are not used inside the method
      variables, getRepository(), getMetaStore() );
    boolean errorsPresent =
      remarks.stream().filter( result -> result.getType() == CheckResultInterface.TYPE_RESULT_ERROR )
        .peek( result -> logError( result.getText() ) )
        .count() > 0;
    return !errorsPresent && isInitalized;
  }

  @Override
  public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
    Object[] row = getRow();

    if ( null == row ) {
      setOutputDone();
      stopMqttClient();
      return false;
    }
    try {
      client.get()  // client is memoized, loaded on first use
        .publish( getTopic( row ), getMessage( row ) );

      incrementLinesOutput();
      putRow( getInputRowMeta(), row ); // copy row to possible alternate rowset(s).

      if ( checkFeedback( getLinesRead() ) ) {
        if ( log.isBasic() ) {
          logBasic( getString( PKG, "MQTTProducer.Log.LineNumber" ) + getLinesRead() );
        }
      }
    } catch ( MqttException e ) {
      logError( getString( PKG, "MQTTProducer.Error.QOSNotSupported", meta.qos ) );
      logError( e.getMessage(), e );
      setErrors( 1 );
      stopAll();
      return false;
    } catch ( RuntimeException re ) {
      stopAll();
      logError( re.getMessage(), re );
      return false;
    }
    return true;
  }

  private MqttClient connectToClient() {
    logDebug( "Publishing using a quality of service level of " + environmentSubstitute( meta.qos ) );
    try {
      return
        MQTTClientBuilder.builder()
          .withBroker( this.meta.mqttServer )
          .withClientId( meta.clientId )
          .withQos( meta.qos )
          .withStep( this )
          .withUsername( meta.username )
          .withPassword( meta.password )
          .withSslConfig( meta.getSslConfig() )
          .withIsSecure( meta.useSsl )
          .withKeepAliveInterval( meta.keepAliveInterval )
          .withMaxInflight( meta.maxInflight )
          .withConnectionTimeout( meta.connectionTimeout )
          .withCleanSession( meta.cleanSession )
          .withStorageLevel( meta.storageLevel )
          .withServerUris( meta.serverUris )
          .withMqttVersion( meta.mqttVersion )
          .withAutomaticReconnect( meta.automaticReconnect )
          .buildAndConnect();
    } catch ( MqttException e ) {
      throw new RuntimeException( e );
    }
  }

  private MqttMessage getMessage( Object[] row ) throws KettleStepException {
    MqttMessage mqttMessage = new MqttMessage();
    try {
      mqttMessage.setQos( Integer.parseInt( meta.qos ) );
    } catch ( NumberFormatException e ) {
      throw new KettleStepException(
        getString( PKG, "MQTTProducer.Error.QOS", environmentSubstitute( meta.qos ) ) );
    }
    String fieldAsString = getFieldAsString( row, meta.messageField );
    mqttMessage.setPayload( fieldAsString.getBytes( defaultCharset() ) );
    return mqttMessage;
  }

  /**
   * Retrieves the topic, either a raw string, or a field value if meta.topicInField==true
   */
  private String getTopic( Object[] row ) {
    String topic;
    if ( meta.topicInField ) {
      topic = getFieldAsString( row, meta.topic );
    } else {
      topic = meta.topic;
    }
    return topic;
  }

  private String getFieldAsString( Object[] row, String field ) {
    int messageFieldIndex = getInputRowMeta().indexOfValue( field );
    checkArgument( messageFieldIndex > -1, getString( PKG, "MQTTProducer.Error.FieldNotFound", field ) );
    return ofNullable( ( row[ messageFieldIndex ] ).toString() ).orElse( "" );
  }

  @Override public void stopRunning( StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface )
    throws KettleException {
    stopMqttClient();
    super.stopRunning( stepMetaInterface, stepDataInterface );
  }

  private void stopMqttClient() {
    try {
      // Check if connected so subsequent calls does not produce an already stopped exception
      if ( null != client.get() && client.get().isConnected() ) {
        client.get().disconnect();
        client.get().close();
      }
    } catch ( MqttException e ) {
      logError( e.getMessage() );
    }
  }
}
