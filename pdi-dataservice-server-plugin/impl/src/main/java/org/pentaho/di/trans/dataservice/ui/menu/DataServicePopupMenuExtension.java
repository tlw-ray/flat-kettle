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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.dataservice.DataServiceContext;
import org.pentaho.di.trans.dataservice.DataServiceMeta;
import org.pentaho.di.trans.dataservice.ui.DataServiceDelegate;
import org.pentaho.di.trans.dataservice.ui.UIFactory;
import org.pentaho.di.ui.core.ConstUI;
import org.pentaho.di.ui.spoon.TreeSelection;

@ExtensionPoint( id = "DataServicePopupMenuExtension", description = "Creates popup menus for data services",
    extensionPointId = "SpoonPopupMenuExtension" )
public class DataServicePopupMenuExtension implements ExtensionPointInterface {

  private static final Class<?> PKG = DataServicePopupMenuExtension.class;
  private static final Log logger = LogFactory.getLog( DataServicePopupMenuExtension.class );
  private DataServiceDelegate delegate;
  private UIFactory uiFactory;

  public DataServiceMeta selectedDataService;

  public DataServicePopupMenuExtension( DataServiceContext context ) {
    delegate = context.getDataServiceDelegate();
    uiFactory = context.getUIFactory();
  }

  @Override
  public void callExtensionPoint( LogChannelInterface log, Object extension ) throws KettleException {

    Menu popupMenu = null;

    Tree selectionTree = (Tree) extension;
    TreeSelection[] objects = delegate.getSpoon().getTreeObjects( selectionTree );
    TreeSelection object = objects[0];
    Object selection = object.getSelection();

    if ( selection == DataServiceMeta.class ) {
      popupMenu = uiFactory.getMenu( selectionTree );
      createRootPopupMenu( popupMenu );
    } else if ( selection instanceof DataServiceMeta ) {
      selectedDataService = (DataServiceMeta) selection;
      popupMenu = uiFactory.getMenu( selectionTree );
      createItemPopupMenu( popupMenu );
    }

    if ( popupMenu != null ) {
      ConstUI.displayMenu( popupMenu, selectionTree );
    } else {
      selectionTree.setMenu( null );
    }
  }

  private void createNewMenuItem( Menu parent ) {
    MenuItem newMenu =
        createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.New.Label" ),
            new DataServiceNewCommand() );
    newMenu.setEnabled( !delegate.getSpoon().getActiveTransformation().getSteps().isEmpty() );
  }

  private void createRootPopupMenu( Menu parent ) {
    createNewMenuItem( parent );
    uiFactory.getMenuItem( parent, SWT.SEPARATOR );
    createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.DriverDetails.Label" ),
        new DriverDetailsCommand() );
  }

  private void createItemPopupMenu( Menu parent ) {
    createNewMenuItem( parent );
    createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.Edit.Label" ),
        new DataServiceEditCommand() );
    createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.Delete.Label" ),
        new DataServiceDeleteCommand() );
    uiFactory.getMenuItem( parent, SWT.SEPARATOR );
    createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.Test.Label" ),
        new DataServiceTestCommand() );
    uiFactory.getMenuItem( parent, SWT.SEPARATOR );
    createPopupMenu( parent, BaseMessages.getString( PKG, "DataServicePopupMenu.DriverDetails.Label" ),
        new DriverDetailsCommand() );
  }

  private MenuItem createPopupMenu( Menu parent, final String label, final DataServiceCommand dataServiceCommand ) {
    MenuItem menuItem = uiFactory.getMenuItem( parent, SWT.NONE );
    menuItem.setText( label );
    menuItem.addSelectionListener( new SelectionListener() {
      @Override
      public void widgetSelected( SelectionEvent selectionEvent ) {
        dataServiceCommand.execute();
      }

      @Override
      public void widgetDefaultSelected( SelectionEvent selectionEvent ) {

      }
    } );
    return menuItem;
  }

  interface DataServiceCommand {
    public void execute();
  }

  class DataServiceNewCommand implements DataServiceCommand {
    @Override
    public void execute() {
      delegate.createNewDataService( null );
    }
  }

  class DataServiceEditCommand implements DataServiceCommand {
    @Override
    public void execute() {
      delegate.editDataService( selectedDataService );
    }
  }

  class DataServiceDeleteCommand implements DataServiceCommand {
    @Override
    public void execute() {
      delegate.removeDataService( selectedDataService, true );
    }
  }

  class DataServiceTestCommand implements DataServiceCommand {
    @Override
    public void execute() {
      delegate.showTestDataServiceDialog( selectedDataService );
    }
  }

  class DriverDetailsCommand implements DataServiceCommand {
    @Override
    public void execute() {
      delegate.showDriverDetailsDialog();
    }
  }
}
