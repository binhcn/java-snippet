package dev.binhcn;

import dev.binhcn.config.RemotePropertySourceLocator;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@EnableConfigurationProperties
@RequiredArgsConstructor
public class Application implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final RemotePropertySourceLocator locator;
    private final Environment environment;

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
    }

}
