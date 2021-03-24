package dev.binhcn.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import dev.binhcn.config.properties.RemoteConfigProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

/**
 * @author thainq
 */
public class ConsulPropertySource extends EnumerablePropertySource<ConsulClient> {

    private final Map<String, Object> properties = new LinkedHashMap<>();

    private final String context;

    private final RemoteConfigProperties configProperties;

    private Long initialIndex;

    public ConsulPropertySource(String context, ConsulClient source, RemoteConfigProperties configProperties) {
        super(context, source);
        this.context = context;
        this.configProperties = configProperties;
    }

    public void init() {
        Response<List<GetValue>> response = this.source.getKVValues(this.context, this.configProperties.getToken(),
                QueryParams.DEFAULT);

        this.initialIndex = response.getConsulIndex();

        final List<GetValue> values = response.getValue();
        RemoteConfigProperties.Format format = this.configProperties.getFormat();
        switch (format) {
            case PROPERTIES:
            case YAML:
                parsePropertiesWithNonKeyValueFormat(values, format);
        }
    }

    public Long getInitialIndex() {
        return this.initialIndex;
    }

    /**
     * Parses the properties using the format which is not a key value style i.e., either
     * java properties style or YAML style.
     *
     * @param values values to parse
     * @param format format in which the values should be parsed
     */
    protected void parsePropertiesWithNonKeyValueFormat(List<GetValue> values, RemoteConfigProperties.Format format) {
        if (values == null) {
            return;
        }

        for (GetValue getValue : values) {
            parseValue(getValue, format);
        }
    }

    protected void parseValue(GetValue getValue, RemoteConfigProperties.Format format) {
        String value = getValue.getDecodedValue();
        if (value == null) {
            return;
        }

        Properties props = generateProperties(value, format);

        for (Map.Entry entry : props.entrySet()) {
            this.properties.put(entry.getKey().toString(), entry.getValue());
        }
    }

    protected Properties generateProperties(String value, RemoteConfigProperties.Format format) {
        final Properties props = new Properties();

        if (format == RemoteConfigProperties.Format.PROPERTIES) {
            try {
                // Must use the ISO-8859-1 encoding because Properties.load(stream)
                // expects it.
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

    protected Map<String, Object> getProperties() {
        return this.properties;
    }

    protected RemoteConfigProperties getConfigProperties() {
        return this.configProperties;
    }

    protected String getContext() {
        return this.context;
    }

    @Override
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    @Override
    public String[] getPropertyNames() {
        Set<String> strings = this.properties.keySet();
        return strings.toArray(new String[strings.size()]);
    }

}
