package com.microsoft.azure.featureflag.service;

import com.microsoft.azure.featureflag.controller.FeatureFlagContract;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntity;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntityTranslator;
import com.microsoft.azure.featureflag.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureFlagAdminService {
    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    private FeatureFlagEntityTranslator FEATURE_FLAG_ENTITY_TRANSLATOR = new FeatureFlagEntityTranslator();

    public void saveFeatureFlag(FeatureFlagContract featureFlagContract){
        featureFlagRepository.save(FEATURE_FLAG_ENTITY_TRANSLATOR.translateTo(featureFlagContract));
    }

    public List<FeatureFlagContract> getAllFeatureFlags(){

        List<FeatureFlagContract> featureFlagContracts = new ArrayList<>();
        Iterable<FeatureFlagEntity> featureFlagEntities = featureFlagRepository.findAll();
        featureFlagEntities.forEach(featureFlagEntity -> {
            featureFlagContracts.add(FEATURE_FLAG_ENTITY_TRANSLATOR.translateFrom(featureFlagEntity));
        });
       return featureFlagContracts;
    }
}
