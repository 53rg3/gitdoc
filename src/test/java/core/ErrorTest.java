package core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ErrorTest {

    @Test
    public void throwIt() {
        String msg = "user fucked up";
        Error error = new Error(msg);
        assertThat(error.getMessage(), is(msg));
    }

}
