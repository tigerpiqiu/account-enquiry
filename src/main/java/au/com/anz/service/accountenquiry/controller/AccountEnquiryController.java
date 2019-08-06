package au.com.anz.service.accountenquiry.controller;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.dto.AccountTransactionsDTO;
import au.com.anz.service.accountenquiry.service.AccountReadService;
import au.com.anz.service.accountenquiry.service.AccountTransactionsReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/")
@Api(tags = {"Account-Enquiry"}, description = " ")
public class AccountEnquiryController {

    private final AccountReadService accountReadService;

    private final AccountTransactionsReadService accountTransactionsReadService;

    @Autowired
    public AccountEnquiryController(AccountReadService accountReadService, AccountTransactionsReadService accountTransactionsReadService) {
        this.accountReadService = accountReadService;
        this.accountTransactionsReadService = accountTransactionsReadService;
    }

    @GetMapping(
            value = "/{userId}/accounts",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation("Get all accounts for the user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Accounts are found for the user")
    })
    public List<AccountModel> getAccountsForUser(@PathVariable("userId") long userId) {
        return this.accountReadService.getAllAccountsForUser(userId);
    }


    @GetMapping(
            value = "/{accountNumber}/transactions",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation("Get all transactions made on the account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account transactions found")
    })
    public AccountTransactionsDTO getAccountTransactions(@PathVariable("accountNumber") long accountNumber) {
        return this.accountTransactionsReadService.getTransactionsForAccount(accountNumber);
    }

}
