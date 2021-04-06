package demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "service-one")
public interface ServiceOneClient {
    @GetMapping
    Map<String, String> index();

    @GetMapping("/{text}")
    Map<String, String> echo(@PathVariable String text);
}
