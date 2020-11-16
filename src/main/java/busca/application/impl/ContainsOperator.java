package busca.application.impl;

import busca.application.QueryOperatorBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

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
        var boolQueryBuilder = new BoolQueryBuilder();
        var stringBuilder = new StringBuilder();
        stringBuilder.append("(*").append(this.phrase).append("*");
        if (this.affirmations.size() > 0) {
            stringBuilder.append(" AND ");
            stringBuilder.append("(");
            this.parseWordListToQueryString(stringBuilder, this.affirmations);
            stringBuilder.append(")");
        }
        if (this.denials.size() > 0) {
            stringBuilder.append(" AND NOT ");
            stringBuilder.append("(");
            this.parseWordListToQueryString(stringBuilder, this.denials);
            stringBuilder.append(")");
        }
        stringBuilder.append(")");
        boolQueryBuilder.must(QueryBuilders.queryStringQuery(stringBuilder.toString()).field("content"));
        return boolQueryBuilder;
    }

    private StringBuilder parseWordListToQueryString(StringBuilder stringBuilder, List<String> words) {
        final var size = words.size();
        for (int i = 0; i < size; i++) {
            if ((i + 1) >= size) {
                stringBuilder.append("*").append(words.get(i)).append("*");
            } else {
                stringBuilder.append("*").append(words.get(i)).append("* OR ");
            }
        }
        return stringBuilder;
    }

}
