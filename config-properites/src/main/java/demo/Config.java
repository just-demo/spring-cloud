package demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(
        value = {
                "classpath:config/config.properties",
                // TODO: why isn't is supported by spring out of the box?
                // TODO: this does not work with multiple profiles
                "classpath:config/config-${spring.profiles.active}.properties"},
        // To deal with unsupported profiles
        ignoreResourceNotFound = true)
@ConfigurationProperties("demo")
public class Config {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
