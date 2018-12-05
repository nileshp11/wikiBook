package com.microsoft.azure.featureflag.service;

import com.microsoft.azure.featureflag.controller.FeatureFlagContract;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntity;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntityTranslator;
import com.microsoft.azure.featureflag.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagAdminService {
    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    private FeatureFlagEntityTranslator FEATURE_FLAG_ENTITY_TRANSLATOR = new FeatureFlagEntityTranslator();

    public void SaveFetureFlag(FeatureFlagContract featureFlagContract){
        featureFlagRepository.save(FEATURE_FLAG_ENTITY_TRANSLATOR.translateTo(featureFlagContract));
    }
}
