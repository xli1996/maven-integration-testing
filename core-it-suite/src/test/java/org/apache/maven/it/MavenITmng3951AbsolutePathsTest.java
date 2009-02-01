package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Properties;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3951">MNG-3951</a>.
 * 
 * @author Benjamin Bentmann
 * @version $Id$
 */
public class MavenITmng3951AbsolutePathsTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3951AbsolutePathsTest()
    {
        super( "(2.0.10,2.1.0-M1),(2.1.0-M1,)" );
    }

    /**
     * Test that the paths retrieved from the core are always absolute, in particular the drive-relative paths on
     * Windows must be properly resolved.
     */
    public void testitMNG3951()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3951" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );

        /*
         * Cut off anything before the first file separator from the local repo path. This is harmless on a Unix-like
         * filesystem but will make the path drive-relative on Windows so we can check how Maven handles it.
         */
        String repoDir = new File( verifier.localRepo ).getAbsolutePath();
        verifier.setLocalRepo( repoDir.substring( repoDir.indexOf( File.separator ) ) );

        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertFilePresent( "target/path.properties" );
        Properties props = verifier.loadProperties( "target/path.properties" );
        assertEquals( new File( testDir, "tmp" ).getAbsolutePath(), props.getProperty( "fileParams.0" ) );
        assertEquals( new File( getRoot( testDir ), "tmp" ).getAbsolutePath(), props.getProperty( "fileParams.1" ) );
        assertEquals( repoDir, props.getProperty( "stringParams.0" ) );
    }

    private static File getRoot( File path )
    {
        File root = path;
        for ( File dir = path; dir != null; dir = dir.getParentFile() )
        {
            root = dir;
        }
        return root;
    }

}