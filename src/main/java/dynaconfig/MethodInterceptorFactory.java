package dynaconfig;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Factory implementation for creating {@link MethodInterceptor} with a given {@link Configuration} instance
 */
public interface MethodInterceptorFactory<C extends Configuration> {

    MethodInterceptor createMethodInterceptor(C configuration);

    C defaultConfiguration();

    Class getServiceInterface();
}
