package demo.v1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "load-balancer")
public interface LoadBalancerBaseClient {
    @GetMapping
    String index();
}
