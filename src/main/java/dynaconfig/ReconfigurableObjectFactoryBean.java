package dynaconfig;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Date: 12/31/11
 * Time: 6:46 PM
 */
public class ReconfigurableObjectFactoryBean implements FactoryBean, InitializingBean {

    private MethodInterceptorFactory methodInterceptorFactory;

    private ReconfigurableObjectMethodInterceptor reconfigurableObjectMethodInterceptor;

    private Object proxy;

    @Override
    public Object getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return methodInterceptorFactory.getServiceInterface();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(methodInterceptorFactory, "'methodInterceptorFactory' must be provided");
        reconfigurableObjectMethodInterceptor = new ReconfigurableObjectMethodInterceptor(methodInterceptorFactory);
        final ProxyFactory factory = new ProxyFactory(new Class[]{methodInterceptorFactory.getServiceInterface(),
                ConfigurationUpdateAware.class});
        factory.addAdvice(reconfigurableObjectMethodInterceptor);
        proxy = factory.getProxy(methodInterceptorFactory.getClassLoader());
    }

    public void setMethodInterceptorFactory(MethodInterceptorFactory methodInterceptorFactory) {
        this.methodInterceptorFactory = methodInterceptorFactory;
    }
}
