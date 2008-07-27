package org.apache.maven.integrationtests;

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0014Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test POM configuration by settings the -source and -target for the
     * compiler to 1.4
     */
    public void testit0014()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0014" );
        IntegrationTestRunner verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );
        verifier.executeGoal( "test" );
        verifier.assertFilePresent( "target/classes/org/apache/maven/it0014/Person.class" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}

