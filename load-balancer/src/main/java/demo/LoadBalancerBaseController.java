package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * To keep simple scaled controller will be part of load-balancer service itself
 */
@RestController
public class LoadBalancerBaseController {

    @Autowired
    private Environment environment;

    @GetMapping
    public Map<String, String> index() {
        return singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port"));
    }
}
