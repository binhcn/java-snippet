package dev.binhcn.config;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author mramach
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConsulAdapter {
    
    @Autowired
    private ConsulProperties configuration;
    
    public ConsulAdapter(ConsulProperties configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets all properties located at the specified path.
     * 
     * @param path The path to use when resolving properties.
     * @param recurse True if recursive searching should be enabled, false otherwise.
     * @return Map of all properties located under the provided path.
     */
    public Map<String, String> get(String path, boolean recurse) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Consul-Token", "1bfe383f-056e-44c2-7221-30928133e2c2");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List> response = new RestTemplate().exchange(
            String.format(configuration.getEndpoint() + "/kv/{path}?%s", (recurse ? "recurse" : "")),
            HttpMethod.GET,
            request,
            List.class,
            path
        );
        
//        ResponseEntity<List> response = new RestTemplate().getForEntity(
//                String.format(configuration.getEndpoint() + "/kv/{path}?%s", (recurse ? "recurse" : "")),
//                    List.class, path);

        List<Map<String, Object>> values = (List<Map<String, Object>>)response.getBody();
        Map<String, String> properties = new LinkedHashMap<String, String>();
        
        values.stream()
                .filter(i -> i.get("Value") != null)
                    .forEach(i -> {
                        
            properties.put((String) i.get("Key"), 
                    new String(Base64.getDecoder().decode((String) i.get("Value"))));
            
        });
        
        return properties;
        
    }
    
}