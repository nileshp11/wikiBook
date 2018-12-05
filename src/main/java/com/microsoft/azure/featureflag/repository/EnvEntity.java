package com.microsoft.azure.featureflag.repository;


import java.util.List;

public class EnvEntity {
    private Boolean enabled;
    private List<RuleEntity> rules;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<RuleEntity> getRules() {
        return rules;
    }

    public void setRules(List<RuleEntity> rules) {
        this.rules = rules;
    }
}
