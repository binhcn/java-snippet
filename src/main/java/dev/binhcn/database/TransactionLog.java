package dev.binhcn.database;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@JsonNaming
@Table("transaction-log")
public class TransactionLog {
  @Id
  private Long field1;
  private String field2;
  private String field3;
  private String field4;
  private String field5;


  public static TransactionLog getData() {
    TransactionLog transactionLog = new TransactionLog();
    transactionLog.setField1(123456L);
    transactionLog.setField2("binhcn");
    transactionLog.setField3("binhcn");
    transactionLog.setField4("binhcn");
    transactionLog.setField5("binhcn");
    return transactionLog;
  }
}
