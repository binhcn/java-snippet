package dev.binhcn.controller;

import dev.binhcn.async.KafkaProducer;
import dev.binhcn.entity.HttpReq;
import dev.binhcn.entity.HttpRes;
import dev.binhcn.entity.KafkaMessage;
import dev.binhcn.util.GsonUtil;
import dev.binhcn.util.Monitor;
import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HttpController {

  private final Monitor monitor;
  private final KafkaProducer kafkaProducer;

  private static final List<Tag> USER_BALANCE_TAGS = Arrays.asList(
      Tag.of("service", "um"), Tag.of("api", "user-balance"));

  @PostMapping(value = "",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handleAssetExchangeRequest(@RequestBody String raw) {
    long startTime = System.currentTimeMillis();

    HttpReq httpReq = GsonUtil.fromJsonSnakeCase(raw, HttpReq.class);
    HttpRes httpRes = new HttpRes(httpReq.getOrderToken());
    KafkaMessage kafkaMessage = new KafkaMessage(httpReq.getOrderToken(), System.currentTimeMillis());
    kafkaProducer.sendMessage(kafkaMessage);

    monitor.record("processing", USER_BALANCE_TAGS, startTime);
    return ResponseEntity.ok(GsonUtil.toJsonStringSnakeCase(httpRes));
  }
}
