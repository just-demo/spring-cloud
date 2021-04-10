package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceOneController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private Environment environment;

    // fetch('/').then(r => r.text()).then(console.log)
    @GetMapping
    public Map<String, String> index() {
        logger.info("index");
        return singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port")
        );
    }

    // fetch('/echo/demo-path').then(r => r.text()).then(console.log)
    @GetMapping("/echo/{path}")
    public Map<String, String> echoPath(@PathVariable String path) {
        logger.info("echoPath: {}", path);
        return singletonMap(environment.getProperty("spring.application.name"), path);
    }

    // fetch('/echo?param=demo-param').then(r => r.text()).then(console.log)
    @GetMapping("/echo")
    public Map<String, String> echoParam(@RequestParam("param") String param) {
        logger.info("echoParam: {}", param);
        return singletonMap(environment.getProperty("spring.application.name"), param);
    }

    // fetch('/echo', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({demo: 'body'})}).then(r => r.text()).then(console.log)
    @PostMapping("/echo")
    public Map<String, Map<String, String>> echoBody(@RequestBody Map<String, String> body) {
        logger.info("echoBody: {}", body);
        return singletonMap(environment.getProperty("spring.application.name"), body);
    }
}
