package com.microsoft.azure.featureflag.controller;

import java.util.List;
import java.util.Map;

public class FeatureFlagContract {
  private String name;
  private String key;
  private List<String> variations;
  private Boolean enabled;
  private String defaultVariation;
  private Map<String, EnvContract> envs;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public List<String> getVariations() {
    return variations;
  }

  public void setVariations(List<String> variations) {
    this.variations = variations;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getDefaultVariation() {
    return defaultVariation;
  }

  public void setDefaultVariation(String defaultVariation) {
    this.defaultVariation = defaultVariation;
  }

  public Map<String, EnvContract> getEnvs() {
    return envs;
  }

  public void setEnvs(Map<String, EnvContract> envs) {
    this.envs = envs;
  }
}
