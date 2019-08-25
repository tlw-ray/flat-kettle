/*!
 * HITACHI VANTARA PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2016 - 2017 Hitachi Vantara. All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Hitachi Vantara and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Hitachi Vantara and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Hitachi Vantara is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Hitachi Vantara,
 * explicitly covering such access.
 */
package org.pentaho.agilebi.modeler.models.annotations;

import org.pentaho.agilebi.modeler.ModelerException;
import org.pentaho.agilebi.modeler.ModelerWorkspace;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Document;

/**
 * @author Rowell Belen
 */
public class MockAnnotationType extends AnnotationType {

  @ModelProperty( id = "i" )
  private int i;

  @ModelProperty( id = "d" )
  private double d;

  @ModelProperty( id = "f" )
  private float f;

  @ModelProperty( id = "l" )
  private long l;

  @ModelProperty( id = "s" )
  private short s;

  public int getI() {
    return i;
  }

  public void setI( int i ) {
    this.i = i;
  }

  public double getD() {
    return d;
  }

  public void setD( double d ) {
    this.d = d;
  }

  public float getF() {
    return f;
  }

  public void setF( float f ) {
    this.f = f;
  }

  public long getL() {
    return l;
  }

  public void setL( long l ) {
    this.l = l;
  }

  public short getS() {
    return s;
  }

  public void setS( short s ) {
    this.s = s;
  }

  @Override
  public boolean apply( ModelerWorkspace workspace, final IMetaStore metaStore ) {
    return true;
  }

  @Override
  public ModelAnnotation.Type getType() {
    return null;
  }

  @Override public String getSummary() {
    return "";
  }

  @Override
  public boolean apply( Document schema ) throws ModelerException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override public String getName() {
    return "";
  }

  @Override
  public void validate() throws ModelerException {
  }

  @Override
  public String getField() {
    return null;
  }
}
