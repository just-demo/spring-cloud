package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RestController
public class EurekaClientController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping
    public Object get() {
        return discoveryClient.getServices().stream()
                .map(discoveryClient::getInstances)
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
