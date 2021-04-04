package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceOneController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private Environment environment;

    @GetMapping
    public Map<String, String> get() {
        logger.info("get");
        return singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port")
        );
    }
}
