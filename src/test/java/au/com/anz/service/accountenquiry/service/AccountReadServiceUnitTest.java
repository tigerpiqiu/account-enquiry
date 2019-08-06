package au.com.anz.service.accountenquiry.service;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

public class AccountReadServiceUnitTest {

    private AccountReadService accountReadService;

    @Mock
    private AccountDAO accountDAO;

    @Before
    public void setupTest() {
        initMocks(this);
        accountReadService = new AccountReadService(accountDAO);
    }

    @Test
    public void allAccountsForUserShouldBeRetrievedSuccessfully() {
        long userId = 998877;
        List<AccountModel> accountModels = accountModels();
        given(accountDAO.getAccountsByUserId(userId)).willReturn(accountModels);

        List<AccountModel> retrievedAccounts = accountReadService.getAllAccountsForUser(userId);

        assertThat(retrievedAccounts, is(accountModels));
    }

    private List<AccountModel> accountModels() {
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountNumber(1001);
        accountModel.setUserId(998877);

        return Collections.singletonList(accountModel);
    }

}