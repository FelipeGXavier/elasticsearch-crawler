package busca.infra;

import busca.application.JournalFinder;
import shared.FindOperator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/journal")
@Produces(MediaType.APPLICATION_JSON)
public class JournalResource {

    private JournalFinder finder;

    @Inject
    public JournalResource(JournalFinder finder) {
        this.finder = finder;
    }

    @POST
    @Path("/find")
    @Consumes(MediaType.APPLICATION_JSON)
    public String find(SearchFilter request, @DefaultValue("1") @QueryParam("page") int page) throws IOException {
        var hits = this.finder.find(request, FindOperator.getQueryBuilderFromOperator(request), page);
        return hits.toString();
    }

}
