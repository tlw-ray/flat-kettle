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

package org.pentaho.di.trans.dataservice.ui.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.dataservice.DataServiceContext;
import org.pentaho.di.trans.dataservice.DataServiceMeta;
import org.pentaho.di.trans.dataservice.ui.DataServiceDelegate;
import org.pentaho.di.trans.dataservice.ui.UIFactory;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.ui.spoon.Spoon;
import org.pentaho.di.ui.spoon.TreeSelection;

import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class DataServicePopupMenuExtensionTest {

  @Mock
  private LogChannelInterface log;

  @Mock
  private Tree extension;

  @Mock
  private DataServiceContext context;

  @Mock
  private DataServiceDelegate delegate;

  @Mock
  private Spoon spoon;

  @Mock
  private TreeSelection selection;

  @Mock
  private UIFactory uiFactory;

  @Mock
  private TransMeta transMeta;

  @Mock
  private Menu menu;

  @Mock
  private MenuItem menuItem;

  @Mock
  private DataServiceMeta dataServiceMeta;

  private DataServicePopupMenuExtension dataServicePopupMenuExtension;

  @Before
  public void setUp() throws Exception {
    when( context.getDataServiceDelegate() ).thenReturn( delegate );
    when( delegate.getSpoon() ).thenReturn( spoon );
    when( spoon.getActiveTransformation() ).thenReturn( transMeta );
    when( transMeta.getSteps() ).thenReturn( new ArrayList<StepMeta>() );
    when( context.getUIFactory() ).thenReturn( uiFactory );
    when( uiFactory.getMenuItem( any( Menu.class ), anyInt() ) ).thenReturn( menuItem );
    when( uiFactory.getMenu( any( Tree.class ) ) ).thenReturn( menu );
    TreeSelection[] treeSelection = new TreeSelection[] { selection };
    when( spoon.getTreeObjects( any( Tree.class ) ) ).thenReturn( treeSelection );

    dataServicePopupMenuExtension = new DataServicePopupMenuExtension( context );
  }

  @Test
  public void testCallExtensionPointRoot() throws Exception {
    when( selection.getSelection() ).thenReturn( DataServiceMeta.class );

    dataServicePopupMenuExtension.callExtensionPoint( log, extension );

    verify( delegate, times( 2 ) ).getSpoon();
    verify( spoon ).getTreeObjects( extension );
    verify( uiFactory, times( 2 ) ).getMenuItem( menu, SWT.NONE );
  }

  @Test
  public void testCallExtensionPoint() throws Exception {
    when( uiFactory.getMenu( any( Tree.class ) ) ).thenReturn( null );
    when( selection.getSelection() ).thenReturn( dataServiceMeta );

    dataServicePopupMenuExtension.callExtensionPoint( log, extension );

    verify( delegate, times( 2 ) ).getSpoon();
    verify( spoon ).getTreeObjects( extension );
    verify( uiFactory, times( 5 ) ).getMenuItem( null, SWT.NONE );
  }
}
