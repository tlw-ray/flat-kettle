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

package org.pentaho.di.trans.steps.terafast;

import org.apache.commons.lang3.SystemUtils;

import junit.framework.TestCase;

public class FastloadControlBuilderIT extends TestCase {

  public void testErrorTablesBuilding() {

    FastloadControlBuilder fastloadControlBuilder = new FastloadControlBuilder();
    String expectedResult =
      "BEGIN LOADING myTable ERRORFILES MyDB.error1,MyDB.error2;" + SystemUtils.LINE_SEPARATOR;
    fastloadControlBuilder.beginLoading( "MyDB", "myTable" );
    assertEquals( expectedResult, fastloadControlBuilder.toString() );

    // Create a new FastloadControlBuilder or we will be appending to the
    // fastloadControlBuilder's previous test
    fastloadControlBuilder = new FastloadControlBuilder();
    expectedResult = "BEGIN LOADING myTable ERRORFILES error1,error2;" + SystemUtils.LINE_SEPARATOR;
    fastloadControlBuilder.beginLoading( null, "myTable" );
    assertEquals( expectedResult, fastloadControlBuilder.toString() );

    // Create a new FastloadControlBuilder or we will be appending to the
    // fastloadControlBuilder's previous test
    fastloadControlBuilder = new FastloadControlBuilder();
    expectedResult = "BEGIN LOADING myTable ERRORFILES error1,error2;" + SystemUtils.LINE_SEPARATOR;
    fastloadControlBuilder.beginLoading( "", "myTable" );
    assertEquals( expectedResult, fastloadControlBuilder.toString() );
  }

}
