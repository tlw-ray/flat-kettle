/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2019 by Hitachi Vantara : http://www.pentaho.com
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

package org.pentaho.di.pan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.metastore.api.IMetaStore;
import org.pentaho.metastore.stores.delegate.DelegatingMetaStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PanCommandExecutorTest {

  private static final String FS_METASTORE_NAME = "FS_METASTORE";
  private static final String REPO_METASTORE_NAME = "REPO_METASTORE";

  private static final String SAMPLE_KTR = "hello-world.ktr";

  private DelegatingMetaStore metastore = new DelegatingMetaStore();

  private Repository repository;
  private IMetaStore fsMetaStore;
  private IMetaStore repoMetaStore;
  private RepositoryDirectoryInterface directoryInterface;
  private PanCommandExecutor panCommandExecutor;

  @Before
  public void setUp() throws Exception {

    repository = mock( Repository.class );
    fsMetaStore = mock( IMetaStore.class );
    repoMetaStore = mock( IMetaStore.class );
    directoryInterface = mock( RepositoryDirectoryInterface.class );
    panCommandExecutor = mock( PanCommandExecutor.class );

    // mock actions from Metastore
    when( fsMetaStore.getName() ).thenReturn( FS_METASTORE_NAME );
    when( repoMetaStore.getName() ).thenReturn( REPO_METASTORE_NAME );
    metastore.addMetaStore( fsMetaStore );

    // mock actions from Repository
    when( repository.getMetaStore() ).thenReturn( repoMetaStore );

    // mock actions from PanCommandExecutor
    when( panCommandExecutor.getMetaStore() ).thenReturn( metastore );
    when( panCommandExecutor.loadRepositoryDirectory( anyObject(), anyString(), anyString(), anyString(), anyString() ) )
            .thenReturn( directoryInterface );

    // call real methods for loadTransFromFilesystem(), loadTransFromRepository();
    when( panCommandExecutor.loadTransFromFilesystem( anyString(), anyString(), anyString(), anyString() ) ).thenCallRealMethod();
    when( panCommandExecutor.loadTransFromRepository( anyObject(), anyString(), anyString() ) ).thenCallRealMethod();
  }

  @After
  public void tearDown() {
    metastore = null;
    repository = null;
    fsMetaStore = null;
    repoMetaStore = null;
    directoryInterface = null;
    panCommandExecutor = null;
  }

  @Test
  public void testMetastoreFromRepoAddedIn() throws Exception {

    // mock Trans loading from repo
    TransMeta t = new TransMeta( getClass().getResource( SAMPLE_KTR ).getPath() );
    when( repository.loadTransformation( anyString(), anyObject(), anyObject(), anyBoolean(), anyString() ) ).thenReturn( t );

    // test
    Trans trans = panCommandExecutor.loadTransFromRepository( repository, "", SAMPLE_KTR );
    assertNotNull( trans );
    assertNotNull( trans.getMetaStore() );
    assertTrue( trans.getMetaStore() instanceof DelegatingMetaStore );
    assertNotNull( ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList() );

    assertEquals( 2, ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList().size() );
    assertTrue( ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList().stream()
            .anyMatch( m -> {
              try {
                return REPO_METASTORE_NAME.equals( m.getName() );
              } catch ( Exception e ) {
                return false;
              }
            } ) );
  }

  @Test
  public void testMetastoreFromFilesystemAddedIn() throws Exception {

    String fullPath = getClass().getResource( SAMPLE_KTR ).getPath();

    Trans trans = panCommandExecutor.loadTransFromFilesystem( "", fullPath, "", "" );
    assertNotNull( trans );
    assertNotNull( trans.getMetaStore() );
    assertTrue( trans.getMetaStore() instanceof DelegatingMetaStore );
    assertNotNull( ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList() );

    assertEquals( 1, ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList().size() );

    assertTrue( ( (DelegatingMetaStore) trans.getMetaStore() ).getMetaStoreList().stream()
            .anyMatch( m -> {
              try {
                return FS_METASTORE_NAME.equals( m.getName() );
              } catch ( Exception e ) {
                return false;
              }
            } ) );
  }
}
