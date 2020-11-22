package busca.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchOperator {

    @JsonProperty("EQUALS")
    EQUALS,
    @JsonProperty("CONTAINS")
    CONTAINS;
}
