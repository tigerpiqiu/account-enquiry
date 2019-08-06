package au.com.anz.service.accountenquiry.utils;

import au.com.anz.service.accountenquiry.domain.TransactionType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class TestDataUtils {

    public static void deleteAllAccountRecords(JdbcTemplate jdbcTemplate) {
        String sqlDeleteAccounts = "DELETE FROM account";
        jdbcTemplate.update(sqlDeleteAccounts);
    }

    public static void deleteAllTransactionRecords(JdbcTemplate jdbcTemplate) {
        String sqlDeleteTransactions = "DELETE FROM account_transaction";
        jdbcTemplate.update(sqlDeleteTransactions);
    }

    public static void insertAccountRecord(JdbcTemplate jdbcTemplate, long accountNumber, String accountName, long userId, String accountType,
                                           String balanceDate, String currency, BigDecimal openingAvailableBalance) {
        String sqlAccount = getInsertAccountSql(accountNumber, accountName, userId, accountType, balanceDate, currency, openingAvailableBalance);
        jdbcTemplate.update(sqlAccount);
    }

    public static void insertTransactionRecord(JdbcTemplate jdbcTemplate, long accountNumber, String valueDate, TransactionType accountTransactionType,
                                               BigDecimal transactionAmount, String transactionNarrative) {
        String sqlTransaction = getInsertTransactionSql(accountNumber, valueDate, accountTransactionType, transactionAmount, transactionNarrative);
        jdbcTemplate.update(sqlTransaction);
    }

    private static String getInsertAccountSql(long accountNumber, String accountName, long userId, String accountType, String balanceDate, String currency, BigDecimal openingAvailableBalance) {
        return "INSERT INTO account (account_number, account_name, user_id, account_type, balance_date, currency, opening_available_balance) VALUES ("
                + accountNumber + ", '"
                + accountName + "', "
                + userId + ", '"
                + accountType + "', '"
                + balanceDate + "', '"
                + currency + "', "
                + openingAvailableBalance + ") ";
    }

    private static String getInsertTransactionSql(long accountNumber, String valueDate, TransactionType accountTransactionType,
                                                  BigDecimal transactionAmount, String transactionNarrative) {
        return "INSERT INTO account_transaction ( account_number, value_date, transaction_type, transaction_amount, transaction_narrative) VALUES ("
                + accountNumber + ", '"
                + valueDate + "', '"
                + accountTransactionType.getCode() + "',"
                + transactionAmount + ", '"
                + transactionNarrative + "') ";

    }
}
