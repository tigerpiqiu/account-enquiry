package au.com.anz.service.accountenquiry.persistence;

import au.com.anz.service.accountenquiry.domain.AccountModel;
import au.com.anz.service.accountenquiry.domain.AccountType;
import au.com.anz.service.accountenquiry.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AccountDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String SELECT_ACCOUNTS_BY_USER_ID = "SELECT account_number, account_name, user_id, account_type, balance_date, "
            + "currency, opening_available_balance FROM account WHERE user_id = :userId";

    private static final String SELECT_ACCOUNT_BY_NUMBER = "SELECT account_number, account_name, user_id, account_type, balance_date, "
            + "currency, opening_available_balance FROM account WHERE account_number = :accountNumber";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final AccountRowMapper accountRowMapper = new AccountRowMapper();

    @Autowired
    public AccountDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AccountModel> getAccountsByUserId(long userId) {
        return jdbcTemplate.query(SELECT_ACCOUNTS_BY_USER_ID, new MapSqlParameterSource("userId", userId), accountRowMapper);
    }

    public AccountModel getAccountByAccountNumber(long accountNumber) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ACCOUNT_BY_NUMBER, new MapSqlParameterSource("accountNumber", accountNumber),
                    accountRowMapper);
        } catch (EmptyResultDataAccessException ignored) {
            LOGGER.warn("Account number {} is not found.", accountNumber);
            throw new NotFoundException(String.format("Account number %s is not found.", accountNumber));
        }
    }

    static class AccountRowMapper implements RowMapper<AccountModel> {
        @Override
        public AccountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AccountModel accountReadModel = new AccountModel();
            accountReadModel.setAccountNumber(rs.getLong("account_number"));
            accountReadModel.setAccountName(rs.getString("account_name"));
            accountReadModel.setUserId(rs.getLong("user_id"));
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
