package dev.binhcn;

import dev.binhcn.model.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final RedisConfig redisConfig;
    private final Environment env;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

    }
}
