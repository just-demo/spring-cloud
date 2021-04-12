package demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient("service-two")
public interface ServiceTwoClient {
    @GetMapping
    Map<String, String> index();
}
