package dynaconfig.remote;

import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 1/1/12
 * Time: 12:56 AM
 */
public class HttpRemoteServiceConfigurationTest {

    HttpRemoteServiceConfiguration target;

    @Test
    public void testForValidityWithNullAsUrl() {
        target = new HttpRemoteServiceConfiguration(null);
        Assert.assertFalse("Configuration should be invalid with null url", target.isValid());
    }

    @Test
    public void testForValidityWithEmptyStringAsUrl() {
        target = new HttpRemoteServiceConfiguration("");
        Assert.assertFalse("Configuration should be invalid with empty string as url", target.isValid());
    }

    @Test
    public void testForValidityWithStringAsUrl() {
        target = new HttpRemoteServiceConfiguration("test");
        Assert.assertTrue("Configuration should be valid with valid string", target.isValid());
    }
}
