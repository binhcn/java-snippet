package dev.binhcn.config;

import lombok.Data;

@Data
public class HttpClientConfig {

  private String url;
  private boolean useProxy;
  private long timeout;
}