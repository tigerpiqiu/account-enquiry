package au.com.anz.service.accountenquiry.persistence;

import au.com.anz.service.accountenquiry.domain.TransactionModel;
import au.com.anz.service.accountenquiry.domain.TransactionType;
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountTransactionDAOIntegrationTest {

    @Autowired
    private AccountTransactionDAO accountTransactionDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        TestDataUtils.deleteAllTransactionRecords(jdbcTemplate);
        TestDataUtils.insertTransactionRecord(jdbcTemplate, 585309209, "2019-02-09", TransactionType.CREDIT, BigDecimal.valueOf(13.89), "This is a test 01.");
        TestDataUtils.insertTransactionRecord(jdbcTemplate, 585309209, "2019-03-09", TransactionType.DEBIT, BigDecimal.valueOf(1993.89), "This is a test 02.");

        TestDataUtils.insertTransactionRecord(jdbcTemplate, 456780921, "2019-04-05", TransactionType.DEBIT, BigDecimal.valueOf(3399.09), "This is a test 02.");
        TestDataUtils.insertTransactionRecord(jdbcTemplate, 456780921, "2019-06-07", TransactionType.CREDIT, BigDecimal.valueOf(2345.66), "This is a test 02.");
    }

    @Test
    public void allAccountShouldBeRetrievedSuccessfully() {
        long accountNumber = 585309209;

        List<TransactionModel> transactions = accountTransactionDAO.getTransactionsByAccountNumber(accountNumber);

        assertThat(transactions.size(), is(2));
        verifyTransaction(transactions.get(0), 585309209, LocalDate.of(2019, 2, 9), TransactionType.CREDIT, BigDecimal.valueOf(13.89), "This is a test 01.");
        verifyTransaction(transactions.get(1), 585309209, LocalDate.of(2019, 3, 9), TransactionType.DEBIT, BigDecimal.valueOf(1993.89), "This is a test 02.");
    }


    private void verifyTransaction(TransactionModel accountTransaction, long accountNumber, LocalDate valueDate, TransactionType accountTransactionType,
                                   BigDecimal transactionAmount, String transactionNarrative) {
        assertThat(accountTransaction.getAccountNumber(), is(accountNumber));
        assertThat(accountTransaction.getValueDate(), is(valueDate));
        assertThat(accountTransaction.getTransactionType(), is(accountTransactionType));
        assertThat(accountTransaction.getTransactionAmount(), is(transactionAmount));
        assertThat(accountTransaction.getTransactionNarrative(), is(transactionNarrative));

    }
}