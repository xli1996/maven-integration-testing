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
import java.util.Collections;
import java.util.List;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3769">MNG-3769</a>.
 * 
 * @version $Id: MavenITmng3380ManagedRelocatedTransdepsTest.java 721706 2008-11-29 19:13:17Z bentmann $
 */
public class MavenITmng3769ExclusionRelocatedTransdepsTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3769ExclusionRelocatedTransdepsTest()
    {
        // also didn't work in 2.0, but did in 2.0.1+ until regressed in 2.1.0-M1
        super("[2.0.1,2.1.0-M1),(2.1.0-M1,)");
    }

    /**
     * Verify that dependency resolution considers dependency management also for relocated artifacts.
     */
    public void testitMNG3769()
        throws Exception 
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3769" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3769" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        List artifacts = verifier.loadLines( "target/artifacts.txt", "UTF-8" );
        assertEquals( Collections.singletonList( "org.apache.maven.its.mng3769:dependency:jar:1.0" ), artifacts );

        List paths = verifier.loadLines( "target/test.txt", "UTF-8" );
        assertEquals( 3, paths.size() );
        assertEquals( "dependency-1.0.jar", paths.get( 2 ) );
    }

}
