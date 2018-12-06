package com.microsoft.azure.featureflag.service;

import com.microsoft.azure.featureflag.controller.EnvContract;
import com.microsoft.azure.featureflag.controller.FeatureFlagContract;
import com.microsoft.azure.featureflag.controller.PredicateContract;
import com.microsoft.azure.featureflag.controller.RuleContract;
import com.microsoft.azure.featureflag.controller.client.UserContract;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntity;
import com.microsoft.azure.featureflag.repository.FeatureFlagEntityTranslator;
import com.microsoft.azure.featureflag.repository.FeatureFlagRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service public class FeatureFlagClientService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFlagClientService.class);
  private static final Field[] USER_FIELDS = UserContract.class.getFields();

  @Autowired private FeatureFlagRepository featureFlagRepository;

  private FeatureFlagEntityTranslator FEATURE_FLAG_ENTITY_TRANSLATOR = new FeatureFlagEntityTranslator();

  public String findVariation(String featureFlagKey, UserContract userContract, String env) {
    FeatureFlagContract featureFlagContract = getFeatureFlag(featureFlagKey);
    if (featureFlagContract == null) {
      throw new IllegalStateException("Could not find feature-flag for key: " + featureFlagKey);
    }

    String defaultVariation = featureFlagContract.getDefaultVariation();
    if (StringUtils.isBlank(defaultVariation)) {
      throw new IllegalStateException("Default variation cannot be blank for feature-flag: " + featureFlagKey);
    }

    if (!featureFlagContract.getEnabled()) {
      return defaultVariation;
    }

    Map<String, EnvContract> envs = featureFlagContract.getEnvs();
    if (envs == null || envs.size() == 0 || !envs.containsKey(env)) {
      return defaultVariation;
    }

    EnvContract envContract = envs.get(env);
    if (!envContract.getEnabled()) {
      return defaultVariation;
    }

    List<RuleContract> ruleContracts = envContract.getRules();
    if (CollectionUtils.isEmpty(ruleContracts)) {
      return defaultVariation;
    }
    for (RuleContract ruleContract : ruleContracts) {
      String variationToMatch = ruleContract.getVariation();
      if (evaluateListOfOrs(ruleContract.getClauses(), userContract)) {
        return variationToMatch;
      }
    }

    return defaultVariation;
  }

  private boolean evaluateListOfOrs(List<List<PredicateContract>> listOfOrs, UserContract userContract) {
    boolean result = false;
    if (CollectionUtils.isEmpty(listOfOrs)) {
      return result;
    }

    for (List<PredicateContract> listOfAnds : listOfOrs) {
      result = result || evaluateListOfAnds(listOfAnds, userContract);
      if (result) {
        return result;
      }
    }
    return result;
  }

  private boolean evaluateListOfAnds(List<PredicateContract> listOfAnds, UserContract userContract) {
    boolean result = true;
    if (CollectionUtils.isEmpty(listOfAnds)) {
      return false;
    }

    for (PredicateContract predicateContract : listOfAnds) {
      result = result && evaluatePredicate(predicateContract, userContract);
      if (!result) {
        return result;
      }
    }
    return result;
  }

  private boolean evaluatePredicate(PredicateContract predicateContract, UserContract userContract) {
    String keyToMatch = predicateContract.getKeyToMatch();
    String valueToMatch = predicateContract.getValueToMatch();
    Pattern patternToMatch = Pattern.compile(valueToMatch);

    for (Field userField : USER_FIELDS) {
      String userFieldName = userField.getName();
      if (userFieldName.equals(keyToMatch)) {
        String userFieldValue = null;
        try {
          userFieldValue = (String) userField.get(userContract);
        } catch (IllegalAccessException e) {
          LOGGER.error("Could not access field {} of userContract: {}", userFieldName, userContract);
          throw new IllegalStateException("Could not access field "
              + userFieldName
              + " of userContract: "
              + userContract);
        }

        if (StringUtils.isBlank(userFieldValue)) {
          return false;
        }

        Matcher matcher = patternToMatch.matcher(userFieldValue);
        return matcher.matches();
      }
    }

    for (Map.Entry<String, String> attributeEntry : userContract.getAttributes().entrySet()) {
      String attributeKey = attributeEntry.getKey();
      String attributeValue = attributeEntry.getValue();

      if (attributeKey.equalsIgnoreCase(keyToMatch)) {
        Matcher matcher = patternToMatch.matcher(attributeValue);
        return matcher.matches();
      }
    }
    return false;
  }

  public FeatureFlagContract getFeatureFlag(String featureFlagKey) {
    Optional<FeatureFlagEntity> featureFlagEntity = featureFlagRepository.findById(featureFlagKey);
    LOGGER.info("Retrieved feature flag entity from db: {}", featureFlagEntity.get());
    return FEATURE_FLAG_ENTITY_TRANSLATOR.translateFrom(featureFlagEntity.get());
  }
}
