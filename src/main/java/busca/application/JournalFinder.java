package busca.application;

import busca.infra.SearchFilter;
import org.json.JSONObject;

import java.io.IOException;

public interface JournalFinder {

    JSONObject find(SearchFilter filter, QueryOperatorBuilder operator, int from) throws IOException;
}
