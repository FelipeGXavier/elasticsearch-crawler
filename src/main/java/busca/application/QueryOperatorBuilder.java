package busca.application;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface QueryOperatorBuilder {

    BoolQueryBuilder getElasticQuery();
}
