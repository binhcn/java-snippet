package dev.binhcn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MonitorConfig.
 *
 * @author anhnq7
 */
@Configuration
@ConfigurationProperties(prefix = "monitor-config")
@Data
public class MonitorConfig {

  private boolean enable;
  private int intervalInMiliseconds;
  private boolean histogram;
  private double percentiles;
  private int expiryMinutes;
  private int bufferLength;
  private long[] slaInNanos;
  private boolean stopTracking;
}
