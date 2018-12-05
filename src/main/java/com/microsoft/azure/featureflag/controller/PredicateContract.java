package com.microsoft.azure.featureflag.controller;

public class PredicateContract {
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
