package com.microsoft.azure.featureflag.controller;

import java.util.List;

public class RuleContract {
  private String variation;
  private List<List<PredicateContract>> clauses;
}
