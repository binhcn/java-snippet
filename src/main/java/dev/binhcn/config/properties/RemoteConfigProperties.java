package dev.binhcn.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author thainq
 */
@Getter
@Setter
@ConfigurationProperties("zalopay.starter.remote-config")
@Component
public class RemoteConfigProperties {

    private String host = "";
    private String scheme = "";
    private int port = 7510;
    private String token = "";
    private String prefix = "";
    private String defaultContext = "";
    private String profileSeparator = "/";
    private Format format = Format.YAML;
    private String dataKey = "stable";
    private String name = "";
    private boolean loadByVersion = false;

    public enum Format {

        PROPERTIES,
        YAML
    }
}
