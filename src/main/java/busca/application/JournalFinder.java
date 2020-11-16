package busca.application;

import busca.infra.SearchFilter;
import org.json.JSONArray;

import java.io.IOException;

public interface JournalFinder {

    JSONArray find(SearchFilter filter, QueryOperatorBuilder operator) throws IOException;
}
