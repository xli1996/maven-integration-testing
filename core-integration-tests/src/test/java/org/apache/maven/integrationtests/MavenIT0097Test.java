package org.apache.maven.integrationtests;

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0097Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test that the implied relative path for the parent POM works, even two
     * levels deep.
     */
    public void testit0097()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0097" );
        IntegrationTestRunner verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );
        verifier.executeGoal( "package" );
        verifier.assertFilePresent( "project/project-level2/project-level3/target/it0097.txt" );
        verifier.assertFilePresent( "project/project-sibling-level2/target/it0097.txt" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}

