package dev.binhcn.config.autoconfig;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "redis")
@Component
@Data
public class RedisConfig {

  private boolean cluster;
  private int scanInterval;
  private int slaveConnectionMinimumIdleSize;
  private int slaveConnectionPoolSize;
  private int masterConnectionMinimumIdleSize;
  private int masterConnectionPoolSize;
  private int idleConnectionTimeout;
  private int connectTimeout;
  private int responseTimeout;
  private int retryAttempts;
  private int retryInterval;
  private int reconnectionTimeout;
  private int failedAttempts;
  private String readMode;
  private boolean kryoCodec;
  private List<String> redisNodes;
  private int cacheExpireMinute;
  private int lockAcquireTimeMillisecond;
  private String codecType;

  @PostConstruct
  public void init() {
    System.out.println();
  }
}