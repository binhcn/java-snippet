package dev.binhcn.config.autoconfig;

import com.ecwid.consul.v1.ConsulClient;
import dev.binhcn.config.RemotePropertySourceLocator;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.io.IOException;
import java.util.Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

/**
 * @author thainq
 */
@Configuration
@ConditionalOnProperty(name = "host", prefix = "zalopay.starter.remote-config")
//@EnableConfigurationProperties(RemoteConfigProperties.class)
public class RemoteConfigBootstrapConfiguration {

    private final RemoteConfigProperties properties;

    public RemoteConfigBootstrapConfiguration(RemoteConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsulClient consulClient() {
        int agentPort = properties.getPort();
        String agentHost = !StringUtils.isEmpty(properties.getScheme()) ? properties.getScheme() + "://" + properties.getHost() : properties.getHost();
        return new ConsulClient(agentHost, agentPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public RemotePropertySourceLocator remotePropertySourceLocator(ConsulClient consulClient) throws IOException {

        String version = "";
        if (properties.isLoadByVersion()) {
            version = getSourceCodeVersion();
        }
            return new RemotePropertySourceLocator(consulClient, properties, version);
    }

    private String getSourceCodeVersion() throws IOException {
        Resource resource = new ClassPathResource("git.properties");
        Properties props = new Properties();
        PropertiesLoaderUtils.fillProperties(props, new EncodedResource(resource, "UTF-8"));
        return props.getProperty("git.build.version");
    }
}
