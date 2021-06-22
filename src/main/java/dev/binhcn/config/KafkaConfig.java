package dev.binhcn.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The type Kafka config.
 *
 * @author anhnq7
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.kafka")
@EnableAsync
public class KafkaConfig {

  private String bootstrapServers;
  private String acks;
  private int retries;
  private int requestTimeout;
  private int retryBackoff;

  /**
   * Kafka ProducerFactory.
   *
   * @return DefaultKafkaProducerFactory
   */
  @Bean
  public ProducerFactory<String, String> defaultProducerFactory() {
    return producerFactory(bootstrapServers);
  }

  @Bean(name = "peKafka")
  public KafkaTemplate<String, String> peKafkaTemplate() {
    return new KafkaTemplate<>(defaultProducerFactory());
  }

  private ProducerFactory<String, String> producerFactory(String bootstrapServers){
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
    configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
    configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retryBackoff);
    configProps.put(ProducerConfig.ACKS_CONFIG, acks);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    return new DefaultKafkaConsumerFactory<>(config);
  }

  /**
   * ConcurrentKafkaListenerContainerFactory.
   *
   * @return ConcurrentKafkaListenerContainerFactory
   */
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory();
    factory.setConsumerFactory(consumerFactory());
    factory.setBatchListener(false);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    return factory;
  }
}