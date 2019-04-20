/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Hitachi Vantara : http://www.pentaho.com
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

package org.pentaho.di.osgi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.pentaho.di.osgi.service.notifier.DelayedServiceNotifierListener;
import org.pentaho.osgi.api.IKarafBlueprintWatcher;
import org.pentaho.osgi.api.IKarafFeatureWatcher;
import org.pentaho.platform.servicecoordination.api.IPhasedLifecycleEvent;
import org.pentaho.platform.servicecoordination.api.IPhasedLifecycleManager;
import org.pentaho.platform.servicecoordination.impl.BaseCountdownLatchLifecycleManager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nbaker on 9/23/15.
 */
public class KarafLifecycleListenerTest {
  private int timeout;
  private OSGIPluginTracker osgiPluginTracker;
  private KarafLifecycleListener karafLifecycleListener;
  private BundleContext bundleContext;
  private ServiceReference<IKarafFeatureWatcher> featureWatcherServiceReference;
  private IKarafFeatureWatcher iKarafFeatureWatcher;
  private ServiceReference<IKarafBlueprintWatcher> blueprintWatcherServiceReference;
  private IKarafBlueprintWatcher iKarafBlueprintWatcher;

  @Before
  public void setup() {
    timeout = 500;
    osgiPluginTracker = mock( OSGIPluginTracker.class );
    karafLifecycleListener = new KarafLifecycleListener( timeout, osgiPluginTracker );

    bundleContext = mock( BundleContext.class );
    karafLifecycleListener.setBundleContext( bundleContext );

    featureWatcherServiceReference = mock( ServiceReference.class );
    iKarafFeatureWatcher = mock( IKarafFeatureWatcher.class );
    when( bundleContext.getService( featureWatcherServiceReference ) ).thenReturn( iKarafFeatureWatcher );

    blueprintWatcherServiceReference = mock( ServiceReference.class );
    iKarafBlueprintWatcher = mock( IKarafBlueprintWatcher.class );
    when( bundleContext.getService( blueprintWatcherServiceReference ) ).thenReturn( iKarafBlueprintWatcher );
  }

  @Test
  public void testSatisfied() throws Exception {

    final BaseCountdownLatchLifecycleManager<KettleLifecycleEvent> lifecycleManager = new BaseCountdownLatchLifecycleManager<KettleLifecycleEvent>() {
      @Override protected KettleLifecycleEvent getNotificationObject() {
        return KettleLifecycleEvent.INIT;
      }
    };

    KarafLifecycleListener listener = new KarafLifecycleListener( 500 );
    lifecycleManager.addLifecycleListener( listener );
    lifecycleManager.advance();
    assertTrue( lifecycleManager.getPhase() == 1 );

    Thread t = new Thread( new Runnable() {
      @Override public void run() {
        try {
          lifecycleManager.advance();
        } catch ( InterruptedException e ) {
          fail( "error in manager" );
          e.printStackTrace();
        }
      }
    });
    t.start();

    int fatalCount = 0;
    do {
      Thread.sleep( 10 );
    } while ( t.getState() != Thread.State.WAITING && ++fatalCount < 10 );
    assertTrue( t.getState() == Thread.State.WAITING );
    assertEquals( 1, lifecycleManager.getPhase() ); // still at 1 because the first is blocked


    // Now lets satisfy the requirements, the listener should release now
    IKarafFeatureWatcher featureWatcher = mock( IKarafFeatureWatcher.class );
    IKarafBlueprintWatcher blueprintWatcher = mock( IKarafBlueprintWatcher.class );

    BundleContext bundleContext = mock( BundleContext.class );
    ServiceReference reference = mock( ServiceReference.class );
    ServiceReference reference2 = mock( ServiceReference.class );

    when( bundleContext.getServiceReference( IKarafFeatureWatcher.class ) ).thenReturn( reference );
    when( bundleContext.getService( reference )).thenReturn( featureWatcher );

    when( bundleContext.getServiceReference( IKarafBlueprintWatcher.class ) ).thenReturn( reference2 );
    when( bundleContext.getService( reference2 )).thenReturn( blueprintWatcher );

    listener.setBundleContext( bundleContext );
    t.join();
    assertEquals( 2, lifecycleManager.getPhase() );
  }

  @Test
  public void testTimeoutRelease() throws Exception {

    final BaseCountdownLatchLifecycleManager<KettleLifecycleEvent> lifecycleManager = new BaseCountdownLatchLifecycleManager<KettleLifecycleEvent>() {
      @Override protected KettleLifecycleEvent getNotificationObject() {
        return KettleLifecycleEvent.INIT;
      }
    };

    KarafLifecycleListener listener = new KarafLifecycleListener( 500 );
    lifecycleManager.addLifecycleListener( listener );
    lifecycleManager.advance();
    assertTrue( lifecycleManager.getPhase() == 1 );

    Thread t = new Thread( new Runnable() {
      @Override public void run() {
        try {
          lifecycleManager.advance();
        } catch ( InterruptedException e ) {
          fail( "error in manager" );
          e.printStackTrace();
        }
      }
    });
    t.start();

    int fatalCount = 0;
    do {
      Thread.sleep( 10 );
    } while ( t.getState() != Thread.State.WAITING && ++fatalCount < 10 );
    assertTrue( t.getState() == Thread.State.WAITING );
    assertEquals( 1, lifecycleManager.getPhase() ); // still at 1 because the first is blocked

    // At this point the KarafLifecycleListener will be blocked as we're never going to satisfy it's requirements.
    // The Timeout thread within it should release the event after the configured timeout. We'll join and wait to make
    // sure that happens
    t.join();
    assertEquals( 2, lifecycleManager.getPhase() );
  }

  @Test
  public void testDelayedServiceNotifierListener() {
    IPhasedLifecycleEvent<KettleLifecycleEvent> iPhasedLifecycleEvent = mock( IPhasedLifecycleEvent.class );
    when( iPhasedLifecycleEvent.getNotificationObject() ).thenReturn( KettleLifecycleEvent.INIT );

    when( bundleContext.getServiceReference( IKarafFeatureWatcher.class ) ).thenReturn( featureWatcherServiceReference );
    when( bundleContext.getServiceReference( IKarafBlueprintWatcher.class ) ).thenReturn( blueprintWatcherServiceReference );

    when( osgiPluginTracker.getOutstandingServiceNotifierListeners() ).thenReturn( 1 ).thenReturn( 0 );
    karafLifecycleListener.onPhaseChange( iPhasedLifecycleEvent );
    ArgumentCaptor<DelayedServiceNotifierListener> delayedServiceNotifierListenerArgumentCaptor =
      ArgumentCaptor.forClass( DelayedServiceNotifierListener.class );
    verify( osgiPluginTracker, timeout( (int) TimeUnit.SECONDS.toMillis( 5 ) ) ).addDelayedServiceNotifierListener( delayedServiceNotifierListenerArgumentCaptor.capture() );
    verify( iPhasedLifecycleEvent, never() ).accept();
    delayedServiceNotifierListenerArgumentCaptor.getValue().onRun( null, null );
    verify( iPhasedLifecycleEvent ).accept();
    delayedServiceNotifierListenerArgumentCaptor.getValue().onRun( null, null );
    verify( iPhasedLifecycleEvent, times( 1 ) ).accept();
  }
}