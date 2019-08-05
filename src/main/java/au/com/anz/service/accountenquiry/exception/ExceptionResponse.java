package au.com.anz.service.accountenquiry.exception;

public class ExceptionResponse {

    public String url;
    public String exception;
    public String cause;

    public ExceptionResponse(String url, Exception ex) {
        this.url = url;
        this.exception = ex.getLocalizedMessage();
        this.cause = ex.getCause() != null ? ex.getCause().getLocalizedMessage() : "";
    }
}
