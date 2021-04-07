package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class LoadBalancerController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private Environment environment;

    @GetMapping
    public Map<?, ?> index() {
        // To keep simple let's lookup the same service
        ServiceInstance instance = loadBalancer.choose("load-balancer");
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(instance.getHost())
                .port(instance.getPort())
                .path("info")
                .build()
                .toString();
        return new RestTemplate().getForObject(url, Map.class);
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        logger.info("info");
        return singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port"));
    }
}
