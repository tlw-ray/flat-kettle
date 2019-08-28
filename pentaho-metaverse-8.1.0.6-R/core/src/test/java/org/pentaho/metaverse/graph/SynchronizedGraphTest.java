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

package org.pentaho.metaverse.graph;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith( MockitoJUnitRunner.class )
public class SynchronizedGraphTest {

  private SynchronizedGraph synchronizedGraph;

  @Mock IdGraph mockGraph;
  @Mock Vertex mockVertex;
  @Mock Edge mockEdge;

  @Before
  public void setUp() throws Exception {
    synchronizedGraph = new SynchronizedGraph( mockGraph );
  }

  @Test
  public void testAddVertex_nullId() throws Exception {
    synchronizedGraph.addVertex( null );
    verify( mockGraph, times( 1 ) ).addVertex( null );
  }

  @Test
  public void testAddVertex_withExistingId() throws Exception {
    when( mockGraph.getVertex( "id" ) ).thenReturn( mockVertex );
    synchronizedGraph.addVertex( "id" );
    verify( mockGraph, times( 1 ) ).getVertex( "id" );
    verify( mockGraph, never() ).addVertex( anyString() );
  }

  @Test
  public void testAddVertex_withNewId() throws Exception {
    when( mockGraph.getVertex( "id" ) ).thenReturn( null );
    synchronizedGraph.addVertex( "id" );
    verify( mockGraph, times( 1 ) ).getVertex( "id" );
    verify( mockGraph, times( 1 ) ).addVertex( "id" );
  }

  @Test
  public void testAddEdge_nullId() throws Exception {
    synchronizedGraph.addEdge( null, mockVertex, mockVertex, "self link" );
    verify( mockGraph, times( 1 ) ).addEdge( null, mockVertex, mockVertex, "self link" );
  }

  @Test
  public void testAddEdge_withExistingId() throws Exception {
    when( mockGraph.getEdge( "id" ) ).thenReturn( mockEdge );
    synchronizedGraph.addEdge( "id", mockVertex, mockVertex, "self link" );
    verify( mockGraph, times( 1 ) ).getEdge( "id" );
    verify( mockGraph, never() ).addEdge( anyString(), eq(mockVertex), eq(mockVertex), anyString() );
  }

  @Test
  public void testAddEdge_withNewId() throws Exception {
    when( mockGraph.getEdge( "id" ) ).thenReturn( null );
    synchronizedGraph.addEdge( "id", mockVertex, mockVertex, "self link" );
    verify( mockGraph, times( 1 ) ).getEdge( "id" );
    verify( mockGraph, times( 1 ) ).addEdge( "id", mockVertex, mockVertex, "self link" );
  }

  @Test
  public void testDelegateMethodsAreCalled() throws Exception {
    synchronizedGraph.removeVertex( mockVertex );
    verify( mockGraph, times( 1 ) ).removeVertex( mockVertex );

    synchronizedGraph.removeEdge( mockEdge );
    verify( mockGraph, times( 1 ) ).removeEdge( mockEdge );

    synchronizedGraph.getEdge( "id" );
    verify( mockGraph, times( 1 ) ).getEdge( "id" );

    synchronizedGraph.getEdges();
    verify( mockGraph, times( 1 ) ).getEdges();

    synchronizedGraph.getEdges( "key", "value" );
    verify( mockGraph, times( 1 ) ).getEdges( "key", "value" );

    synchronizedGraph.getFeatures();
    verify( mockGraph, times( 1 ) ).getFeatures();

    synchronizedGraph.getVertex( "id" );
    verify( mockGraph, times( 1 ) ).getVertex( "id" );

    synchronizedGraph.getVertices();
    verify( mockGraph, times( 1 ) ).getVertices();

    synchronizedGraph.getVertices( "key", "value" );
    verify( mockGraph, times( 1 ) ).getVertices( "key", "value" );

    synchronizedGraph.query();
    verify( mockGraph, times( 1 ) ).query();

    synchronizedGraph.shutdown();
    verify( mockGraph, times( 1 ) ).shutdown();
  }
}
