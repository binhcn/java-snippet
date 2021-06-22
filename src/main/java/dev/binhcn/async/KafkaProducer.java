package dev.binhcn.async;

import dev.binhcn.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

  private final KafkaTemplate kafkaTemplate;

  @Value("${kafka.demo.topic}")
  private String topic;

  public void sendMessage(Object data) {
    try {
      String messageStr = GsonUtil.toJsonString(data);
      ListenableFuture future = kafkaTemplate.send(topic, messageStr);

      future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
        @Override
        public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
          log.info("Send kafka success message={} - topic={}", messageStr, topic);
        }

        @Override
        public void onFailure(Throwable throwable) {
          log.error("Send kafka message {} Error: {}", messageStr, throwable);
        }
      });

    } catch (Exception e) {
      log.error("Send Kafka message exception: ", e);
    }
  }
}
