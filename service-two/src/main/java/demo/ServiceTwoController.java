package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.net.InetAddress.getLocalHost;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceTwoController {
    private final Logger logger = getLogger(getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private ServiceOneClient serviceOneClient;

    @GetMapping
    public Map<String, String> index() throws Exception {
        logger.info("index");
        Map<String, String> response = serviceOneClient.index();
        response.put(getLocalHost().getHostName(), environment.getProperty("local.server.port"));
        return response;
    }

    @GetMapping("/{text}")
    public Map<String, String> echo(@PathVariable String text) throws Exception {
        logger.info("echo: {}", text);
        Map<String, String> response = serviceOneClient.echo(text);
        response.put(getLocalHost().getHostName(), text);
        return response;
    }
}
