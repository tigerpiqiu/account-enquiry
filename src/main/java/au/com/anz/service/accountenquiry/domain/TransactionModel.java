package au.com.anz.service.accountenquiry.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionModel {

    private long transactionId;

    private long accountNumber;

    private LocalDate valueDate;

    private TransactionType transactionType;

    private BigDecimal transactionAmount;

    private String transactionNarrative;

}
