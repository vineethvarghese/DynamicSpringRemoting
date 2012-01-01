package dynaconfig.remote;


import dynaconfig.MethodInterceptorFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor;
import org.springframework.remoting.support.RemoteInvocationFactory;

/**
 * Date: 12/30/11
 * Time: 4:19 PM
 */
public class HttpInvokerClientInterceptorFactory
        implements MethodInterceptorFactory<HttpRemoteServiceConfiguration>, BeanClassLoaderAware {

    private ClassLoader beanClassLoader;

    private RemoteInvocationFactory remoteInvocationFactory;

    private Class serviceInterface;

    private static final Logger logger = LoggerFactory.getLogger(HttpInvokerClientInterceptorFactory.class);

    private final HttpRemoteServiceConfiguration defaultConfiguration = new HttpRemoteServiceConfiguration();

    @Override
    public MethodInterceptor createMethodInterceptor(HttpRemoteServiceConfiguration configuration) {
        logger.debug("Creating new HttpInvokerClientInterceptor based on configuration {} and service interface {}",
                configuration, serviceInterface);
        HttpInvokerClientInterceptor interceptor = new HttpInvokerClientInterceptor();
        interceptor.setServiceInterface(serviceInterface);
        interceptor.setServiceUrl(configuration.getServiceUrl());
        if (remoteInvocationFactory != null) interceptor.setRemoteInvocationFactory(remoteInvocationFactory);
        interceptor.setBeanClassLoader(beanClassLoader);
        return interceptor;
    }

    @Override
    public HttpRemoteServiceConfiguration defaultConfiguration() {
        return defaultConfiguration;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.beanClassLoader;
    }

    public RemoteInvocationFactory getRemoteInvocationFactory() {
        return remoteInvocationFactory;
    }

    public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
        this.remoteInvocationFactory = remoteInvocationFactory;
    }

    @Override
    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        if (serviceInterface != null && !serviceInterface.isInterface()) {
            throw new IllegalArgumentException("'serviceInterface' must be an interface");
        }
        this.serviceInterface = serviceInterface;
    }

      public void setServiceUrl(String serviceUrl) {
        this.defaultConfiguration.setServiceUrl(serviceUrl);
    }
}
