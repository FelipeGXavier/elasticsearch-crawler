package busca.infra;

import busca.core.SearchOperator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class SearchFilter {

    @JsonProperty("search_phrase")
    private String searchPhrase;
    private List<String> affirmations;
    private List<String> denials;
    @JsonIgnore
    private LocalDate timestamp;
    private SearchOperator operator;

    private SearchFilter(String searchPhrase, List<String> affirmations, List<String> denials, SearchOperator operator) {
        this.searchPhrase = searchPhrase;
        this.affirmations = affirmations;
        this.denials = denials;
        this.operator = operator;
    }

    public SearchFilter() {
    }

    public static SearchFilter of(String searchPhrase, List<String> affirmations, List<String> denials, String operator) {
        return new SearchFilter(searchPhrase, affirmations, denials, SearchOperator.valueOf(operator));
    }

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

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

}
