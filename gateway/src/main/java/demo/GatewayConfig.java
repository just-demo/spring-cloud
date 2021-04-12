package demo;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator demoLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/test")
                        .filters(f -> f
                                .addRequestHeader("demo", "header")
                                .addRequestParameter("demo", "param")
                                .setPath("/anything"))
                        .uri("http://httpbin.org"))
                .route(p -> p.path("/one/**").filters(f -> f.stripPrefix(1)).uri("lb://service-one"))
                .route(p -> p.path("/two/**").filters(f -> f.stripPrefix(1)).uri("lb://service-two"))
                .build();
    }

    @Bean
    public GlobalFilter demoFilter() {
        return (exchange, chain) -> {
            // Do something useful
            return chain.filter(exchange);
        };
    }
}
