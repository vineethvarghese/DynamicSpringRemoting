package dynaconfig;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Factory implementation for creating {@link MethodInterceptor} with a given {@link Configuration} instance
 */
public interface MethodInterceptorFactory<C extends Configuration> {

    MethodInterceptor createMethodInterceptor(C configuration);

    /**
     * The default configuration type is used to determine the type of the updated Configuration object.
     * @return
     */
    C defaultConfiguration();

    Class getServiceInterface();

    /**
     * Need this to use the same classloader which might have been used in the MethodInteceptor
     * @return
     */
    ClassLoader getClassLoader();
}
