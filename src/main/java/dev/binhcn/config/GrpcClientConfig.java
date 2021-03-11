package dev.binhcn.config;

import lombok.Data;

@Data
public class GrpcClientConfig {

  private boolean async;
  private String grpcHost;
  private int grpcPort;
  private boolean useSsl;
  private long timeoutInSeconds;
  private int poolSize;
}