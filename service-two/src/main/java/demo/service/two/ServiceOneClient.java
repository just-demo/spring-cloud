package demo.service.two;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "service-one")
public interface ServiceOneClient {
    @GetMapping
    Map<String, String> get();
}
