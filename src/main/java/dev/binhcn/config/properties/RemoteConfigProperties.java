package dev.binhcn.config.properties;

import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "zalopay.starter.remote-config")
//@Component
public class RemoteConfigProperties {

    private String host = "10.60.45.6";
    private String scheme = "http";
    private int port = 8500;
    private String token = "1bfe383f-056e-44c2-7221-30928133e2c2";
    private String prefix = "payment-engine/cashier";
    private String defaultContext = "";
    private String profileSeparator = "/";
    private Format format = Format.YAML;
    private String dataKey = "stable";
    private String name = "cashier-v16";
    private boolean loadByVersion = false;

    public enum Format {

        PROPERTIES,
        YAML
    }

    @PostConstruct
    public void init() {
        System.out.println(name);
    }
}
