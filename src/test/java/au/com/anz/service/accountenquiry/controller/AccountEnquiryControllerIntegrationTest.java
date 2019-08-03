package au.com.anz.service.accountenquiry.controller;

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
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountEnquiryControllerIntegrationTest {

    @Autowired
    private AccountEnquiryController accountEnquiryController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        TestDataUtils.insertAccountRecord(jdbcTemplate, 585309209, "SGSavings726", "SAV", "2019-02-09", "AUD", BigDecimal.valueOf(9898773.89));
        TestDataUtils.insertAccountRecord(jdbcTemplate, 456780921, "MyCurrent001", "CUR", "2017-09-03", "RMB", BigDecimal.valueOf(81556773.89));
    }

    @Test
    public void allAccountShouldBeRetrievedSuccessfully() {
        List<AccountModel> accounts = accountEnquiryController.getAccounts();
        assertThat(accounts.size(), is(2));
    }
}