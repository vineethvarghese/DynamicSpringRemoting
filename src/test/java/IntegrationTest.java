import dynaconfig.ConfigurationUpdateAware;
import dynaconfig.TestConfiguration;
import dynaconfig.TestInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Date: 1/3/12
 * Time: 3:39 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-ctx.xml")
public class IntegrationTest {
    //A quick and dirty integration test

    @Autowired
    TestInterface testBean;

    @Test
    public void test() {
        testBean.getLocation();
        ConfigurationUpdateAware configurationUpdateAware = (ConfigurationUpdateAware) testBean;
        configurationUpdateAware.update(new TestConfiguration());
        testBean.getLocation();
    }

}
