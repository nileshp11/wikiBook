package com.microsoft.azure.featureflag.repository;


import java.util.List;

public class RuleEntity {
    private String variation;
    private List<List<PredicateEntity>> clauses;

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public List<List<PredicateEntity>> getClauses() {
        return clauses;
    }

    public void setClauses(List<List<PredicateEntity>> clauses) {
        this.clauses = clauses;
    }
}
