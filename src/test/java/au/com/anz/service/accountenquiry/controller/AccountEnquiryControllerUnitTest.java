package au.com.anz.service.accountenquiry.controller;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.TransactionType;
import au.com.anz.service.accountenquiry.domain.AccountType;
import au.com.anz.service.accountenquiry.dto.AccountTransactionsDTO;
import au.com.anz.service.accountenquiry.dto.TransactionDTO;
import au.com.anz.service.accountenquiry.service.AccountReadService;
import au.com.anz.service.accountenquiry.service.AccountTransactionsReadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;

public class AccountEnquiryControllerUnitTest extends ControllerUnitTestBase {

    @Mock
    private AccountReadService accountReadService;

    @Mock
    private AccountTransactionsReadService accountTransactionsReadService;


    @Before
    public void setUp() {
        initMocks(this);
        AccountEnquiryController accountEnquiryController = new AccountEnquiryController(accountReadService, accountTransactionsReadService);
        initializeController(accountEnquiryController);
    }

    @Test
    public void accountsShouldBeReturnedToClientSuccessfully() throws Exception {
        long userId = 1000001;
        given(accountReadService.getAllAccountsForUser(userId)).willReturn(accountReadModelList());

        String expectedResponsePayload = "[{\"accountNumber\":9911,\"accountName\":\"DAVSavings001\",\"userId\":1000001,\"accountType\":\"SAVINGS\",\"balanceDate\":\"08/01/2019\",\"currency\":\"AUD\",\"openingAvailableBalance\":99.88}]";

        assertSuccessfulRequestWithExpectedResponse(onGet("/1000001/accounts"), 200, expectedResponsePayload);
    }

    @Test
    public void accountTransactionsShouldBeReturnedToClientSuccessfully() throws Exception {
        long accountNumber = 1009;
        AccountTransactionsDTO accountTransactionsDTO = accountTransactionsDAO();
        given(accountTransactionsReadService.getTransactionsForAccount(accountNumber)).willReturn(accountTransactionsDTO);

        String expectedResponsePayload = "{\"accountNumber\":1009,\"accountName\":\"DAVSavings001\",\"transactions\":[{\"transactionId\":1001,\"accountNumber\":1009,\"valueDate\":\"12/11/2019\",\"transactionType\":\"CREDIT\",\"transactionAmount\":99.99,\"transactionNarrative\":\"pleas confirm\"}]}";

        assertSuccessfulRequestWithExpectedResponse(onGet("/1009/transactions"), 200, expectedResponsePayload);
    }

    private List<AccountModel> accountReadModelList() {
        AccountModel accountReadModel1 = new AccountModel();
        accountReadModel1.setAccountNumber(9911);
        accountReadModel1.setAccountName("DAVSavings001");
        accountReadModel1.setUserId(1000001);
        accountReadModel1.setAccountType(AccountType.SAVINGS);
        accountReadModel1.setBalanceDate(LocalDate.of(2019, 1, 8));
        accountReadModel1.setCurrency("AUD");
        accountReadModel1.setOpeningAvailableBalance(BigDecimal.valueOf(99.88));

        return Arrays.asList(accountReadModel1);
    }

    private AccountTransactionsDTO accountTransactionsDAO() {
        return AccountTransactionsDTO.builder()
                .accountNumber(1009)
                .accountName("DAVSavings001")
                .transactions(transactionDTOs()).build();
    }

    private List<TransactionDTO> transactionDTOs() {
        TransactionDTO transaction = TransactionDTO.builder()
                .transactionId(1001)
                .accountNumber(1009)
                .valueDate(LocalDate.of(2019, 11, 12))
                .transactionType(TransactionType.CREDIT)
                .transactionAmount(BigDecimal.valueOf(99.99))
                .transactionNarrative("pleas confirm").build();
        return Collections.singletonList(transaction);

    }
}