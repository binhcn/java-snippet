package dev.binhcn.controller;

import dev.binhcn.async.KafkaProducer;
import dev.binhcn.database.TransactionLog;
import dev.binhcn.database.TransactionRepository;
import dev.binhcn.entity.HttpReq;
import dev.binhcn.entity.KafkaMessage;
import dev.binhcn.util.Monitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Processor {

  private final KafkaProducer kafkaProducer;
  private final TransactionRepository transactionRepository;
  private final Monitor monitor;

  public void sendKafkaMessage(HttpReq httpReq) {
    KafkaMessage kafkaMessage = new KafkaMessage(httpReq.getOrderToken(), System.currentTimeMillis());
    kafkaProducer.sendMessage(kafkaMessage);
  }

  public void saveTransactionData() {
    TransactionLog transactionLog = TransactionLog.getData();
    long startTime = System.currentTimeMillis();
    transactionRepository.save(transactionLog);
    monitor.record("Processor", startTime);
  }
}
