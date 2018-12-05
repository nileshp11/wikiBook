package com.microsoft.azure.featureflag.repository;


import java.util.List;

public class EnvEntity {
    private String enabled;
    private List<RuleEntity> rules;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public List<RuleEntity> getRules() {
        return rules;
    }

    public void setRules(List<RuleEntity> rules) {
        this.rules = rules;
    }
}
