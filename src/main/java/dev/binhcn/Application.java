package dev.binhcn;

import dev.binhcn.config.TpeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RefreshScope // if @Value, please must use, else @ConfigurationProperties doesn't need to attach
@RestController
@RequiredArgsConstructor
public class Application {

    @Value("${welcome.message}")
    String welcomeText;

    private final TpeConfig tpeConfig;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String welcomeText() {
        return welcomeText + tpeConfig.toString();
    }

}
