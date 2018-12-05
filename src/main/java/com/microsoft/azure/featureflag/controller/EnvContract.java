package com.microsoft.azure.featureflag.controller;

import java.util.List;

public class EnvContract {
  private Boolean enabled;
  private List<RuleContract> rules;

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public List<RuleContract> getRules() {
    return rules;
  }

  public void setRules(List<RuleContract> rules) {
    this.rules = rules;
  }
}
