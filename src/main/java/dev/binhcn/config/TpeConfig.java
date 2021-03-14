package dev.binhcn.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "tpe-config")
@Data
@ToString(callSuper = true)
public class TpeConfig extends BaseExternalConfig {
    private List<String> redisNodes;
}