package demo.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("v1")
@RequestMapping("/v1")
public class LoadBalancerController {

    @Autowired
    private LoadBalancerBaseClient loadBalancerBaseClient;

    @GetMapping
    public String index() {
        return loadBalancerBaseClient.index();
    }
}
