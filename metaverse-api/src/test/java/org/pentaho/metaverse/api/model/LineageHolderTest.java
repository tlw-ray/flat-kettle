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

package org.pentaho.metaverse.api.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pentaho.metaverse.api.IMetaverseBuilder;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class LineageHolderTest {

  LineageHolder lineageHolder;

  @Mock
  IExecutionProfile executionProfile;
  @Mock
  IMetaverseBuilder builder;
  @Mock
  Future lineageTask;


  @Test
  public void testEmptyConstructor() throws Exception {
    lineageHolder = new LineageHolder();
    assertNull( lineageHolder.getExecutionProfile() );
    assertNull( lineageHolder.getMetaverseBuilder() );
    assertNull( lineageHolder.getLineageTask() );
    assertNull( lineageHolder.getId() );
  }

  @Test
  public void testSetters() throws Exception {
    lineageHolder = new LineageHolder();

    lineageHolder.setExecutionProfile( executionProfile );
    assertEquals( executionProfile, lineageHolder.getExecutionProfile() );

    lineageHolder.setLineageTask( lineageTask );
    assertEquals( lineageTask, lineageHolder.getLineageTask() );

    lineageHolder.setMetaverseBuilder( builder );
    assertEquals( builder, lineageHolder.getMetaverseBuilder() );

    lineageHolder.setId( "ID" );
    assertEquals( "ID", lineageHolder.getId() );
  }

  @Test
  public void testConstructor() throws Exception {
    lineageHolder = new LineageHolder( executionProfile, builder );
    assertEquals( executionProfile, lineageHolder.getExecutionProfile() );
    assertEquals( builder, lineageHolder.getMetaverseBuilder() );
  }

  @Test
  public void testGetIdFromExecutionProfile() throws Exception {
    when( executionProfile.getPath() ).thenReturn( "profile/path" );
    lineageHolder = new LineageHolder( executionProfile, builder );
    assertEquals( "profile/path", lineageHolder.getId() );
  }
}
