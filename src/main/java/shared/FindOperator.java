package shared;

import busca.application.QueryOperatorBuilder;
import busca.application.impl.ContainsOperator;
import busca.application.impl.EqualsOperator;
import busca.infra.SearchFilter;

public class FindOperator {

    public static QueryOperatorBuilder getQueryBuilderFromOperator(SearchFilter filter) {
        QueryOperatorBuilder query = null;
        switch (filter.getOperator()) {
            case EQUALS:
                query = new EqualsOperator(filter.getAffirmations(), filter.getDenials(), filter.getSearchPhrase());
                break;
            case CONTAINS:
                query = new ContainsOperator(filter.getAffirmations(), filter.getDenials(), filter.getSearchPhrase());
                break;
        }
        return query;
    }
}
