package dev.binhcn.util;

import dev.binhcn.config.MonitorConfig;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This is Monitor class.
 *
 * @author phuctt4
 */
@Slf4j
@Component
public class Monitor {

  private final MonitorConfig monitorConfig;
  private final MeterRegistry registry;

  /**
   * This is constructors.
   *
   * @param monitorConfig represents monitor config
   * @param registry represents meter registry
   */
  public Monitor(MonitorConfig monitorConfig, MeterRegistry registry) {
    this.monitorConfig = monitorConfig;
    this.registry = registry;
    registry.config().meterFilter(new MeterFilter() {
      @Override
      public DistributionStatisticConfig configure(
          Meter.Id id, @NonNull DistributionStatisticConfig config) {
        return DistributionStatisticConfig.builder()
            .percentilesHistogram(monitorConfig.isHistogram())
            .percentiles(monitorConfig.getPercentiles())
            .expiry(Duration.ofMinutes(monitorConfig.getExpiryMinutes()))
            .bufferLength(monitorConfig.getBufferLength())
            .sla(monitorConfig.getSlaInNanos())
            .build()
            .merge(config);
      }
    });
  }

  /**
   * This method is used for record metric.
   *
   * @param name represents method name
   * @param tags represents tags
   * @param startTime represents start time
   */
  public void record(String name, long startTime, Tag... tags) {
    try {
      if (startTime == 0) {
        return;
      }
      List<Tag> tagList = Arrays.asList(tags);
      registry.timer(name, tagList).record(Duration.ofMillis(System.currentTimeMillis() - startTime));
    } catch (Exception e) {
      log.error("Exception Occurs Timer On Metric Name {}", name, e);
    }
  }

  public void counter(String name, Tag tag) {
    counter(name, Collections.singletonList(tag));
  }

  /**
   * This method is used for count the number of request.
   *
   * @param name represents method name
   * @param tags represents tags
   */
  public void counter(String name, Iterable<Tag> tags) {
    try {
      registry.counter(name, tags).increment();
    } catch (Exception e) {
      log.error("Exception Occurs Timer On Metric Name {}", name, e);
    }
  }
}
