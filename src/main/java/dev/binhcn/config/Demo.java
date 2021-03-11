package dev.binhcn.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Demo {
    private final TpeConfig tpeConfig;

    @Scheduled(fixedDelay = 3000)
    public void scheduled() {
        System.out.println(tpeConfig.toString());
    }
}
