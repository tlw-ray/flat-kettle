/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2019 by Hitachi Vantara : http://www.pentaho.com
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

package org.pentaho.di.trans.steps.excelwriter;

import com.google.common.io.Files;
import org.apache.commons.vfs2.FileObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LoggingObjectInterface;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaBigNumber;
import org.pentaho.di.core.row.value.ValueMetaBinary;
import org.pentaho.di.core.row.value.ValueMetaDate;
import org.pentaho.di.core.row.value.ValueMetaInteger;
import org.pentaho.di.core.row.value.ValueMetaInternetAddress;
import org.pentaho.di.core.row.value.ValueMetaNumber;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.core.row.value.ValueMetaTimestamp;
import org.pentaho.di.trans.step.StepInjectionMetaEntry;
import org.pentaho.di.trans.steps.mock.StepMockHelper;
import org.pentaho.di.utils.TestUtils;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExcelWriterStepTest {

  private static final String SHEET_NAME = "Sheet1";
  private static final String XLSX = "xlsx";
  private static final String DOT_XLSX = '.' + XLSX;
  private static final String EMPTY_STRING = "";

  private Workbook wb;
  private StepMockHelper<ExcelWriterStepMeta, ExcelWriterStepData> mockHelper;
  private ExcelWriterStep step;

  private ExcelWriterStepMeta stepMeta;
  private ExcelWriterStepMeta metaMock;
  private ExcelWriterStepData dataMock;

  @Before
  public void setUp() throws Exception {
    String path = TestUtils.createRamFile( getClass().getSimpleName() + "/testXLSProtect.xls" );
    FileObject xlsFile = TestUtils.getFileObject( path );
    wb = createWorkbook( xlsFile );
    mockHelper = new StepMockHelper<>( "Excel Writer Test", ExcelWriterStepMeta.class, ExcelWriterStepData.class );
    when( mockHelper.logChannelInterfaceFactory.create( any(), any( LoggingObjectInterface.class ) ) ).thenReturn(
      mockHelper.logChannelInterface );
    step = spy( new ExcelWriterStep(
      mockHelper.stepMeta, mockHelper.stepDataInterface, 0, mockHelper.transMeta, mockHelper.trans ) );

    stepMeta = new ExcelWriterStepMeta();
    metaMock = mock( ExcelWriterStepMeta.class );
    dataMock = mock( ExcelWriterStepData.class );
  }

  @After
  public void cleanUp() {
    mockHelper.cleanUp();
  }

  @Test
  public void testProtectSheet() throws Exception {
    step.protectSheet( wb.getSheet( SHEET_NAME ), "aa" );
    assertTrue( wb.getSheet( SHEET_NAME ).getProtect() );
  }

  @Test
  public void testMaxSheetNameLength() {

    // Return a 32 character name
    when( mockHelper.initStepMetaInterface.getSheetname() ).thenReturn( "12345678901234567890123456789012" );
    step.init( mockHelper.initStepMetaInterface, mockHelper.initStepDataInterface );

    try {
      step.prepareNextOutputFile();
      // An exception should have been thrown!
      fail();
    } catch ( KettleException e ) {
      String content = e.getMessage();

      // We expected this error message, the sheet name is too long for Excel
      assertTrue( content.contains( "12345678901234567890123456789012" ) );
    }
  }

  @Test
  public void testTopLevelMetadataEntries() throws Exception {

    List<StepInjectionMetaEntry> entries =
      stepMeta.getStepMetaInjectionInterface().getStepInjectionMetadataEntries();

    String masterKeys = "FIELDS";

    for ( StepInjectionMetaEntry entry : entries ) {
      String key = entry.getKey();
      assertTrue( masterKeys.contains( key ) );
      masterKeys = masterKeys.replace( key, EMPTY_STRING );
    }

    assertEquals( 0, masterKeys.trim().length() );
  }

  @Test
  public void testChildLevelMetadataEntries() throws Exception {

    List<StepInjectionMetaEntry> entries =
      stepMeta.getStepMetaInjectionInterface().getStepInjectionMetadataEntries();

    StepInjectionMetaEntry mappingEntry = null;
    String childKeys =
      "NAME TYPE FORMAT STYLECELL FIELDTITLE TITLESTYLE FORMULA HYPERLINKFIELD CELLCOMMENT COMMENTAUTHOR";

    for ( StepInjectionMetaEntry entry : entries ) {
      String key = entry.getKey();
      if ( key.equals( "FIELDS" ) ) {
        mappingEntry = entry;
        break;
      }
    }

    assertNotNull( mappingEntry );

    List<StepInjectionMetaEntry> fieldAttributes = mappingEntry.getDetails().get( 0 ).getDetails();

    for ( StepInjectionMetaEntry attribute : fieldAttributes ) {
      String key = attribute.getKey();
      assertTrue( childKeys.contains( key ) );
      childKeys = childKeys.replace( key, EMPTY_STRING );
    }

    assertEquals( 0, childKeys.trim().length() );
  }

  @Test
  public void testInjection() throws Exception {

    List<StepInjectionMetaEntry> entries =
      stepMeta.getStepMetaInjectionInterface().getStepInjectionMetadataEntries();

    for ( StepInjectionMetaEntry entry : entries ) {
      switch ( entry.getValueType() ) {
        case ValueMetaInterface.TYPE_STRING:
          entry.setValue( "new_".concat( entry.getKey() ) );
          break;
        case ValueMetaInterface.TYPE_BOOLEAN:
          entry.setValue( Boolean.TRUE );
          break;
        default:
          break;
      }

      if ( !entry.getDetails().isEmpty() ) {

        List<StepInjectionMetaEntry> childEntries = entry.getDetails().get( 0 ).getDetails();
        for ( StepInjectionMetaEntry childEntry : childEntries ) {
          switch ( childEntry.getValueType() ) {
            case ValueMetaInterface.TYPE_STRING:
              childEntry.setValue( "new_".concat( childEntry.getKey() ) );
              break;
            case ValueMetaInterface.TYPE_BOOLEAN:
              childEntry.setValue( Boolean.TRUE );
              break;
            default:
              break;
          }
        }
      }
    }

    stepMeta.getStepMetaInjectionInterface().injectStepMetadataEntries( entries );

    assertEquals( "Cell comment not properly injected... ", "new_CELLCOMMENT",
      stepMeta.getOutputFields()[ 0 ].getCommentField() );
    assertEquals( "Format not properly injected... ", "new_FORMAT", stepMeta.getOutputFields()[ 0 ].getFormat() );
    assertEquals( "Hyperlink not properly injected... ", "new_HYPERLINKFIELD",
      stepMeta.getOutputFields()[ 0 ].getHyperlinkField() );
    assertEquals( "Name not properly injected... ", "new_NAME", stepMeta.getOutputFields()[ 0 ].getName() );
    assertEquals( "Style cell not properly injected... ", "new_STYLECELL",
      stepMeta.getOutputFields()[ 0 ].getStyleCell() );
    assertEquals( "Title not properly injected... ", "new_FIELDTITLE", stepMeta.getOutputFields()[ 0 ].getTitle() );
    assertEquals( "Title style cell not properly injected... ", "new_TITLESTYLE",
      stepMeta.getOutputFields()[ 0 ].getTitleStyleCell() );
    assertEquals( "Type not properly injected... ", 0, stepMeta.getOutputFields()[ 0 ].getType() );
    assertEquals( "Comment author not properly injected... ", "new_COMMENTAUTHOR",
      stepMeta.getOutputFields()[ 0 ].getCommentAuthorField() );
  }

  @Test
  public void testPrepareNextOutputFile() throws Exception {
    assertTrue( step.init( metaMock, dataMock ) );
    File outDir = Files.createTempDir();
    String testFileOut = outDir.getAbsolutePath() + File.separator + "test.xlsx";
    when( step.buildFilename( 0 ) ).thenReturn( testFileOut );
    when( metaMock.isTemplateEnabled() ).thenReturn( true );
    when( metaMock.isStreamingData() ).thenReturn( true );
    when( metaMock.isHeaderEnabled() ).thenReturn( true );
    when( metaMock.getExtension() ).thenReturn( "xlsx" );
    dataMock.createNewFile = true;
    dataMock.realTemplateFileName = getClass().getResource( "template_test.xlsx" ).getFile();
    dataMock.realSheetname = "Sheet1";
    step.prepareNextOutputFile();
  }

  @Test
  public void testWriteUsingTemplateWithFormatting() throws Exception {
    assertTrue( step.init( metaMock, dataMock ) );
    String path = Files.createTempDir().getAbsolutePath() + File.separator + "formatted.xlsx";

    dataMock.fieldnrs = new int[] { 0 };
    dataMock.linkfieldnrs = new int[] { -1 };
    dataMock.commentfieldnrs = new int[] { -1 };
    dataMock.createNewFile = true;
    dataMock.realTemplateFileName = getClass().getResource( "template_with_formatting.xlsx" ).getFile();
    dataMock.realSheetname = "TicketData";
    dataMock.inputRowMeta = mock( RowMetaInterface.class );

    ExcelWriterStepField field = new ExcelWriterStepField();
    ValueMetaInterface vmi = mock( ValueMetaInteger.class );
    doReturn( ValueMetaInterface.TYPE_INTEGER ).when( vmi ).getType();
    doReturn( "name" ).when( vmi ).getName();
    doReturn( 12.0 ).when( vmi ).getNumber( anyObject() );

    doReturn( path ).when( step ).buildFilename( 0 );
    doReturn( true ).when( metaMock ).isTemplateEnabled();
    doReturn( true ).when( metaMock ).isStreamingData();
    doReturn( false ).when( metaMock ).isHeaderEnabled();
    doReturn( "xlsx" ).when( metaMock ).getExtension();
    doReturn( new ExcelWriterStepField[] { field } ).when( metaMock ).getOutputFields();

    doReturn( 10 ).when( dataMock.inputRowMeta ).size();
    doReturn( vmi ).when( dataMock.inputRowMeta ).getValueMeta( anyInt() );

    step.prepareNextOutputFile();

    dataMock.posY = 1;
    dataMock.sheet = spy( dataMock.sheet );
    step.writeNextLine( new Object[] { 12 } );

    verify( dataMock.sheet, times( 0 ) ).createRow( 1 );
    verify( dataMock.sheet ).getRow( 1 );
  }

  @Test
  public void testValueBigNumber() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaBigNumber.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_BIGNUMBER ).when( vmi ).getType();
    doReturn( "value_bigNumber" ).when( vmi ).getName();
    doReturn( Double.MAX_VALUE ).when( vmi ).getNumber( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueBinary() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaBinary.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_BINARY ).when( vmi ).getType();
    doReturn( "value_binary" ).when( vmi ).getName();
    doReturn( "a1b2c3d4e5f6g7h8i9j0" ).when( vmi ).getString( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueBoolean() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaInteger.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_BOOLEAN ).when( vmi ).getType();
    doReturn( "value_bool" ).when( vmi ).getName();
    doReturn( Boolean.FALSE ).when( vmi ).getBoolean( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueDate() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaDate.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_DATE ).when( vmi ).getType();
    doReturn( "value_date" ).when( vmi ).getName();
    doReturn( new Date() ).when( vmi ).getDate( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueInteger() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaInteger.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_INTEGER ).when( vmi ).getType();
    doReturn( "value_integer" ).when( vmi ).getName();
    doReturn( Double.MAX_VALUE ).when( vmi ).getNumber( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueInternetAddress() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaInternetAddress.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_INET ).when( vmi ).getType();
    doReturn( "value_internetAddress" ).when( vmi ).getName();
    doReturn( "127.0.0.1" ).when( vmi ).getString( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueNumber() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaNumber.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_NUMBER ).when( vmi ).getType();
    doReturn( "value_number" ).when( vmi ).getName();
    doReturn( Double.MIN_VALUE ).when( vmi ).getNumber( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueString() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaString.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_STRING ).when( vmi ).getType();
    doReturn( "value_string" ).when( vmi ).getName();
    doReturn( "a_string" ).when( vmi ).getString( anyObject() );

    testValue_Template( vmi, vObj );
  }

  @Test
  public void testValueTimestamp() throws Exception {

    ValueMetaInterface vmi = mock( ValueMetaTimestamp.class, new DefaultAnswerThrowsException() );
    Object vObj = new Object();
    doReturn( ValueMetaInterface.TYPE_INET ).when( vmi ).getType();
    doReturn( "value_timestamp" ).when( vmi ).getName();
    doReturn( "127.0.0.1" ).when( vmi ).getString( vObj );

    testValue_Template( vmi, vObj );
  }

  /**
   * <p>The base for testing if a field of a specific type is correctly handled.</p>
   *
   * @param vmi  {@link ValueMetaInterface}'s instance to be used
   * @param vObj the {@link Object} to be used as the value
   */
  private void testValue_Template( ValueMetaInterface vmi, Object vObj ) throws Exception {
    Object[] vObjArr = { vObj };
    assertTrue( step.init( metaMock, dataMock ) );
    File tempFile = File.createTempFile( XLSX, DOT_XLSX );
    tempFile.deleteOnExit();
    String path = tempFile.getAbsolutePath();

    dataMock.fieldnrs = new int[] { 0 };
    dataMock.linkfieldnrs = new int[] { -1 };
    dataMock.commentfieldnrs = new int[] { -1 };
    dataMock.createNewFile = true;
    dataMock.realSheetname = SHEET_NAME;
    dataMock.inputRowMeta = mock( RowMetaInterface.class );

    doReturn( path ).when( step ).buildFilename( 0 );
    doReturn( false ).when( metaMock ).isTemplateEnabled();
    doReturn( false ).when( metaMock ).isStreamingData();
    doReturn( false ).when( metaMock ).isHeaderEnabled();
    doReturn( XLSX ).when( metaMock ).getExtension();
    ExcelWriterStepField field = new ExcelWriterStepField();
    doReturn( new ExcelWriterStepField[] { field } ).when( metaMock ).getOutputFields();

    doReturn( 1 ).when( dataMock.inputRowMeta ).size();
    doReturn( vmi ).when( dataMock.inputRowMeta ).getValueMeta( anyInt() );

    step.prepareNextOutputFile();

    dataMock.posY = 1;
    dataMock.sheet = spy( dataMock.sheet );
    step.writeNextLine( vObjArr );

    verify( step ).writeField( eq( vObj ), eq( vmi ), eq( field ), any( Row.class ), eq( 0 ), any(), eq( 0 ),
      eq( Boolean.FALSE ) );

    verify( dataMock.sheet ).createRow( anyInt() );
    verify( dataMock.sheet ).getRow( 1 );
  }

  /**
   * <p>Class to be used when mocking an Object so that, if not explicitly specified, any method called will throw an
   * exception.</p>
   */
  private static class DefaultAnswerThrowsException implements Answer<Object> {
    @Override
    public Object answer( InvocationOnMock invocation ) throws Throwable {
      throw new RuntimeException( "This method (" + invocation.getMethod() + ") shouldn't have been called." );
    }
  }

  private Workbook createWorkbook( FileObject file ) throws Exception {
    Workbook wb = null;
    OutputStream os = null;
    try {
      os = file.getContent().getOutputStream();
      wb = new HSSFWorkbook();
      wb.createSheet( SHEET_NAME );
      wb.write( os );
    } finally {
      os.flush();
      os.close();
    }
    return wb;
  }
}
