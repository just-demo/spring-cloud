package demo.config.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("config-client")
public class Config {
    private String demo;

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getDemo() {
        return demo;
    }
}
