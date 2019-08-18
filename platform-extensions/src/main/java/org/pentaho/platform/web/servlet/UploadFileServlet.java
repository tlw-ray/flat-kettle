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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.IAuthorizationPolicy;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.web.servlet.messages.Messages;
import org.pentaho.reporting.libraries.base.util.StringUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UploadFileServlet extends HttpServlet implements Servlet {

  private static final long serialVersionUID = 8305367618713715640L;

  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    try {
      IPentahoSession session = PentahoSessionHolder.getSession();
      if ( !hasManageDataAccessPermission( session ) ) {
        response.sendError( 403, Messages.getInstance().getErrorString( "UploadFileServlet.ERROR_0009_UNAUTHORIZED" ) );
        return;
      }

      UploadFileUtils utils = new UploadFileUtils( session );

      response.setContentType( "text/plain" ); //$NON-NLS-1$

      // Note - request.getParameter doesn't work on multi-part file data. But just in case,
      // we get the standardRequestParamaters as well as the parameter map we create from the
      // multi-part form data.
      Map<String, String[]> standardRequestParameters = request.getParameterMap();
      Map<String, FileItem> parsedMultiPartRequestParameters = this.getParsedRequestParameters( request, session );

      FileItem uploadItem = parsedMultiPartRequestParameters.get( "uploadFormElement" ); //$NON-NLS-1$
      if ( uploadItem == null ) {
        String error = Messages.getInstance().getErrorString( "UploadFileServlet.ERROR_0001_NO_FILE_TO_UPLOAD" ); //$NON-NLS-1$
        response.getWriter().write( error );
        return;
      }

      String unzip = getRequestParameter( standardRequestParameters, parsedMultiPartRequestParameters, "unzip" ); //$NON-NLS-1$
      String temporary =
          getRequestParameter( standardRequestParameters, parsedMultiPartRequestParameters, "mark_temporary" ); //$NON-NLS-1$
      String fileName = getRequestParameter( standardRequestParameters, parsedMultiPartRequestParameters, "file_name" ); //$NON-NLS-1$

      if ( StringUtils.isEmpty( fileName ) ) {
        throw new ServletException( Messages.getInstance().getErrorString( "UploadFileServlet.ERROR_0010_FILE_NAME_INVALID" ) );
      }
      boolean isTemporary = false;
      if ( temporary != null ) {
        isTemporary = Boolean.valueOf( temporary );
      }
      boolean shouldUnzip = false;
      if ( unzip != null ) {
        shouldUnzip = Boolean.valueOf( unzip );
      }
      utils.setShouldUnzip( shouldUnzip );
      utils.setTemporary( isTemporary );
      utils.setFileName( fileName );
      utils.setWriter( response.getWriter() );
      utils.setUploadedFileItem( uploadItem );
      utils.process(); // Do nothing with success value - the output should already have been written to the servlet response.

    } catch ( Exception e ) {
      String error =
          Messages.getInstance().getErrorString( "UploadFileServlet.ERROR_0005_UNKNOWN_ERROR",
            e.getLocalizedMessage() ); //$NON-NLS-1
      response.getWriter().write( error );
    }
  }

  protected String getRequestParameter( Map<String, String[]> primary, Map<String, FileItem> secondary, String parameterName ) {
    String rtn = getRequestParameter( primary, parameterName );
    return ( rtn != null ) ? rtn : getRequestParameter( secondary, parameterName );
  }

  protected String getRequestParameter( Map<String, ?> requestParameters, String parameterName ) {
    Object obj = requestParameters.get( parameterName );
    if ( obj != null ) {
      if ( obj instanceof FileItem ) {
        return ( (FileItem) obj ).getString();
      } else if ( obj instanceof String[] ) { // such is the case for request.getParameterMap() values
        String[] a = (String[]) obj;
        if ( a.length > 0 ) {
          return a[0];
        }
      } else {
        return obj.toString();
      }
    }
    return null;
  }

  /**
   * Parses the multi-part request to get all the parameters out.
   *
   * @param request
   * @param session
   * @return Map of the request parameters
   */
  private Map<String, FileItem> getParsedRequestParameters( HttpServletRequest request, IPentahoSession session ) {

    HashMap<String, FileItem> rtn = new HashMap<>();
    DiskFileItemFactory factory = new DiskFileItemFactory();

    ServletFileUpload upload = new ServletFileUpload( factory );
    try {
      List<FileItem> items = upload.parseRequest( request );
      Iterator<FileItem> it = items.iterator();
      while ( it.hasNext() ) {
        FileItem item = it.next();
        String fName = item.getFieldName();
        rtn.put( fName, item );
      }
    } catch ( FileUploadException e ) {
      return null;
    }
    return rtn;
  }

  /**
   * Returns true if the current user has Manage Data Source Security. Otherwise returns false.
   * @param session
   * @return
   */
  protected boolean hasManageDataAccessPermission( IPentahoSession session ) {
    // If this breaks an OEM's plugin, provide a get-out-of-jail card with an entry in the pentaho.xml.
    String override = PentahoSystem.getSystemSetting( "data-access-override", "false" );
    Boolean rtnOverride = Boolean.valueOf( override );
    if ( !rtnOverride ) {
      IAuthorizationPolicy policy = PentahoSystem.get( IAuthorizationPolicy.class );
      if ( policy != null ) {
        return policy.isAllowed( "org.pentaho.platform.dataaccess.datasource.security.manage" );
      } else {
        return false;
      }
    } else {
      return true; // Override the security policy with the entry in the pentaho.xml.
    }
  }

}
