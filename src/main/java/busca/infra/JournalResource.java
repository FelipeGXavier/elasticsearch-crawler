package busca.infra;

import busca.application.JournalFinder;
import busca.application.QueryOperatorBuilder;
import busca.application.impl.ContainsOperator;
import busca.application.impl.EqualsOperator;
import busca.core.SearchOperator;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public String find(SearchFilter request) throws IOException {
        var hits = this.finder.find(request, this.getQueryBuilderFromOperator(request.getOperator(), request));
        return hits.toString();
    }

    private QueryOperatorBuilder getQueryBuilderFromOperator(SearchOperator operator, SearchFilter filter) {
        QueryOperatorBuilder query = null;
        switch (operator) {
            case EQUALS:
                query = new EqualsOperator(filter.getAffirmations(), filter.getDenials(), filter.getSearchPhrase());
                break;
            case CONTAINS:
                query = new ContainsOperator(filter.getAffirmations(), filter.getDenials(), filter.getSearchPhrase());
                break;
        }
        return query;
    }


}
