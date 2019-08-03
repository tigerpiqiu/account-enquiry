package au.com.anz.service.accountenquiry;

import au.com.anz.service.accountenquiry.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class AccountEnquiryServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AccountEnquiryServiceApplication.class);
        springApplication.run(args);
    }
}
