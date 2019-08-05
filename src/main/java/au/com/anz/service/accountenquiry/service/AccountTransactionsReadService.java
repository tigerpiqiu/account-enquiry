package au.com.anz.service.accountenquiry.service;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.TransactionModel;
import au.com.anz.service.accountenquiry.dto.AccountTransactionsDTO;
import au.com.anz.service.accountenquiry.dto.TransactionDTO;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import au.com.anz.service.accountenquiry.persistence.AccountTransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountTransactionsReadService {

    private final AccountDAO accountDAO;

    private final AccountTransactionDAO accountTransactionDAO;

    @Autowired
    public AccountTransactionsReadService(AccountDAO accountDAO, AccountTransactionDAO accountTransactionDAO) {
        this.accountDAO = accountDAO;
        this.accountTransactionDAO = accountTransactionDAO;
    }

    public AccountTransactionsDTO getTransactionsForAccount(long accountNumber) {
        AccountModel account = this.accountDAO.getAccountByAccountNumber(accountNumber);
        List<TransactionModel> transactions = this.accountTransactionDAO.getTransactionsByAccountNumber(accountNumber);
        return this.convertToAccountTransactionsDAO(account, transactions);
    }

    private AccountTransactionsDTO convertToAccountTransactionsDAO(AccountModel accountModel, List<TransactionModel> transactionModels) {
        List<TransactionDTO> transactionDTOS = transactionModels.stream().map(this::convertToTransactionDTO).collect(Collectors.toList());
        return AccountTransactionsDTO.builder().accountNumber(accountModel.getAccountNumber())
                .accountName(accountModel.getAccountName())
                .transactions(transactionDTOS).build();
    }

    private TransactionDTO convertToTransactionDTO(TransactionModel model) {
        return TransactionDTO.builder().transactionId(model.getTransactionId())
                .accountNumber(model.getAccountNumber())
                .valueDate(model.getValueDate())
                .accountTransactionType(model.getAccountTransactionType())
                .transactionAmount(model.getTransactionAmount())
                .transactionNarrative(model.getTransactionNarrative()).build();

    }
}
