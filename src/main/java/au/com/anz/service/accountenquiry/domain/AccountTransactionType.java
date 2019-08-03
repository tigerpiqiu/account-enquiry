package au.com.anz.service.accountenquiry.domain;


public enum AccountTransactionType {

    DEBIT("D"),

    CREDIT("C");

    private final String code;

    AccountTransactionType(final String code) {
        this.code = code;
    }

    public static AccountTransactionType fromCode(String code) {
        for (AccountTransactionType transactionType : AccountTransactionType.values()) {
            if (transactionType.code.equalsIgnoreCase(code)) {
                return transactionType;
            }
        }
        throw new IllegalStateException("AccountTransactionType not found for: " + code);
    }

    public String getCode() {
        return code;
    }
}
