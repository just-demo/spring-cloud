package demo.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RefreshScope
@RestController
public class GatewayConfigController {
    @Value("${test.url:}")
    private String testUrl;

    @GetMapping("/config")
    public Map<String, String> config() {
        return Collections.singletonMap("testUrl", testUrl);
    }
}
