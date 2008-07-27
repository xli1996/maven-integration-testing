package org.apache.maven.integrationtests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0090Test
    extends AbstractMavenIntegrationTestCase
{
    /**
     * Test that ensures that envars are interpolated correctly into plugin
     * configurations.
     */
    public void testit0090()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0090" );
        IntegrationTestRunner verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );
        Map envVars = new HashMap();
        envVars.put( "MAVEN_TEST_ENVAR", "MAVEN_TEST_ENVAR_VALUE" );
        verifier.executeGoal( "test", envVars );
        verifier.assertFilePresent( "target/mojo-generated.properties" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}

