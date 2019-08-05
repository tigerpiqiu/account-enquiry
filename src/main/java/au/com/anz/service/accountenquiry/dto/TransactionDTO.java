package au.com.anz.service.accountenquiry.dto;

import au.com.anz.service.accountenquiry.config.jackson.CustomDateSerializer;
import au.com.anz.service.accountenquiry.domain.AccountTransactionType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransactionDTO {

    private long transactionId;

    private long accountNumber;

    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate valueDate;

    private AccountTransactionType accountTransactionType;

    private BigDecimal transactionAmount;

    private String transactionNarrative;
}
