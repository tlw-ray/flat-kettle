/*!
 * Copyright 2010 - 2018 Hitachi Vantara.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pentaho.authentication.mapper.impl;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

import org.pentaho.authentication.mapper.api.AuthenticationMappingManager;
import org.pentaho.authentication.mapper.api.AuthenticationMappingService;
import org.pentaho.authentication.mapper.api.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimaps;
import com.google.common.collect.SortedSetMultimap;

/**
 * @author bryan
 */
public class AuthenticationMappingManagerImpl implements AuthenticationMappingManager {

  private Logger LOGGER = LoggerFactory.getLogger( AuthenticationMappingManagerImpl.class );
  private final SortedSetMultimap<TypePair, RankedAuthService> serviceMap = Multimaps.synchronizedSortedSetMultimap(
      Multimaps.newSortedSetMultimap( new HashMap<>(), TreeSet::new )
  );

  public AuthenticationMappingManagerImpl() throws IOException {
  }

  @Override
  @SuppressWarnings( "unchecked" )
  public <InputType, OutputType> OutputType getMapping( Class<InputType> inputType, InputType input,
                                                        Class<OutputType> outputType ) throws MappingException {
    AuthenticationMappingService<InputType, OutputType> service;
    synchronized ( serviceMap ) {
      service = serviceMap.get( new TypePair( inputType, outputType ) ).stream()
        .filter( ( rankedService ) -> rankedService.getService().accepts( input ) )
        .findFirst()
        .map( RankedAuthService::getService )
        .orElse( null );
    }

    return service != null ? service.getMapping( input, null ) : null;
  }

  public void onMappingServiceAdded( AuthenticationMappingService service, Map config ) {
    if ( service == null ) {
      return;
    }

    int ranking = Optional.ofNullable( config.get( RANKING_CONFIG ) )
        .map( String::valueOf ).map( Integer::parseInt ).orElse( 50 );

    serviceMap.put( new TypePair( service ), new RankedAuthService( ranking, service ) );
  }

  public void onMappingServiceRemoved( AuthenticationMappingService service ) {
    if ( service == null ) {
      return;
    }

    synchronized ( serviceMap ) {
      serviceMap.get( new TypePair( service ) )
        .removeIf( rankedAuthService -> rankedAuthService.service.equals( service ) );
    }
  }

  private static class TypePair {
    final Class input, output;

    TypePair( AuthenticationMappingService service ) {
      this( service.getInputType(), service.getOutputType() );
    }

    TypePair( Class input, Class output ) {
      this.input = Objects.requireNonNull( input );
      this.output = Objects.requireNonNull( output );
    }

    @Override public boolean equals( Object o ) {
      if ( this == o ) {
        return true;
      }
      if ( !( o instanceof TypePair ) ) {
        return false;
      }
      TypePair typePair = (TypePair) o;
      return Objects.equals( input, typePair.input ) && Objects.equals( output, typePair.output );
    }

    @Override public int hashCode() {
      return Objects.hash( input, output );
    }

    @Override public String toString() {
      return input + " -> " + output;
    }
  }

  private static class RankedAuthService implements Comparable<RankedAuthService> {
    final int rank;
    final AuthenticationMappingService service;

    RankedAuthService( int rank, AuthenticationMappingService service ) {
      this.rank = rank;
      this.service = service;
    }

    private String getId() {
      return getService().getId();
    }

    int getRank() {
      return rank;
    }

    AuthenticationMappingService getService() {
      return service;
    }

    @Override public String toString() {
      return "(" + rank + ") " + service;
    }

    @Override public int compareTo( RankedAuthService o ) {
      return Comparator
        .comparingInt( RankedAuthService::getRank ).reversed()
        .thenComparing( RankedAuthService::getId )
        .compare( this, o );
    }

  }
}
