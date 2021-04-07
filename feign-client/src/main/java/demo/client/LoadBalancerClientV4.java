package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//TODO: try Kubernetes
@FeignClient(name = "this-name-does-not-matter-3", url = "http://feign-service")
public interface LoadBalancerClientV4 {
    @GetMapping
    String index();
}
