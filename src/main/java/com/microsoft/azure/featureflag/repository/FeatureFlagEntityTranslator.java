package com.microsoft.azure.featureflag.repository;

import com.microsoft.azure.featureflag.common.BaseTranslator;
import com.microsoft.azure.featureflag.controller.EnvContract;
import com.microsoft.azure.featureflag.controller.FeatureFlagContract;
import com.microsoft.azure.featureflag.controller.PredicateContract;
import com.microsoft.azure.featureflag.controller.RuleContract;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureFlagEntityTranslator extends BaseTranslator<FeatureFlagContract, FeatureFlagEntity> {

    private PredicateEntityTranslator PREDICATE_ENTITY_TRANSLATOR = new PredicateEntityTranslator();
    private RulesEntityTranslator RULES_ENTITY_TRANSLATOR = new RulesEntityTranslator();
    private EnvEntityTranslator ENV_ENTITY_TRANSLATOR = new EnvEntityTranslator();
    @Override
    public FeatureFlagEntity translateTo(FeatureFlagContract featureFlagContract) {
        FeatureFlagEntity featureFlagEntity = new FeatureFlagEntity();
        featureFlagEntity.setName(featureFlagContract.getName());
        featureFlagEntity.setVariations(featureFlagContract.getVariations());
        featureFlagEntity.setEnabled(featureFlagContract.getEnabled());
        Map<String, EnvEntity> environmentMap = new HashMap<>();
        Map<String, EnvContract> envrionmentContractMap = featureFlagContract.getEnvs();
        envrionmentContractMap.keySet().forEach(envrionmentContractKey ->{
            environmentMap.put(envrionmentContractKey,ENV_ENTITY_TRANSLATOR.translateTo(envrionmentContractMap.get(envrionmentContractKey)));
        });
        featureFlagEntity.setDefaultVariation(featureFlagContract.getDefaultVariation());
        featureFlagEntity.setEnvs(environmentMap);
        featureFlagEntity.setKey(featureFlagContract.getKey());
        return featureFlagEntity;
    }


    private class EnvEntityTranslator extends BaseTranslator<EnvContract, EnvEntity> {

        @Override
        public EnvEntity translateTo(EnvContract envContract) {
            EnvEntity envEntity = new EnvEntity();
            envEntity.setEnabled(envContract.getEnabled());
            envEntity.setRules(RULES_ENTITY_TRANSLATOR.translateTo(envContract.getRules()));
            return envEntity;
        }
    }


    private class RulesEntityTranslator extends BaseTranslator<RuleContract, RuleEntity> {

        @Override
        public RuleEntity translateTo(RuleContract ruleContract) {
            RuleEntity ruleEntity = new RuleEntity();
            List<List<PredicateContract>> listOfListPredicateContract = ruleContract.getClauses();
            List<List<PredicateEntity>> listOfListPredicateEntity = new ArrayList<>();
            if (!CollectionUtils.isEmpty(listOfListPredicateContract)) {
                listOfListPredicateContract.forEach(listPredicateContract -> {
                    listOfListPredicateEntity.add(PREDICATE_ENTITY_TRANSLATOR.translateTo(listPredicateContract));
                });
            }
            ruleEntity.setVariation(ruleContract.getVariation());
            ruleEntity.setClauses(listOfListPredicateEntity);
            return ruleEntity;
        }
    }


    private class PredicateEntityTranslator extends BaseTranslator<PredicateContract, PredicateEntity> {
        @Override
        public PredicateEntity translateTo(PredicateContract predicateContract) {
            PredicateEntity predicateEntity = new PredicateEntity();
            predicateEntity.setKeyToMatch(predicateContract.getKeyToMatch());
            predicateEntity.setValueToMatch(predicateContract.getValueToMatch());
            return predicateEntity;
        }
    }

}
