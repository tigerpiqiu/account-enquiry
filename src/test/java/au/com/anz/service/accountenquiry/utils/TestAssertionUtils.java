package au.com.anz.service.accountenquiry.utils;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class TestAssertionUtils {

    public static void assertThrows(Class expectedExceptionClass, Executable executable) {
        boolean exceptionWasThrown = false;
        try {
            executable.execute();
        } catch (Throwable actualException) {
            exceptionWasThrown = true;
            assertThat(actualException, CoreMatchers.is(instanceOf(expectedExceptionClass)));
        }
        if (!exceptionWasThrown) {
            throw new RuntimeException(String.format("Expected exception %s was not thrown", expectedExceptionClass));
        }
    }

    public interface Executable {
        void execute() throws Throwable;
    }
}
