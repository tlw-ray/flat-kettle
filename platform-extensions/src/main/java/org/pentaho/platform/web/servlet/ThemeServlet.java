/*!
 *
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
 *
 * Copyright (c) 2002-2018 Hitachi Vantara. All rights reserved.
 *
 */

package org.pentaho.platform.web.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.pentaho.platform.api.ui.IThemeManager;
import org.pentaho.platform.api.ui.ModuleThemeInfo;
import org.pentaho.platform.api.ui.Theme;
import org.pentaho.platform.api.ui.ThemeResource;
import org.pentaho.platform.api.usersettings.IUserSettingService;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes out the current Theme Tree out as Javascript. The current system and active module theme information is turned
 * into a JSON object for use by the web client
 * 
 * User: nbaker Date: 5/24/11
 */
public class ThemeServlet extends ServletBase {

  private static final long serialVersionUID = -7408362317719420602L;

  private static final Log logger = LogFactory.getLog( UIServlet.class );
  private IThemeManager themeManager = (IThemeManager) PentahoSystem.get( IThemeManager.class, null );

  @Override
  public Log getLogger() {
    return logger;
  }

  @Override
  protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
    handleRequest( req, resp );
  }

  public void handleRequest( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
    try {
      // look for a passed in theme context (content generator, other named area)
      String moduleName = req.getParameter( "context" );
      OutputStream out = resp.getOutputStream();
      resp.setContentType( "text/javascript" ); //$NON-NLS-1$ 
      resp.setHeader( "Cache-Control", "no-cache" ); //$NON-NLS-1$

      IUserSettingService settingsService = PentahoSystem.get( IUserSettingService.class, getPentahoSession( req ) );

      // NOTE: this code should be kept in sync with that of PentahoWebContextFilter.java

      String activeTheme = (String) getPentahoSession( req ).getAttribute( "pentaho-user-theme" );

      String ua = req.getHeader( "User-Agent" );
      // check if we're coming from a mobile device, if so, lock to system default (ruby)
      if ( !StringUtils.isEmpty( ua ) && ua.matches( ".*(?i)(iPad|iPod|iPhone|Android).*" ) ) {
        activeTheme = PentahoSystem.getSystemSetting( "default-theme", "ruby" );
      }
      if ( activeTheme == null ) {
        try {
          activeTheme = settingsService.getUserSetting( "pentaho-user-theme", null ).getSettingValue();
        } catch ( Exception ignored ) { // the user settings service is not valid in the agile-bi deployment of the
                                        // server
        }
        if ( activeTheme == null ) {
          activeTheme = PentahoSystem.getSystemSetting( "default-theme", "ruby" );
        }
      }

      out.write( ( "\n\n// Theming scripts. This file is generated by (" + getClass().getName()
        + ") and cannot be found on disk\n" )
          .getBytes() );

      out.write( ( "var active_theme = \"" + activeTheme + "\";\n\n" ).getBytes() );

      // Build-up JSON graph for system theme.
      JSONObject root = new JSONObject();
      JSONObject themeObject;

      for ( String systemThemeName : themeManager.getSystemThemeIds() ) {
        Theme theme = themeManager.getSystemTheme( systemThemeName );

        themeObject = new JSONObject();
        root.put( theme.getId(), themeObject );
        themeObject.put( "rootDir", theme.getThemeRootDir() );
        for ( ThemeResource res : theme.getResources() ) {
          themeObject.append( "resources", res.getLocation() );
        }
      }

      out.write( ( "var core_theme_tree = " + root.toString() + ";\n\n" ).getBytes() );
      out.write( "// Inject the theme script to handle the insertion of requested theme resources\n\n".getBytes() );

      ModuleThemeInfo moduleThemeinfo = themeManager.getModuleThemeInfo( moduleName );
      if ( moduleThemeinfo != null ) {
        // Build-up JSON graph for module theme.
        root = new JSONObject();
        for ( Theme theme : moduleThemeinfo.getModuleThemes() ) {
          themeObject = new JSONObject();
          root.put( theme.getName(), themeObject );
          themeObject.put( "rootDir", theme.getThemeRootDir() );
          for ( ThemeResource res : theme.getResources() ) {
            themeObject.append( "resources", res.getLocation() );
          }
        }

        out.write( ( "var module_theme_tree = " + root.toString() + ";\n\n" ).getBytes() );
      }

      // createElement & insertBefore
      out.write( ( "(function() {\n"
          + "var script = document.createElement('script');\n"
          + "script.type = 'text/javascript';\n"
          +
          // "script.async = false;\n" +
          "script.src = CONTEXT_PATH + 'js/themeResources.js';\n"
          + "var existing = document.getElementsByTagName('script')[0];\n"
          + "existing.parentNode.insertBefore(script, existing);\n" + "}());" ).getBytes() );

    } catch ( IOException e ) {
      logger.debug( "IO exception creating Theme info", e );
      throw new ServletException( e );
    } catch ( JSONException e ) {
      logger.debug( "JSON exception creating Theme info", e );
      throw new ServletException( e );
    }

  }
}
