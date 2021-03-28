package dev.binhcn.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.cloud.consul.config")
public class ConsulClientConfig {
    @Value("${spring.cloud.consul.host}")
    private String host;
    @Value("${spring.cloud.consul.port}")
    private int port;
    private String aclToken;
    private String prefix;
    private String profileSeparator;
    private Format format;
    private String dataKey;
    private String name;
    private boolean loadByVersion;

    public enum Format {
        PROPERTIES,
        YAML
    }
}
