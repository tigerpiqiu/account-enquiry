package au.com.anz.service.accountenquiry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AccountTransactionsDTO {

    private long accountNumber;

    private String accountName;

    private List<TransactionDTO> transactions;
}
