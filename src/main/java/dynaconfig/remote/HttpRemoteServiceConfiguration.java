package dynaconfig.remote;

import dynaconfig.Configuration;
import org.springframework.util.StringUtils;

/**
 * Date: 12/30/11
 * Time: 3:52 PM
 */
public class HttpRemoteServiceConfiguration implements Configuration {

    private String serviceUrl;

    public HttpRemoteServiceConfiguration() {
    }

    public HttpRemoteServiceConfiguration(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public boolean isValid() {
        //Not testing for the validity of the url format yet
        return StringUtils.hasText(serviceUrl);
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString() {
        return "HttpRemoteServiceVariableConfiguration{" +
                "serviceUrl='" + serviceUrl + '\'' +
                '}';
    }
}
