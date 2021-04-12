package demo;

import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class GatewayConfiguration {
    private final Logger logger = getLogger(getClass());

    @Bean
    public RouteLocator demoRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/google")
                        .filters(f -> f.setPath("/"))
                        // TODO: why does it change URL in browser?
                        .uri("https://www.google.com"))
                .route(p -> p.path("/test")
                        .filters(f -> f
                                .addRequestHeader("demo", "header")
                                .addRequestParameter("demo", "param"))
                        .uri("http://httpbin.org"))
                .route(p -> p.path("/one/**")
                        // when stripping prefix make sure there is always a leading slash left
                        .filters(f -> f.rewritePath("/one(/(.*))?", "/$2"))
                        .uri("lb://service-one"))
                .route(p -> p.path("/two/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://service-two"))
                .build();
    }

    @Bean
    public GlobalFilter demoGlobalFilter() {
        return (exchange, chain) -> {
            logger.info("Request: {}", exchange.getRequest().getPath());
            return chain.filter(exchange);
        };
    }
}
