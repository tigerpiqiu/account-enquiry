package au.com.anz.service.accountenquiry.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class TestDataUtils {

    public static void insertAccountRecord(JdbcTemplate jdbcTemplate, long accountNumber, String accountName, String accountType, String balanceDate, String currency, BigDecimal openingAvailableBalance) {
        String sqlAccount = getInsertAccountSql(accountNumber, accountName, accountType, balanceDate, currency, openingAvailableBalance);
        jdbcTemplate.update(sqlAccount);
    }

    private static String getInsertAccountSql(long accountNumber, String accountName, String accountType, String balanceDate, String currency, BigDecimal openingAvailableBalance) {
        return "INSERT INTO account (account_number, account_name, account_type, balance_date, currency, opening_available_balance) VALUES ("
                + accountNumber + ", '"
                + accountName + "', '"
                + accountType + "', '"
                + balanceDate + "', '"
                + currency + "', "
                + openingAvailableBalance + ") ";

    }

}
