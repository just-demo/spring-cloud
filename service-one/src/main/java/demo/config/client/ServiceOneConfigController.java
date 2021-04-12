package demo.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RefreshScope
@RestController
public class ServiceOneConfigController {
    @Autowired
    private Environment environment;

    @Value("${demo.value:}")
    private String value;

    @GetMapping("/config")
    public List<String> config() {
        return asList(environment.getProperty("demo.value"), value);
    }
}
