package dev.binhcn.async;

import dev.binhcn.entity.KafkaMessage;
import dev.binhcn.util.GsonUtil;
import dev.binhcn.util.Monitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

  private final Monitor monitor;

  @KafkaListener(
      topics = "${kafka.demo.topic}",
      groupId = "${kafka.demo.group-id}",
      concurrency = "${kafka.demo.concurrency}")
  public void consume(String messageStr, Acknowledgment acknowledgment) {
    try {
      KafkaMessage kafkaMessage = GsonUtil.fromJsonString(messageStr, KafkaMessage.class);
      monitor.record("kafka", kafkaMessage.getStartTime());
      log.info("Demo Listener-consume message={}", messageStr);
      acknowledgment.acknowledge();
    } catch (Exception e) {
      log.error("Demo Listener-consume message failed - Message={} - EXCEPTION: ",
          messageStr, e);
    }
  }

}