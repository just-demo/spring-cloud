package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceOneController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private ServiceTwoClient serviceTwoClient;

    @GetMapping
    public Map<String, String> index() {
        logger.info("index");
        Map<String, String> one = singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port"));
        Map<String, String> two = serviceTwoClient.index();
        return merge(one, two);
    }

    private <K, V> Map<K, V> merge(Map<K, V> one, Map<K, V> two) {
        Map<K, V> result = new LinkedHashMap<>(one);
        result.putAll(two);
        return result;
    }
}
