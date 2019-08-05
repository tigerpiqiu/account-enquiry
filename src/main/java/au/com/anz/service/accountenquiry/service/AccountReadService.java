package au.com.anz.service.accountenquiry.service;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.persistence.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountReadService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountReadService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<AccountModel> getAllAccounts() {
        return this.accountDAO.getAccounts();
    }
}
