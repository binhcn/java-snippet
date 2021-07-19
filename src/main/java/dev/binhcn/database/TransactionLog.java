package dev.binhcn.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("transactionlog")
public class TransactionLog {
  @Id
  private long id;
  private String fieldColumn;
  private String field1;
  private String field2;
  private String field3;

  public static TransactionLog getData() {
    TransactionLog transactionLog = new TransactionLog();
    transactionLog.setFieldColumn("binhcn");
    transactionLog.setField1("binhcn");
    transactionLog.setField2("binhcn");
    transactionLog.setField3("binhcn");
    return transactionLog;
  }
}
