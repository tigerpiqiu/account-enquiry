package au.com.anz.service.accountenquiry.controller;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/")
@Api(tags = {"Account-Enquiry"}, description = " ")
public class AccountEnquiryController {

    private AccountDAO accountDAO;

    @Autowired
    public AccountEnquiryController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping(
            value = "/accounts",
            produces = APPLICATION_JSON_VALUE
    )
    @Profiled
    @ApiOperation("Get all accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Accounts are found")
    })
    public List<AccountModel> getAccounts() {
        return this.accountDAO.getAccounts();
    }


    @GetMapping(
            value = "/{accountNumber}/transactions",
            produces = APPLICATION_JSON_VALUE
    )
    @Profiled
    @ApiOperation("Get all transactions made on the account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account transactions found")
    })
    public List<AccountModel> getAccountTransactions(@PathVariable("accountNumber") long accountNumber) {
        return new ArrayList<>();
    }

}
