package dynaconfig;

/**
 * Abstract place holder representation of a configuration. Concrete implementation will be closely tied
 * to an implementation of {@link MethodInterceptorFactory}. <b>Only configuration details that could change
 * should be placed in this object.</b> The remaining configuration should be kept at the dynaconfig.MethodInterceptorFactory
 * instance. A dynaconfig.MethodInterceptorFactory instance will use a combination of the static configuration details it has
 * along with the dynaconfig.Configuration object passed to it to create a new MethodInterceptor.
 */
public interface Configuration {

    boolean isValid();

}
