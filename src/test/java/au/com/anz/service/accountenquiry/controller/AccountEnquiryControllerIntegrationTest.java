package au.com.anz.service.accountenquiry.controller;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.TransactionType;
import au.com.anz.service.accountenquiry.dto.AccountTransactionsDTO;
import au.com.anz.service.accountenquiry.dto.TransactionDTO;
import au.com.anz.service.accountenquiry.utils.TestDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountEnquiryControllerIntegrationTest {

    @Autowired
    private AccountEnquiryController accountEnquiryController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        TestDataUtils.deleteAllAccountRecords(jdbcTemplate);
        TestDataUtils.deleteAllTransactionRecords(jdbcTemplate);

        TestDataUtils.insertAccountRecord(jdbcTemplate, 585309209, "SGSavings726", 1000001, "SAV", "2019-02-09", "AUD", BigDecimal.valueOf(9898773.89));
        TestDataUtils.insertAccountRecord(jdbcTemplate, 456780921, "MyCurrent001", 1000001, "CUR", "2017-09-03", "RMB", BigDecimal.valueOf(81556773.89));

        TestDataUtils.insertTransactionRecord(jdbcTemplate, 585309209, "2019-02-09", TransactionType.CREDIT, BigDecimal.valueOf(13.89), "This is a test 01.");
        TestDataUtils.insertTransactionRecord(jdbcTemplate, 585309209, "2019-03-09", TransactionType.DEBIT, BigDecimal.valueOf(1993.89), "This is a test 02.");

        TestDataUtils.insertTransactionRecord(jdbcTemplate, 456780921, "2019-04-05", TransactionType.DEBIT, BigDecimal.valueOf(3399.09), "This is a test 02.");
        TestDataUtils.insertTransactionRecord(jdbcTemplate, 456780921, "2019-06-07", TransactionType.CREDIT, BigDecimal.valueOf(2345.66), "This is a test 02.");
    }

    @Test
    public void allAccountShouldBeRetrievedSuccessfully() {
        List<AccountModel> accounts = accountEnquiryController.getAccountsForUser(1000001);

        assertThat(accounts.size(), is(2));

        verifyAccountModel(accounts.get(0), 585309209, "SGSavings726", 1000001, "SAV", LocalDate.of(2019, 2, 9), "AUD", BigDecimal.valueOf(9898773.89));
        verifyAccountModel(accounts.get(1), 456780921, "MyCurrent001", 1000001, "CUR", LocalDate.of(2017, 9, 3), "RMB", BigDecimal.valueOf(81556773.89));
    }

    @Test
    public void accountTransactionsShouldBeRetrievedSuccessfully() {
        long accountNumber = 585309209;

        AccountTransactionsDTO accountTransactions = accountEnquiryController.getAccountTransactions(accountNumber);

        assertThat(accountTransactions.getAccountNumber(), is(accountNumber));
        assertThat(accountTransactions.getAccountName(), is("SGSavings726"));

        verifyTransactionDTO(accountTransactions.getTransactions().get(0), 585309209, LocalDate.of(2019, 2, 9), TransactionType.CREDIT, BigDecimal.valueOf(13.89), "This is a test 01.");
        verifyTransactionDTO(accountTransactions.getTransactions().get(1), 585309209, LocalDate.of(2019, 3, 9), TransactionType.DEBIT, BigDecimal.valueOf(1993.89), "This is a test 02.");
    }

    private void verifyAccountModel(AccountModel account, long accountNumber, String accountName, long userId, String accountType,
                                    LocalDate balanceDate, String currency, BigDecimal openingAvailableBalance) {
        assertThat(account.getAccountNumber(), is(accountNumber));
        assertThat(account.getAccountName(), is(accountName));
        assertThat(account.getUserId(), is(userId));
        assertThat(account.getAccountType().getCode(), is(accountType));
        assertThat(account.getBalanceDate(), is(balanceDate));
        assertThat(account.getCurrency(), is(currency));
        assertThat(account.getOpeningAvailableBalance(), is(openingAvailableBalance));
    }

    private void verifyTransactionDTO(TransactionDTO transactionDTO, long accountNumber,
                                      LocalDate valueDate, TransactionType accountTransactionType,
                                      BigDecimal transactionAmount, String transactionNarrative) {
        assertThat(transactionDTO.getAccountNumber(), is(accountNumber));
        assertThat(transactionDTO.getTransactionType(), is(accountTransactionType));
        assertThat(transactionDTO.getValueDate(), is(valueDate));
        assertThat(transactionDTO.getTransactionAmount(), is(transactionAmount));
        assertThat(transactionDTO.getTransactionNarrative(), is(transactionNarrative));
    }
}