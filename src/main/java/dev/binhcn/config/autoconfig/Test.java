package dev.binhcn.config.autoconfig;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;

public class Test {
  public static void main(String[] args) {
    ConsulClient client = new ConsulClient("10.60.45.6", 8500);
    Response<GetValue> keyValueResponse = client.getKVValue(
        "huyda1/test-app/nginx-backend/qc/public/2.0.0-1/stable",
        "1bfe383f-056e-44c2-7221-30928133e2c2");

//    ConsulClient client = new ConsulClient("http://localhost", 8500);
//    Response<GetValue> keyValueResponse = client.getKVValue("payment-engine/cashier-v16/loadtest/stable");
    System.out.println(keyValueResponse.getValue().getKey() + ": " + keyValueResponse.getValue().getDecodedValue());
  }
}
