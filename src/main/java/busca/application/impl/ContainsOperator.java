package busca.application.impl;

import busca.application.QueryOperatorBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.util.List;

public class ContainsOperator implements QueryOperatorBuilder {

    private final List<String> affirmations;
    private final List<String> denials;
    private final String phrase;

    public ContainsOperator(List<String> affirmations, List<String> denials, String phrase) {
        this.affirmations = affirmations;
        this.phrase = phrase;
        this.denials = denials;
    }

    @Override
    public BoolQueryBuilder getElasticQuery() {
        return null;
    }

}
