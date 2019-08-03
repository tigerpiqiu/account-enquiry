package au.com.anz.service.accountenquiry.domain;

import au.com.anz.service.accountenquiry.config.jackson.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountModel {

    private long accountNumber;

    private String accountName;

    private AccountType accountType;

    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate balanceDate;

    private String currency;

    private BigDecimal openingAvailableBalance;

}
