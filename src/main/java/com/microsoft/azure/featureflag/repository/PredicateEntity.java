package com.microsoft.azure.featureflag.repository;

public class PredicateEntity {
    private String keyToMatch;
    private String valueToMatch;

    public String getKeyToMatch() {
        return keyToMatch;
    }

    public void setKeyToMatch(String keyToMatch) {
        this.keyToMatch = keyToMatch;
    }

    public String getValueToMatch() {
        return valueToMatch;
    }

    public void setValueToMatch(String valueToMatch) {
        this.valueToMatch = valueToMatch;
    }
}
