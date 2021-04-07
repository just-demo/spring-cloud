package demo.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Profile("v2")
@RequestMapping("/v2")
public class LoadBalancerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String index() {
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("load-balancer")
                .build()
                .toString();
        return restTemplate.getForObject(url, String.class);
    }
}
