package dev.binhcn.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zalopay.starter.remote-config")
public class RemoteConfigProperties {

    private String host;
    private String scheme;
    private int port;
    private String token;
    private String prefix;
    private String defaultContext;
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
