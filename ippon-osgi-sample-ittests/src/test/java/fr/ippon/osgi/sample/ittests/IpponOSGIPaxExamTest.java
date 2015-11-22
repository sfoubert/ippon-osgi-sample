package fr.ippon.osgi.sample.ittests;

import aQute.bnd.osgi.Constants;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.streamBundle;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;

/**
 * Tests unitaires des commandes Karaf avec PaxExam
 *
 * @author sfoubert
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class IpponOSGIPaxExamTest extends KarafTestSupport {

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        return probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*,org.apache.felix.service.*;status=provisional");
    }

    @Configuration
    public static Option[] configure() throws Exception {
        return new Option[]{
            karafDistributionConfiguration()
            .frameworkUrl("mvn:org.apache.karaf/apache-karaf/4.0.3/tar.gz")
            .karafVersion("4.0.3")
            .useDeployFolder(false)
            .unpackDirectory(new File("target/paxexam/unpack")),
            logLevel(LogLevelOption.LogLevel.WARN),

            // install features
            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "jdbc"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "hibernate"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "jpa"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "transaction"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "jndi"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "pax-jdbc-pool-dbcp2"),

            features(maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml").classifier(
            "features").versionAsInProject(), "aries-annotation"),

            // Change ssh port
            editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiRegistryPort", RMI_REG_PORT),
            editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiServerPort", RMI_SERVER_PORT),

            keepRuntimeFolder(),

            // install bundles
            mavenBundle().groupId("com.h2database").artifactId("h2").version("1.4.190"),
            mavenBundle().groupId("commons-lang").artifactId("commons-lang").version("2.6"),
            mavenBundle().groupId("commons-logging").artifactId("commons-logging").version("1.2"),
            mavenBundle().groupId("commons-io").artifactId("commons-io").version("2.4"),

            // install bundle datasource h2 for test
            streamBundle(bundle().add("OSGI-INF/blueprint/datasource-h2-test.xml",
                    new File("src/test/resources/OSGI-INF/blueprint/datasource-h2-test.xml").toURL())
                    .set(Constants.BUNDLE_NAME, "Apache Karaf :: Ippon OSGI Datasource Test")
                    .set(Constants.BUNDLE_SYMBOLICNAME, "ippon-osgi-sample-ds")
                    .set("Bundle-ManifestVersion", "2")
                    .set(Constants.DYNAMICIMPORT_PACKAGE, "*").build()).start(),

            // install ippon bundles
            mavenBundle().groupId("fr.ippon.osgi.sample").artifactId("ippon-osgi-sample-services").version("1.0-SNAPSHOT"),
            mavenBundle().groupId("fr.ippon.osgi.sample").artifactId("ippon-osgi-sample-command").version("1.0-SNAPSHOT"),

        };
    }

    @Test
    public void testProvisioning() throws Exception {
        // Check that the features are installed
        assertFeatureInstalled("jdbc", "4.0.3");
        assertFeatureInstalled("hibernate", "4.3.6.Final");
        assertFeatureInstalled("jpa", "2.2.0");

        // Check that the bundles are installed
        assertBundleInstalled("ippon-osgi-sample-services");
        assertBundleInstalled("ippon-osgi-sample-command");
    }

    @Test
    public void testListEmployeesCommand() {
        Assert.assertNotNull(bundleContext);

        String result = executeCommand("ippon:list-employees");
        System.out.println("result : " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testListEmployeesWithOptionsCommand() {
        Assert.assertNotNull(bundleContext);

        String result = executeCommand("ippon:list-employees -j ARCHITECT -n 'Employee 3'");
        System.out.println("result : " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAddEmployeeCommand() {
        Assert.assertNotNull(bundleContext);

        String result = executeCommand("ippon:add-employee DEV 'New Employee' 'New Employee' '01-01-1990'");
        System.out.println("result : " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveEmployeeCommand() {
        Assert.assertNotNull(bundleContext);

        String result = executeCommand("ippon:remove-employee 2");
        System.out.println("result : " + result);
        Assert.assertNotNull(result);
    }

}
