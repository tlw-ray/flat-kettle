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

package org.pentaho.osgi.blueprint;

import org.apache.aries.blueprint.NamespaceHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * An Activator which registers the Hitachi Vantara Blueprint NamespaceHandler
 * <p/>
 * Created by nbaker on 2/11/15.
 */
public class PentahoNamespaceActivator implements BundleActivator {
  @Override public void start( BundleContext bundleContext ) throws Exception {

    bundleContext.registerService( NamespaceHandler.class, new PentahoNamespaceHandler( bundleContext ),
        new Hashtable<String, String>() {
          {
            put( "osgi.service.blueprint.namespace",
                "http://www.pentaho.com/xml/schemas/pentaho-blueprint" );
          }
        }
    );
  }

  @Override public void stop( BundleContext bundleContext ) throws Exception {

  }
}
