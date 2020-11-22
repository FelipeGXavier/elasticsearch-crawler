package busca.application;

import busca.application.impl.JournalTextMatcher;
import busca.infra.SearchFilter;
import envio.domain.User;
import org.json.JSONObject;

import java.io.IOException;

public interface JournalFinder {

    JSONObject find(SearchFilter filter, QueryOperatorBuilder operator, int from) throws IOException;
    JSONObject findEmailSubmissionsFromUser(String hash) throws IOException;
    JournalTextMatcher.Payload find(String index, User entity) throws IOException;

}
