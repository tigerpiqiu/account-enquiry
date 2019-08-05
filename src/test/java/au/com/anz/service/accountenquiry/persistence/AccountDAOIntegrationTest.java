package au.com.anz.service.accountenquiry.persistence;

import au.com.anz.service.accountenquiry.domain.AccountModel;
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

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountDAOIntegrationTest {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        TestDataUtils.deleteAllAccountRecords(jdbcTemplate);
        TestDataUtils.insertAccountRecord(jdbcTemplate, 585309209, "SGSavings726", "SAV", "2019-02-09", "AUD", BigDecimal.valueOf(9898773.89));
        TestDataUtils.insertAccountRecord(jdbcTemplate, 456780921, "MyCurrent001", "CUR", "2017-09-03", "RMB", BigDecimal.valueOf(81556773.89));
    }

    @Test
    public void allAccountShouldBeRetrievedSuccessfully() {
        List<AccountModel> accounts = accountDAO.getAccounts();

        assertThat(accounts.size(), is(2));
        verifyAccount(accounts.get(0), 585309209, "SGSavings726", "SAV", LocalDate.of(2019, 2, 9), "AUD", BigDecimal.valueOf(9898773.89));
        verifyAccount(accounts.get(1), 456780921, "MyCurrent001", "CUR", LocalDate.of(2017, 9, 3), "RMB", BigDecimal.valueOf(81556773.89));
    }


    @Test
    public void accountShouldBeRetrievedSuccessfullyByAccountNumber() {
        AccountModel account = accountDAO.getAccountByAccountNumber(585309209);

        verifyAccount(account, 585309209, "SGSavings726", "SAV", LocalDate.of(2019, 2, 9), "AUD", BigDecimal.valueOf(9898773.89));
    }

    private void verifyAccount(AccountModel account, long accountNumber, String accountName, String accountType,
                               LocalDate balanceDate, String currency, BigDecimal openingAvailableBalance) {
        assertThat(account.getAccountNumber(), is(accountNumber));
        assertThat(account.getAccountName(), is(accountName));
        assertThat(account.getAccountType().getCode(), is(accountType));
        assertThat(account.getBalanceDate(), is(balanceDate));
        assertThat(account.getCurrency(), is(currency));
        assertThat(account.getOpeningAvailableBalance(), is(openingAvailableBalance));
    }
}