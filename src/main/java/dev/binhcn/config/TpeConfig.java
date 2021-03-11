package dev.binhcn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "tpe-config")
@Data
//@RefreshScope
public class TpeConfig extends BaseExternalConfig {
    private List<String> redisNodes;
}