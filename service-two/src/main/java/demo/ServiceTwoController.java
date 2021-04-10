package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceTwoController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private ServiceOneClient serviceOneClient;

    @GetMapping
    public Map<String, String> index() throws Exception {
        logger.info("index");
        Map<String, String> response = serviceOneClient.index();
        response.put(environment.getProperty("spring.application.name"), environment.getProperty("local.server.port"));
        return response;
    }

    @GetMapping("/echo/{path}")
    public Map<String, String> echoPath(@PathVariable String path) {
        logger.info("echoPath: {}", path);
        Map<String, String> one = serviceOneClient.echoPath(path);
        Map<String, String> two = singletonMap(environment.getProperty("spring.application.name"), path);
        return merge(one, two);
    }

    @GetMapping("/echo")
    public Map<String, String> echoParam(@RequestParam("param") String param) {
        logger.info("echoParam: {}", param);
        Map<String, String> one = serviceOneClient.echoParam(param);
        Map<String, String> two = singletonMap(environment.getProperty("spring.application.name"), param);
        return merge(one, two);
    }

    @PostMapping("/echo")
    public Map<String, Map<String, String>> echoBody(@RequestBody Map<String, String> body) {
        logger.info("echoBody: {}", body);
        Map<String, Map<String, String>> one = serviceOneClient.echoBody(body);
        Map<String, Map<String, String>> two = singletonMap(environment.getProperty("spring.application.name"), body);
        return merge(one, two);
    }

    private <K, V> Map<K, V> merge(Map<K, V> one, Map<K, V> two) {
        Map<K, V> result = new LinkedHashMap<>(one);
        result.putAll(two);
        return result;
    }
}
