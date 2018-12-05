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
}
