package au.com.anz.service.accountenquiry.domain;


public enum AccountType {

    SAVINGS("SAV"),

    CURRENT("CUR");

    private final String code;

    AccountType(final String code) {
        this.code = code;
    }

    public static AccountType fromCode(String code) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.code.equalsIgnoreCase(code)) {
                return accountType;
            }
        }
        throw new IllegalStateException("AccountType not found for: " + code);
    }

    public String getCode() {
        return code;
    }
}
