package demo;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Duration;

@Configuration
@Profile("resilience4j")
public class ConfigResilience4j {
    // Optionally can override default behaviour
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultResilience4JCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                // ...
                .build());
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> demoResilience4JCustomizer() {
        return factory -> factory.configure(builder -> builder
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .minimumNumberOfCalls(5) // default is 100
                        .waitDurationInOpenState(Duration.ofSeconds(3)) // default is 60s
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(2)) // default is 1s
                        .build()), "demo-fail", "demo-delay");
    }
}
