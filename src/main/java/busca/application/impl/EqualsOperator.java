package busca.application.impl;

import busca.application.QueryOperatorBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

public class EqualsOperator implements QueryOperatorBuilder {

    private final List<String> affirmations;
    private final List<String> denials;
    private final String phrase;

    public EqualsOperator(List<String> affirmations, List<String> denials, String phrase) {
        this.affirmations = affirmations;
        this.phrase = phrase;
        this.denials = denials;
    }

    public BoolQueryBuilder getElasticQuery() {
        var builder = new BoolQueryBuilder();
        if (this.affirmations.size() > 0) {
            this.affirmations.forEach(affirmation -> {
                builder.should(QueryBuilders.matchPhraseQuery("content", this.phrase + " " + affirmation).slop(10)).minimumShouldMatch(1);
            });
        } else {
            builder.should(QueryBuilders.matchPhraseQuery("content", this.phrase));
        }
        this.denials.forEach(denial -> {
            builder.mustNot(QueryBuilders.matchPhraseQuery("content", this.phrase + " " + denial).slop(10)).minimumShouldMatch(1);
        });
        return builder;
    }
}
