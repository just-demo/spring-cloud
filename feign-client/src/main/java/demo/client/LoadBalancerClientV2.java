package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "this-name-does-not-matter-2", url = "localhost:8080")
public interface LoadBalancerClientV2 {
    @GetMapping
    String index();
}
