package dev.binhcn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//https://www.baeldung.com/spring-boot-minikube
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("")
    public String helloWorld() throws UnknownHostException {

        return "Hello from " + InetAddress.getLocalHost().getHostName();
    }

}
