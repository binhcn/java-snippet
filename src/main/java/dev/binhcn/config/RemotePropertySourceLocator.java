package dev.binhcn.config;

import com.ecwid.consul.v1.ConsulClient;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
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
//        Set<String> contexts = new LinkedHashSet<>();

        List<String> contexts = new ArrayList<>();
        String defaultContext = properties.getPrefix()
            + properties.getProfileSeparator()
            + properties.getName()
            + properties.getProfileSeparator()
            + profile + "/public"
            + properties.getProfileSeparator()
            + properties.getDataKey();
        defaultContext = defaultContext.replaceAll("//", "/");
        contexts.add(defaultContext);

        String secretPath = properties.getPrefix()
            + properties.getProfileSeparator()
            + properties.getName()
            + properties.getProfileSeparator()
            + profile + "/secret"
            + properties.getProfileSeparator()
            + properties.getDataKey();
        secretPath = secretPath.replaceAll("//", "/");
        contexts.add(secretPath);
        return contexts;

//        List<String> filterContexts = new ArrayList<>();
//        List<String> hint = new ArrayList<>();
//        for (String context : contexts) {
//
//            if (this.properties.isLoadByVersion()) {
//                hint.add(context + version + "-<number>/" + this.properties.getDataKey());
//            }
//
//            hint.add(context + this.properties.getDataKey());
//            Response<List<String>> keysResp = this.consul.getKVKeysOnly(context, "/", this.properties.getToken());
//            if (keysResp.getValue() == null) {
//                continue;
//            }
//            List<String> keys = keysResp.getValue();
//            if (keys.isEmpty()) {
//                continue;
//            }
//            Pattern patternWithVersion = Pattern.compile(context + version + "-[0-9]+/" + this.properties.getDataKey() + "$");
//            Pattern patternNoVersion = Pattern.compile(context + this.properties.getDataKey() + "$");
//            List<String> filterKeys = new ArrayList<>();
//            for (String key : keys) {
//                if (patternWithVersion.matcher(key).matches() || patternNoVersion.matcher(key).matches()) {
//                    filterKeys.add(key);
//                }
//            }
//            if (filterKeys.isEmpty()) {
//                continue;
//            }
//            Collections.sort(filterKeys);
//            filterContexts.add(filterKeys.get(filterKeys.size() - 1));
//        }
//
//        if (filterContexts.isEmpty()) {
//            throw new RemoteConfigNotFoundException("Remote config not found. Please check in : " + hint.toString());
//        }
//
//        return filterContexts;
    }

    private String getSourceCodeVersion() throws IOException {
        Resource resource = new ClassPathResource("git.properties");
        Properties props = new Properties();
        PropertiesLoaderUtils.fillProperties(props, new EncodedResource(resource, "UTF-8"));
        return props.getProperty("git.build.version");
    }
}
