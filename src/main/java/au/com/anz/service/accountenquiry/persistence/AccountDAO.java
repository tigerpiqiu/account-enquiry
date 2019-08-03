package au.com.anz.service.accountenquiry.persistence;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.AccountType;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AccountDAO {

    private static final String SELECT_ALL_ACCOUNTS = "SELECT account_number, account_name, account_type, balance_date, currency, "
            + " opening_available_balance FROM account;";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final AccountRowMapper accountRowMapper = new AccountRowMapper();

    @Autowired
    public AccountDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Profiled
    public List<AccountModel> getAccounts() {
        return jdbcTemplate.query(SELECT_ALL_ACCOUNTS, new MapSqlParameterSource(), accountRowMapper);
    }

    static class AccountRowMapper implements RowMapper<AccountModel> {
        @Override
        public AccountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AccountModel accountReadModel = new AccountModel();
            accountReadModel.setAccountNumber(rs.getLong("account_number"));
            accountReadModel.setAccountName(rs.getString("account_name"));
            accountReadModel.setAccountType(AccountType.fromCode(rs.getString("account_type")));
            accountReadModel.setBalanceDate(convertAsLocalDate(rs.getDate("balance_date")));
            accountReadModel.setCurrency(rs.getString("currency"));
            accountReadModel.setOpeningAvailableBalance(rs.getBigDecimal("opening_available_balance"));
            return accountReadModel;
        }

        private LocalDate convertAsLocalDate(Date date) {
            return date == null ? null : date.toLocalDate();
        }
    }
}
