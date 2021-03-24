package dev.binhcn;

import dev.binhcn.config.RemotePropertySourceLocator;
import dev.binhcn.config.autoconfig.RedisConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@EnableConfigurationProperties
@RequiredArgsConstructor
public class Application implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final RemotePropertySourceLocator locator;
    private final Environment environment;
    private final RedisConfig redisConfig;
    private final ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//        c.setLocation(new ClassPathResource("git.properties"));
//        c.setIgnoreResourceNotFound(true);
//        c.setIgnoreUnresolvablePlaceholders(true);
//        return c;
//    }

    @Override
    public void run(String...args) throws Exception {
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.",
            Arrays.toString(args));
        locator.locate(environment);
//        System.out.println(redisConfig.toString());

        Map<String, Object> map = new HashMap();
        for(Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
    }

}
