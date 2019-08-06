package au.com.anz.service.accountenquiry.service;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.TransactionType;
import au.com.anz.service.accountenquiry.domain.TransactionModel;
import au.com.anz.service.accountenquiry.dto.AccountTransactionsDTO;
import au.com.anz.service.accountenquiry.dto.TransactionDTO;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import au.com.anz.service.accountenquiry.persistence.AccountTransactionDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

public class AccountTransactionsReadServiceUnitTest {

    private AccountTransactionsReadService accountTransactionsReadService;

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private AccountTransactionDAO accountTransactionDAO;

    @Before
    public void setupTest() {
        initMocks(this);
        accountTransactionsReadService = new AccountTransactionsReadService(accountDAO, accountTransactionDAO);
    }

    @Test
    public void accountTransactionsShouldBeRetrievedAndConvertedSuccessfully() {
        long accountNumber = 10011;
        String accountName = "GlenSavings";

        AccountModel accountModel = accountModel(accountNumber, accountName);
        given(accountDAO.getAccountByAccountNumber(accountNumber)).willReturn(accountModel);

        List<TransactionModel> transactionModels = transactionModels(accountNumber);
        given(accountTransactionDAO.getTransactionsByAccountNumber(accountNumber)).willReturn(transactionModels);

        AccountTransactionsDTO accountTransactionsDTO = accountTransactionsReadService.getTransactionsForAccount(accountNumber);

        assertThat(accountTransactionsDTO.getAccountNumber(), is(accountNumber));
        assertThat(accountTransactionsDTO.getAccountName(), is(accountName));

        assertThat(accountTransactionsDTO.getTransactions().size(), is(transactionModels.size()));

        verifyTransaction(transactionModels.get(0), accountTransactionsDTO.getTransactions().get(0));
    }

    private void verifyTransaction(TransactionModel model, TransactionDTO dto) {
        assertThat(model.getTransactionId(), is(dto.getTransactionId()));
        assertThat(model.getAccountNumber(), is(dto.getAccountNumber()));
        assertThat(model.getTransactionType(), is(dto.getTransactionType()));
        assertThat(model.getTransactionAmount(), is(dto.getTransactionAmount()));
        assertThat(model.getValueDate(), is(dto.getValueDate()));
        assertThat(model.getTransactionNarrative(), is(dto.getTransactionNarrative()));
    }

    private AccountModel accountModel(long accountNumber, String accountName) {
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountNumber(accountNumber);
        accountModel.setAccountName(accountName);
        return accountModel;
    }

    private List<TransactionModel> transactionModels(long accountNumber) {
        TransactionModel transaction = new TransactionModel();
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionId(100001);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionAmount(BigDecimal.valueOf(99.99));
        transaction.setValueDate(LocalDate.of(2019, 8, 8));
        transaction.setTransactionNarrative("Please confirm");
        return Collections.singletonList(transaction);
    }
}