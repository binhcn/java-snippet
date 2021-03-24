package dev.binhcn.config.autoconfig;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class Test {
  public static void main(String[] args) {
    ConsulClient client = new ConsulClient("10.60.45.6", 8500);
    Response<GetValue> keyValueResponse = client.getKVValue(
        "payment-engine/cashier/cashier-v16/loadtest/stable",
        "1bfe383f-056e-44c2-7221-30928133e2c2");
//    System.out.println(keyValueResponse.getValue().getKey() + ": " + keyValueResponse.getValue().getDecodedValue());
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    Resource resource = new ByteArrayResource(keyValueResponse.getValue().getDecodedValue().getBytes());
    factory.setResources(resource);
    Properties properties = factory.getObject();
  }
}
