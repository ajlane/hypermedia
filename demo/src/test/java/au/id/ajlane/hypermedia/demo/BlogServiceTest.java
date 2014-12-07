package au.id.ajlane.hypermedia.demo;

import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import org.junit.Test;

import java.io.IOException;

public class BlogServiceTest extends JerseyTest {

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder()
            .clientConfig(new DefaultClientConfig(BlogService.class))
                .build();
    }

    @Test
    public void test() throws IOException {

    }
}
