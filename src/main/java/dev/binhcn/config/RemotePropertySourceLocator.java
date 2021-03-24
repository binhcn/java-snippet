package dev.binhcn.config;

import com.ecwid.consul.v1.ConsulClient;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

/**
 * @author thainq
 */
public class RemotePropertySourceLocator implements PropertySourceLocator {

    private static final String PROPERTIES_NAME = "consul";

    private final ConsulClient consul;
    private final RemoteConfigProperties properties;
    private final String version;

    public RemotePropertySourceLocator(ConsulClient consul, RemoteConfigProperties properties, String version) {
        this.consul = consul;
        this.properties = properties;
        this.version = version;
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
                ConsulPropertySource propertySource = new ConsulPropertySource(context, this.consul, this.properties);
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
        // prefix + properties + defaultContext
        String defaultContext = properties.getPrefix()
            + properties.getProfileSeparator()
            + properties.getName()
            + properties.getProfileSeparator()
            + profile + "/public"
            + properties.getProfileSeparator()
            + properties.getDataKey();
        defaultContext = defaultContext.replaceAll("//", "/");
        contexts.add(defaultContext);

        // prefix + properties + name
        String nameContex = properties.getPrefix()
            + properties.getProfileSeparator()
            + properties.getName()
            + properties.getProfileSeparator()
            + profile + "/secret"
            + properties.getProfileSeparator()
            + properties.getDataKey();
        nameContex = nameContex.replaceAll("//", "/");
        contexts.add(nameContex);
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

    private String getContext(String prefix, String context) {
        if (StringUtils.isEmpty(prefix)) {
            return context;
        }
        return prefix + "/" + context;
    }

    private void addProfiles(Set<String> contexts, String baseContext, List<String> profiles, String suffix) {
        for (String profile : profiles) {
            contexts.add(baseContext + properties.getProfileSeparator() + profile + suffix);
        }
    }
}
