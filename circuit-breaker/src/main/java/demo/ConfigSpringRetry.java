package demo;

import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

@Configuration
@Profile("springretry")
public class ConfigSpringRetry {
    @Bean
    public Customizer<SpringRetryCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
                // ...
                .build());
    }

    @Bean
    public Customizer<SpringRetryCircuitBreakerFactory> demoCustomizer() {
        BackOffPolicy backOffPolicy = new NoBackOffPolicy(); // same as default
        CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(
                new SimpleRetryPolicy(5)); // default is 3
        retryPolicy.setResetTimeout(3000); // default is 20000
        retryPolicy.setOpenTimeout(2000); // default is 5000
        return factory -> factory.configure(builder -> builder
                .backOffPolicy(backOffPolicy)
                .retryPolicy(retryPolicy)
                .build(), "demo-fail", "demo-delay");
    }
}
