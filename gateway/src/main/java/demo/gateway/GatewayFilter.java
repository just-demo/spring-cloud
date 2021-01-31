package demo.gateway;

import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GatewayFilter implements GlobalFilter {
    private final Logger logger = getLogger(getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Request: {}", exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
}
