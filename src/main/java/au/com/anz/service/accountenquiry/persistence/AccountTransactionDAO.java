package au.com.anz.service.accountenquiry.persistence;

import au.com.anz.service.accountenquiry.domain.TransactionModel;
import au.com.anz.service.accountenquiry.domain.TransactionType;
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
public class AccountTransactionDAO {

    private static final String SELECT_TRANSACTIONS_BY_ACCOUNT_NUMBER =
            "SELECT transaction_id, account_number, value_date, transaction_type, transaction_amount, transaction_narrative  "
                    + "  FROM account_transaction WHERE account_number = :accountNumber";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final AccountTransactionRowMapper transactionRowMapper = new AccountTransactionRowMapper();

    @Autowired
    public AccountTransactionDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TransactionModel> getTransactionsByAccountNumber(long accountNumber) {
        return jdbcTemplate.query(SELECT_TRANSACTIONS_BY_ACCOUNT_NUMBER,
                new MapSqlParameterSource("accountNumber", accountNumber), transactionRowMapper);
    }

    static class AccountTransactionRowMapper implements RowMapper<TransactionModel> {
        @Override
        public TransactionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TransactionModel transaction = new TransactionModel();
            transaction.setTransactionId(rs.getLong("transaction_id"));
            transaction.setAccountNumber(rs.getLong("account_number"));
            transaction.setValueDate(convertAsLocalDate(rs.getDate("value_date")));
            transaction.setTransactionType(TransactionType.fromCode(rs.getString("transaction_type")));
            transaction.setTransactionAmount(rs.getBigDecimal("transaction_amount"));
            transaction.setTransactionNarrative(rs.getString("transaction_narrative"));
            return transaction;
        }

        private LocalDate convertAsLocalDate(Date date) {
            return date == null ? null : date.toLocalDate();
        }
    }
}
