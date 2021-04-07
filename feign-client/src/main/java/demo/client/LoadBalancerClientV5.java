package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//TODO: try Kubernetes
@FeignClient(name = "this-name-does-not-matter-5", url = "feign-service")
public interface LoadBalancerClientV5 {
    @GetMapping
    String index();
}
