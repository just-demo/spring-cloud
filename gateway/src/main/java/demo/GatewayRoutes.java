package demo;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/google")
                        .filters(f -> f.setPath("/"))
                        // TODO: why does it change URL in browser?
                        .uri("https://www.google.com"))
                .route(p -> p.path("/test")
                        .filters(f -> f
                                .addRequestHeader("CustomHeaderName", "CustomHeaderValue")
                                .addRequestParameter("CustomParamName", "CustomParamValue"))
                        .uri("http://httpbin.org"))
                .route(p -> p.path("/one/**")
                        .filters(f -> f.rewritePath("/one/(.*)", "/$1"))
                        .uri("lb://service-one"))
                .route(p -> p.path("/two/**")
                        .filters(f -> f.rewritePath("/two/(.*)", "/$1"))
                        .uri("lb://service-two"))
                .build();
    }
}
