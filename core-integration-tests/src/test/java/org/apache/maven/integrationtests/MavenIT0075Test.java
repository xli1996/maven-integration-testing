package org.apache.maven.integrationtests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0075Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Verify that direct invocation of a mojo from the command line still
     * results in the processing of modules included via profiles.
     */
    public void testit0075()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0075" );
        IntegrationTestRunner verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );
        List cliOptions = new ArrayList();
        cliOptions.add( "-Dactivate=anything" );
        verifier.executeGoal( "help:active-profiles, package, eclipse:eclipse, clean:clean", cliOptions );
        verifier.assertFileNotPresent( "sub1/target/maven-it-it0075-sub1-1.0.jar" );
        verifier.assertFileNotPresent( "sub2/target/maven-it-it0075-sub2-1.0.jar" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}

