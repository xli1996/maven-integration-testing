package org.apache.maven.integrationtests;

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0003Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Builds upon it0001: we add a jar installation step. We delete the JAR
     * from the local repository to make sure it is there post build.
     */
    public void testit0003()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0003" );
        IntegrationTestRunner verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );
        verifier.deleteArtifact( "org.apache.maven", "maven-it-it0003", "1.0", "jar" );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent( "target/classes/org/apache/maven/it0003/Person.class" );
        verifier.assertFilePresent( "target/test-classes/org/apache/maven/it0003/PersonTest.class" );
        verifier.assertFilePresent( "target/maven-it-it0003-1.0.jar" );
        verifier.assertFilePresent( "target/maven-it-it0003-1.0.jar!/it0003.properties" );
        verifier.assertArtifactPresent( "org.apache.maven.its.it0003", "maven-it-it0003", "1.0", "jar" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}

