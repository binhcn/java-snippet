package dev.binhcn.controller;

import dev.binhcn.entity.HttpReq;
import dev.binhcn.entity.HttpRes;
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
  private final Processor processor;

  @PostMapping(value = "",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handleRequest(@RequestBody String raw) {
    long startTime = System.currentTimeMillis();

    HttpReq httpReq = GsonUtil.fromJsonSnakeCase(raw, HttpReq.class);
//    processor.sendKafkaMessage(httpReq);
    processor.saveTransactionData();
    HttpRes httpRes = new HttpRes(httpReq.getOrderToken());
    monitor.record("HttpController", startTime);
    return ResponseEntity.ok(GsonUtil.toJsonStringSnakeCase(httpRes));
  }
}
