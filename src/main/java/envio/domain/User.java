package envio.domain;

import busca.application.QueryOperatorBuilder;
import busca.core.SearchOperator;
import busca.infra.SearchFilter;
import org.json.JSONObject;
import shared.FindOperator;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private int company;
    private String config;
    private String email;
    private JSONObject data;

    private User(int id, int company, String email, String config) {
        this.id = id;
        this.company = company;
        this.config = config;
        this.email = email;
        this.data = new JSONObject(config);
    }

    public static User of(int id, int company, String email, String config) {
        return new User(id, company, email, config);
    }

    public List<String> getAffirmationsConfig() {
        var json = this.data.getJSONArray("affirmations");
        List<String> affirmations = new ArrayList<>();
        json.forEach(affirmation -> {
            affirmations.add(affirmation.toString());
        });
        return affirmations;
    }

    public List<String> getDenialsConfig() {
        var json = this.data.getJSONArray("denials");
        List<String> affirmations = new ArrayList<>();
        json.forEach(affirmation -> {
            affirmations.add(affirmation.toString());
        });
        return affirmations;
    }

    public String getPhrase() {
        return this.data.getString("phrase");
    }

    public QueryOperatorBuilder getOperator() {
        var operator = this.data.getString("operator");
        var filter = SearchFilter.of(this.getPhrase(), this.getAffirmationsConfig(), this.getDenialsConfig(), operator);
        return FindOperator.getQueryBuilderFromOperator(filter);
    }

    public String getEmail() {
        return email;
    }

    public int getCompany() {
        return company;
    }

}
