package au.com.anz.service.accountenquiry.controller;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.AccountType;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;

public class AccountEnquiryControllerUnitTest extends ControllerUnitTestBase {

    @Mock
    private AccountDAO accountDAO;


    @Before
    public void setUp() {
        initMocks(this);
        AccountEnquiryController accountEnquiryController = new AccountEnquiryController(accountDAO);
        initializeController(accountEnquiryController);
    }

    @Test
    public void accountsShouldBeReturnedToClientSuccessfully() throws Exception {
        given(accountDAO.getAccounts()).willReturn(accountReadModelList());

        String expectedResponsePayload = "[{\"accountNumber\":9911,\"accountName\":\"DAVSavings001\",\"accountType\":\"SAVINGS\",\"balanceDate\":\"08-01-2019\",\"currency\":\"AUD\",\"openingAvailableBalance\":99.88}]";

        assertSuccessfulRequestWithExpectedResponse(onGet("/accounts"), 200, expectedResponsePayload);
    }

    private List<AccountModel> accountReadModelList() {
        AccountModel accountReadModel1 = new AccountModel();
        accountReadModel1.setAccountNumber(9911);
        accountReadModel1.setAccountName("DAVSavings001");
        accountReadModel1.setAccountType(AccountType.SAVINGS);
        accountReadModel1.setBalanceDate(LocalDate.of(2019, 1, 8));
        accountReadModel1.setCurrency("AUD");
        accountReadModel1.setOpeningAvailableBalance(BigDecimal.valueOf(99.88));

        return Arrays.asList(accountReadModel1);
    }

}