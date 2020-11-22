package envio.infra;

import busca.application.JournalFinder;
import envio.application.MailSender;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/sender")
@Produces(MediaType.APPLICATION_JSON)
public class MailSenderResource {

    private MailSender mailService;
    private JournalFinder finder;

    @Inject
    public MailSenderResource(MailSender mailService, JournalFinder finder) {
        this.mailService = mailService;
        this.finder = finder;
    }

    @GET
    @Path("/mail")
    public Response sendEmailText() throws IOException {
        this.mailService.send();
        return Response.ok().build();
    }

    @GET
    @Path("/mail/{hash}")
    public Response findSearchFromUser(@PathParam("hash") String hash) throws IOException {
        hash = hash.replace("target=\"_blank\"", "");
        var content = this.finder.findEmailSubmissionsFromUser(hash);
        return Response.ok().entity(content.toString()).build();
    }

}
