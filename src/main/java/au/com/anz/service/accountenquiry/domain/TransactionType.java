package au.com.anz.service.accountenquiry.domain;


public enum TransactionType {

    DEBIT("D"),

    CREDIT("C");

    private final String code;

    TransactionType(final String code) {
        this.code = code;
    }

    public static TransactionType fromCode(String code) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.code.equalsIgnoreCase(code)) {
                return transactionType;
            }
        }
        throw new IllegalStateException("TransactionType not found for: " + code);
    }

    public String getCode() {
        return code;
    }
}
