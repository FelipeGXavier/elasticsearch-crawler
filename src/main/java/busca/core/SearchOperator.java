package busca.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchOperator {

    @JsonProperty("0")
    EQUALS,
    @JsonProperty("1")
    CONTAINS;
}
