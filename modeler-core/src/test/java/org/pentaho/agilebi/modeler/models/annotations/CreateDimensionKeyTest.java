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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.pentaho.agilebi.modeler.models.annotations.CreateDimensionKey.FIELD_ID;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.pentaho.agilebi.modeler.ModelerException;
import org.pentaho.di.i18n.LanguageChoice;

public class CreateDimensionKeyTest {

  @Test
  public void testValidateOk() throws Exception {
    CreateDimensionKey createKey = new CreateDimensionKey();
    createKey.setName( "field1" );
    createKey.setDimension( "dim1" );
    createKey.setField( "field1" );
    createKey.validate();
  }

  @Test
  public void testValidateNoDimension() throws Exception {
    CreateDimensionKey createKey = new CreateDimensionKey();
    createKey.setName( "field1" );
    createKey.setField( "field1" );
    try {
      createKey.validate();
      fail( "no exception" );
    } catch ( ModelerException e ) {
      //
    }
  }

  @Test
  public void testValidateNoName() throws Exception {
    CreateDimensionKey createKey = new CreateDimensionKey();
    createKey.setField( "field1" );
    createKey.setDimension( "d" );
    try {
      createKey.validate();
      fail( "no exception" );
    } catch ( ModelerException e ) {
      //
    }
  }

  @Test
  public void testSummary() throws Exception {
    CreateDimensionKey createKey = new CreateDimensionKey();
    createKey.setName( "name" );
    createKey.setField( "field1" );
    createKey.setDimension( "dim1" );
    LanguageChoice.getInstance().setDefaultLocale( Locale.US );
    assertEquals( "field1 is key for dimension dim1", createKey.getSummary() );
    createKey.setField( null );
    assertEquals( "name is key for dimension dim1", createKey.getSummary() );
  }

  @Test
  public void testApplyIsAlwaysTrue() throws Exception {
    assertEquals( true, new CreateDimensionKey().apply( null, null ) );
  }

  @Test
  public void testFieldIsHiddenProperty() throws Exception {
    CreateDimensionKey createDimensionKey = new CreateDimensionKey();
    List<ModelProperty> modelProperties = createDimensionKey.getModelProperties();
    int assertCount = 0;
    for ( ModelProperty modelProperty : modelProperties ) {
      String id = modelProperty.id();
      if ( FIELD_ID.equals( id ) ) {
        assertTrue( modelProperty.hideUI() );
        assertCount++;
      }
    }
    assertEquals( 1, assertCount );
  }
}
