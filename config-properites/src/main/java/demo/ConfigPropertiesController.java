package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class ConfigPropertiesController {

    @Autowired
    private Config config;

    @Autowired
    private Environment environment;

    @Value("${demo.value}")
    private String value;

    @GetMapping
    public List<String> get() {
        return asList(config.getValue(), environment.getProperty("demo.value"), value);
    }
}
