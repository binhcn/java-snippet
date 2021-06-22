package dev.binhcn.entity;

import lombok.Getter;

@Getter
public class KafkaMessage {
  private String orderToken;
  private long startTime;

  public KafkaMessage(String orderToken, long startTime) {
    this.orderToken = orderToken;
    this.startTime = startTime;
  }
}
