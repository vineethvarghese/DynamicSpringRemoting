package dynaconfig;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Date: 12/31/11
 * Time: 8:32 PM
 */
public class ReconfigurableObjectMethodInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ReconfigurableObjectMethodInterceptor.class);

    private final MethodInterceptorFactory methodInterceptorFactory;

    private volatile Configuration lastKnownConfiguration;

    private volatile Object proxyTarget;

    public ReconfigurableObjectMethodInterceptor(MethodInterceptorFactory methodInterceptorFactory) {
        this.methodInterceptorFactory = methodInterceptorFactory;
        this.lastKnownConfiguration = methodInterceptorFactory.defaultConfiguration();
        createProxyTarget();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (isConfigurationUpdateCall(invocation.getMethod())) {
            newConfigurationAvailable((Configuration) invocation.getArguments()[0]);
            return null;
        } else {
            return ReflectionUtils.invokeMethod(invocation.getMethod(), proxyTarget, invocation.getArguments());
        }
    }

    boolean isConfigurationUpdateCall(Method method) {
        return ConfigurationUpdateAware.class.isAssignableFrom(method.getDeclaringClass());
    }

    void newConfigurationAvailable(Configuration configuration) {
        /**
         * Since the configuration update and proxyTarget update is not atomic, there is a chance for inconsistency here.
         */
        if (configuration != null && configuration.isValid()) {
            if (!(configuration.equals(lastKnownConfiguration))
                    && lastKnownConfiguration.getClass().isAssignableFrom(configuration.getClass())) {
                logger.debug("Received updated configuration {}. Replacing target proxy.", configuration);
                lastKnownConfiguration = configuration;
                createProxyTarget();
            } else {
                logger.debug("Received a configuration object {} that is equal to the previous configuration object {} or not of assignable type",
                        configuration, lastKnownConfiguration);
            }
        } else {
            logger.debug("Received configuration {} is null or not valid. So, using the old target proxy", configuration);
        }
    }

    void createProxyTarget() {
        final MethodInterceptor methodInterceptor = methodInterceptorFactory.createMethodInterceptor(lastKnownConfiguration);
        proxyTarget = new ProxyFactory(methodInterceptorFactory.getServiceInterface(), methodInterceptor)
                .getProxy(methodInterceptorFactory.getClassLoader());
    }

}
