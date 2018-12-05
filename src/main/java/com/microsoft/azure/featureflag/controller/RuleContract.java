package com.microsoft.azure.featureflag.controller;

import java.util.List;

public class RuleContract {
  private String variation;
  private List<List<PredicateContract>> clauses;

  public String getVariation() {
    return variation;
  }

  public void setVariation(String variation) {
    this.variation = variation;
  }

  public List<List<PredicateContract>> getClauses() {
    return clauses;
  }

  public void setClauses(List<List<PredicateContract>> clauses) {
    this.clauses = clauses;
  }
}
