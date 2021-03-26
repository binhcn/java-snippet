package dev.binhcn.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@EnableConfigurationProperties(RemoteConfigProperties.class)
public class RemotePropertySourceLocator implements PropertySourceLocator {

    private static final String PROPERTIES_NAME = "consul";

    private final ConsulClient consul;
    private final RemoteConfigProperties properties;

    public RemotePropertySourceLocator(RemoteConfigProperties properties) {
        this.properties = properties;
        this.consul = new ConsulClient(properties.getHost(), properties.getPort());
    }

    @Override
    public PropertySource<?> locate(Environment environment) {

        if (environment instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
            List<String> profiles = Arrays.asList(env.getActiveProfiles());
            if (profiles.isEmpty()) {
                throw new RuntimeException("No profile available.");
            }

            List<String> contexts = getAutomaticContexts(profiles.get(0));

            CompositePropertySource composite = new CompositePropertySource(PROPERTIES_NAME);
            for (String context : contexts) {
                ConsulPropertySource propertySource =
                    new ConsulPropertySource(context, this.consul, this.properties);
                propertySource.init();
                composite.addPropertySource(propertySource);
            }
            return composite;
        }
        return null;
    }

    private List<String> getAutomaticContexts(String profile) {
        String profilePath = new StringBuilder()
            .append(properties.getPrefix())
            .append(properties.getProfileSeparator())
            .append(properties.getName())
            .append(properties.getProfileSeparator())
            .append(profile)
            .toString();
        String publicConfig = getPublicConfigVersion(profilePath + "/public/");

        List<String> contexts = new ArrayList<>();
        String defaultContext = profilePath + "/public"
            + properties.getProfileSeparator()
            + publicConfig
            + properties.getProfileSeparator()
            + properties.getDataKey();
        defaultContext = defaultContext.replaceAll("//", "/");
        contexts.add(defaultContext);

        String secretPath = profilePath + "/secret"
            + properties.getProfileSeparator()
            + properties.getDataKey();
        secretPath = secretPath.replaceAll("//", "/");
        contexts.add(secretPath);
        return contexts;
    }

    private String getPublicConfigVersion(String context) {
        Response<List<String>> keysResp = this.consul.getKVKeysOnly(context, "/", this.properties.getToken());
        if (keysResp.getValue() == null) {
            return null;
        }
        List<String> keys = keysResp.getValue();
        if (keys.isEmpty()) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}-\\d{1,3}");
        int maxConfigVersion = 1;
        String srcCodeVersion = getSrcCodeVersion();
        for (String key: keys) {
            Matcher matcher = pattern.matcher(key);
            if (matcher.find() && matcher.group().startsWith(srcCodeVersion)) {
                int configVersion = Integer.parseInt(
                    matcher.group().replace(srcCodeVersion + "-", ""));
                if (maxConfigVersion < configVersion) maxConfigVersion = configVersion;
            }
        }
        return srcCodeVersion + "-" + maxConfigVersion;
    }

    private String getSrcCodeVersion() {
        Resource resource = new ClassPathResource("git.properties");
        Properties props = new Properties();
        try {
            PropertiesLoaderUtils.fillProperties(
                props, new EncodedResource(resource, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("git.build.version");
    }
}
