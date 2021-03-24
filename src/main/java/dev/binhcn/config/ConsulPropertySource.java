package dev.binhcn.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

/**
 * @author mramach
 *
 */
public class ConsulPropertySource extends EnumerablePropertySource<Properties> {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConsulPropertySourceLocator.class);
    
    private Properties source = new Properties();

    public ConsulPropertySource(ConsulProperties configuration, ConsulAdapter consulAdapter) {
        
        super("consul-property-source-" + UUID.randomUUID().toString());
        
        try {
            
            configuration.getPaths().stream()
                .map(p -> consulAdapter.get(p, true))
                    .reduce(new LinkedHashMap<String, String>(), (l, r) -> {l.putAll(r); return l;})
                        .entrySet().forEach(this::setProperty);
        } catch (RuntimeException e) {
            
            if(configuration.isFailFast()) {
                throw e;
            }
            
            LOG.info("Unable to fetch properties from resource {}.", configuration.getEndpoint());
            
        }
        
    }

    private void setProperty(Entry<String, String> e) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        Resource resource = new ByteArrayResource(e.getValue().getBytes());
        factory.setResources(resource);
        Properties properties = factory.getObject();
        source.putAll(properties);
    }
    
    private String pathToProperty(String path) {
        return path.replace('/', '.');
    }
    
    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[source.size()]);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }

}