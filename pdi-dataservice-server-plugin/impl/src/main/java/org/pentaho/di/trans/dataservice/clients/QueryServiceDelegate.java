/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
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

package org.pentaho.di.trans.dataservice.clients;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.dataservice.client.api.IDataServiceClientService;

import java.util.List;
import java.util.Map;

/**
 * Created by bmorrise on 8/31/16.
 */
public class QueryServiceDelegate implements Query.Service {

  private List<Query.Service> queryServices;

  public QueryServiceDelegate( List<Query.Service> queryServices ) {
    this.queryServices = queryServices;
  }

  @Override public Query prepareQuery( String sql, int maxRows, Map<String, String> parameters )
    throws KettleException {
    for ( Query.Service queryService : queryServices ) {
      Query query = queryService.prepareQuery( sql, maxRows, parameters );
      if ( query != null ) {
        return query;
      }
    }
    return null;
  }

  @Override public Query prepareQuery( String sql, IDataServiceClientService.StreamingMode windowMode,
                                       long windowSize, long windowEvery, long windowLimit,
                                       final Map<String, String> parameters ) throws KettleException {
    for ( Query.Service queryService : queryServices ) {
      Query query = queryService.prepareQuery( sql, windowMode, windowSize, windowEvery, windowLimit, parameters );
      if ( query != null ) {
        return query;
      }
    }
    return null;
  }
}
