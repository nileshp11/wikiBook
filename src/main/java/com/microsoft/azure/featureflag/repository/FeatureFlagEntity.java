package com.microsoft.azure.featureflag.repository;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;

@Document
public class FeatureFlagEntity {
    private String name;
    @Id
    private String key;
    private List<String> variations;
    private Boolean enabled;
    private String defaultVariation;
    private Map<String, EnvEntity> envs;

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

    public Map<String, EnvEntity> getEnvs() {
        return envs;
    }

    public void setEnvs(Map<String, EnvEntity> envs) {
        this.envs = envs;
    }
}
