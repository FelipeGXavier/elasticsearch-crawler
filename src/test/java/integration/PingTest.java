package integration;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import resources.PingResource;

import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PingTest {

    private static final ResourceExtension resource =
            ResourceExtension.builder().addResource(new PingResource()).build();

    @Test
    public void getDummy() {
        final Response response = resource.target("/ping").request().get();
        Assertions.assertEquals(response.getStatusInfo().getStatusCode(), 200);
    }
}
