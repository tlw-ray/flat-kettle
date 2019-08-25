/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.gwt.widgets.client.text;

import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

@RunWith( GwtMockitoTestRunner.class )
public class ValidationPasswordTextBoxTest {
  @Test
  public void testAddKeyUpHandler() throws Exception {
    ValidationPasswordTextBox textBox = mock( ValidationPasswordTextBox.class );
    doCallRealMethod().when( textBox ).addKeyUpHandler( any( KeyUpHandler.class ) );

    final KeyUpHandler handler = mock( KeyUpHandler.class );
    assertNull( textBox.handlers );
    textBox.addKeyUpHandler( handler );
    assertNotNull( textBox.handlers );
    assertEquals( handler, textBox.handlers.get( 0 ) );
  }

  @Test
  public void testAddValidatableTextBoxListener() throws Exception {
    ValidationPasswordTextBox textBox = mock( ValidationPasswordTextBox.class );
    doCallRealMethod().when( textBox ).addValidatableTextBoxListener( any( IValidationTextBoxListener.class ) );

    final IValidationTextBoxListener listener = mock( IValidationTextBoxListener.class );
    assertNull( textBox.listeners );
    textBox.addValidatableTextBoxListener( listener );
    assertNotNull( textBox.listeners );
    assertEquals( listener, textBox.listeners.get( 0 ) );
  }
}
