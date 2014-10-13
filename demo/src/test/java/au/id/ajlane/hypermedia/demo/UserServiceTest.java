package au.id.ajlane.hypermedia.demo;

import au.id.ajlane.hypermedia.UserService;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;

import java.io.IOException;

public class UserServiceTest extends JerseyTest {

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder()
                .clientConfig(new DefaultClientConfig(UserService.class))
                .build();
    }

    @Test
    public void test() throws IOException {

    }
}
