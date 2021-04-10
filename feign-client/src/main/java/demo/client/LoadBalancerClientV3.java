package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// For Docker
// TODO: try Kubernetes
@FeignClient(name = "this-name-does-not-matter-3", url = "${feign-service-url:}")
public interface LoadBalancerClientV3 {
    @GetMapping
    String index();
}
