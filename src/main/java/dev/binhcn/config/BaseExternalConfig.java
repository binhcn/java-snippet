package dev.binhcn.config;

import lombok.Data;

import java.util.Map;

@Data
public class BaseExternalConfig {

  private String clientId;
  private String clientKey;
  private String mode;
  private int poolSize;
  private double rateLimit;
  private GrpcClientConfig grpcClient;
  private HttpClientConfig httpClient;
  private Map<String, String> methodNames;
}