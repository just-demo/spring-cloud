package demo;

import demo.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
public class LoadBalancerController {

    @Autowired
    private Environment environment;

    @Autowired
    private LoadBalancerClientV1 loadBalancerClientV1;

    @Autowired
    private LoadBalancerClientV2 loadBalancerClientV2;

    @Autowired
    private LoadBalancerClientV3 loadBalancerClientV3;

    @Autowired
    private LoadBalancerClientV4 loadBalancerClientV4;

    @Autowired
    private LoadBalancerClientV5 loadBalancerClientV5;

    @GetMapping("{version}")
    public String balance(@PathVariable String version) {
        switch (version) {
            case "v1":
                return loadBalancerClientV1.index();
            case "v2":
                return loadBalancerClientV2.index();
            case "v3":
                return loadBalancerClientV3.index();
            case "v4":
                return loadBalancerClientV4.index();
            case "v5":
                return loadBalancerClientV5.index();
            default:
                return "Unsupported version: " + version;
        }
    }

    @GetMapping
    public Map<String, String> index() {
        return singletonMap(
                environment.getProperty("spring.application.name"),
                environment.getProperty("local.server.port"));
    }
}
