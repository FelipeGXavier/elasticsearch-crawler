package busca.infra;

import busca.core.SearchOperator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class SearchFilter {


    private String searchPhrase;
    private List<String> affirmations;
    private List<String> denials;
    private LocalDate timestamp;
    private SearchOperator operator;

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    @JsonProperty("search_phrase")
    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    public List<String> getAffirmations() {
        return affirmations;
    }

    public void setAffirmations(List<String> affirmations) {
        this.affirmations = affirmations;
    }

    public List<String> getDenials() {
        return denials;
    }

    public void setDenials(List<String> denials) {
        this.denials = denials;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
