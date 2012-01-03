package dynaconfig;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanClassLoaderAware;

import java.util.Date;
import java.util.Random;

/**
 * Date: 1/3/12
 * Time: 3:18 PM
 */
public class TestMethodInterceptorFactory implements MethodInterceptorFactory<TestConfiguration>, BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Override
    public MethodInterceptor createMethodInterceptor(TestConfiguration configuration) {
        return new SysOutMethodInterceptor();
    }

    @Override
    public TestConfiguration defaultConfiguration() {
        return new TestConfiguration();
    }

    @Override
    public Class getServiceInterface() {
        return TestInterface.class;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}

class SysOutMethodInterceptor implements MethodInterceptor {

    private Long randomNumber;

    SysOutMethodInterceptor() {
        randomNumber = new Random().nextLong();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("In interceptor created at [" + randomNumber + "] Method Invocation : " + invocation);
        return "Test";
    }
}
