package demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "service-one")
public interface ServiceOneClient {
    @GetMapping
    Map<String, String> index();

    @GetMapping("/echo/{path}")
    Map<String, String> echoPath(@PathVariable String path);

    @GetMapping("/echo")
    Map<String, String> echoParam(@RequestParam("param") String param);

    @PostMapping("/echo")
    Map<String, Map<String, String>> echoBody(@RequestBody Map<String, String> body);
}
