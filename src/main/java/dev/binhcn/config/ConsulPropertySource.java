package dev.binhcn.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import lombok.Getter;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.io.ByteArrayResource;

@Getter
public class ConsulPropertySource extends EnumerablePropertySource<ConsulClient> {

    private final Properties properties = new Properties();
    private final String context;
    private final RemoteConfigProperties configProperties;

    public ConsulPropertySource(String context, ConsulClient source, RemoteConfigProperties configProperties) {
        super(context, source);
        this.context = context;
        this.configProperties = configProperties;
    }

    public void init() {
        Response<GetValue> response = this.source.getKVValue(
            this.context, this.configProperties.getToken(), QueryParams.DEFAULT);

        final String value = response.getValue().getDecodedValue();
        if (value == null) {
            return;
        }
        this.properties.putAll(
            generateProperties(value, this.configProperties.getFormat()));
    }

    protected Properties generateProperties(String value, RemoteConfigProperties.Format format) {
        final Properties props = new Properties();

        if (format == RemoteConfigProperties.Format.PROPERTIES) {
            try {
                // Must use the ISO-8859-1 encoding because Properties.load(stream) expects it.
                props.load(new ByteArrayInputStream(value.getBytes(StandardCharsets.ISO_8859_1)));
            } catch (IOException e) {
                throw new IllegalArgumentException(value + " can't be encoded using ISO-8859-1");
            }
            return props;
        } else if (format == RemoteConfigProperties.Format.YAML) {
            final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ByteArrayResource(value.getBytes(StandardCharsets.UTF_8)));
            return yaml.getObject();
        }
        return props;
    }

    @Override
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    @Override
    public String[] getPropertyNames() {
        return this.properties.keySet().stream()
            .map(Object::toString).toArray(String[]::new);
    }
}
