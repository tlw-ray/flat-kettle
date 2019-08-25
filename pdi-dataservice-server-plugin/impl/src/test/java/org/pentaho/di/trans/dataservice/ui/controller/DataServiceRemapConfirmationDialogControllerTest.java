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

package org.pentaho.di.trans.dataservice.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Test;
import org.pentaho.di.trans.dataservice.DataServiceMeta;
import org.pentaho.di.trans.dataservice.ui.DataServiceDelegate;
import org.pentaho.di.trans.dataservice.ui.DataServiceRemapConfirmationDialog;
import org.pentaho.ui.xul.swt.tags.SwtDialog;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DataServiceRemapConfirmationDialogControllerTest {
  @Test
  public void testRemapErrorOnNoSteps() {
    DataServiceDelegate delegate = mock( DataServiceDelegate.class );

    DataServiceRemapConfirmationDialogController
        controller =
        spy( new DataServiceRemapConfirmationDialogController( mock( DataServiceMeta.class ),
            new ArrayList<String>(), delegate ) );
    doReturn( mock( SwtDialog.class ) ).when( controller ).getDialog();
    Assert.assertEquals( controller.getAction(), DataServiceRemapConfirmationDialog.Action.CANCEL );
    controller.remap();

    verify( delegate, times( 1 ) ).showRemapNoStepsDialog( any( Shell.class ) );
  }

  @Test
  public void testController() {
    DataServiceDelegate delegate = mock( DataServiceDelegate.class );

    DataServiceRemapConfirmationDialogController
        controller =
        spy( new DataServiceRemapConfirmationDialogController( mock( DataServiceMeta.class ),
            Arrays.asList( "Step1" ), delegate ) );
    doReturn( mock( SwtDialog.class ) ).when( controller ).getDialog();
    Assert.assertEquals( controller.getAction(), DataServiceRemapConfirmationDialog.Action.CANCEL );
    controller.remap();
    Assert.assertEquals( controller.getAction(), DataServiceRemapConfirmationDialog.Action.REMAP );
    controller.delete();
    Assert.assertEquals( controller.getAction(), DataServiceRemapConfirmationDialog.Action.DELETE );
  }

  @Test
  public void testCancel() {
    DataServiceDelegate delegate = mock( DataServiceDelegate.class );
    DataServiceRemapConfirmationDialogController
        controller =
        spy( new DataServiceRemapConfirmationDialogController( mock( DataServiceMeta.class ),
            Arrays.asList( "Step1" ), delegate ) );
    SwtDialog dialog = mock( SwtDialog.class );
    doReturn( dialog ).when( controller ).getElementById( anyString() );
    controller.cancel();
    verify( dialog ).dispose();
  }
}
