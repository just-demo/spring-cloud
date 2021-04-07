package demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("feign-client")
public interface LoadBalancerClientV1 {
    @GetMapping
    String index();
}
