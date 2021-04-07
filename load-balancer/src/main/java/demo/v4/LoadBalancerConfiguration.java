package demo.v4;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@Profile("v4")
@LoadBalancerClient(name = "this-name-does-not-matter",
        configuration = LoadBalancerConfiguration.LoadBalancerClientConfiguration.class)
public class LoadBalancerConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // This configuration does not require service discovery
    static class LoadBalancerClientConfiguration {
        @Bean
        ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new ServiceInstanceListSupplier() {
                @Override
                public Flux<List<ServiceInstance>> get() {
                    return Flux.just(asList(
                            new DefaultServiceInstance("load-balancer-1", "load-balancer", "www.google.com", 80, false),
                            new DefaultServiceInstance("load-balancer-2", "load-balancer", "httpbin.org", 80, false),
                            new DefaultServiceInstance("load-balancer-3", "load-balancer", "example.com", 80, false)
                    ));
                }

                @Override
                public String getServiceId() {
                    return "this-service-id-does-not-matter";
                }
            };
        }
    }
}
