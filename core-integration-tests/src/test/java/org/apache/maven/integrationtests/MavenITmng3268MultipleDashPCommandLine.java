package org.apache.maven.integrationtests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.it.IntegrationTestRunner;

public class MavenITmng3268MultipleDashPCommandLine
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng3268MultipleDashPCommandLine()
        throws InvalidVersionSpecificationException
    {
        super( "(2.0.9,)" );
    }

    public void testMultipleProfileParams ()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/mng-3268-MultipleDashPCommandLine" );

        IntegrationTestRunner verifier;

        verifier = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();
        cliOptions.add( "-Pprofile1,profile2" );
        cliOptions.add( "-Pprofile3" );
        cliOptions.add( "-P profile4" );
        verifier.executeGoal( "package", cliOptions );
        verifier.verifyErrorFreeLog();
        verifier.assertFilePresent( "target/profile1/touch.txt" );
        verifier.assertFilePresent( "target/profile2/touch.txt" );
        verifier.assertFilePresent( "target/profile3/touch.txt" );
        verifier.assertFilePresent( "target/profile4/touch.txt" );
        verifier.resetStreams();
    }
}
