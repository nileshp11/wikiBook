package com.microsoft.azure.featureflag.controller;

import java.util.List;

public class EnvContract {
  private String enabled;
  private List<RuleContract> rules;

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public List<RuleContract> getRules() {
    return rules;
  }

  public void setRules(List<RuleContract> rules) {
    this.rules = rules;
  }
}
