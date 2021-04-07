package demo.v3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Profile("v3")
@RequestMapping("/v3")
public class LoadBalancerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String index() {
        ServiceInstance instance = loadBalancerClient.choose("load-balancer");
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(instance.getHost())
                .port(instance.getPort())
                .build()
                .toString();
        return restTemplate.getForObject(url, String.class);
    }
}
