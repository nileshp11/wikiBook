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
    String matchedVariation = null;
    FeatureFlagContract featureFlagContract = getFeatureFlag(featureFlagKey);
    if (featureFlagContract == null) {
      return matchedVariation;
    }

    if (!featureFlagContract.getEnabled()) {
      return featureFlagContract.getDefaultVariation();
    }

    Map<String, EnvContract> envs = featureFlagContract.getEnvs();
    if (envs == null || envs.size() == 0 || !envs.containsKey(env)) {
      return matchedVariation;
    }

    EnvContract envContract = envs.get(env);
    if (!envContract.getEnabled()) {
      return matchedVariation;
    }

    List<RuleContract> ruleContracts = envContract.getRules();
    if (CollectionUtils.isEmpty(ruleContracts)) {
      return matchedVariation;
    }
    for (RuleContract ruleContract : ruleContracts) {
      matchedVariation = ruleContract.getVariation();
      if (evaluateListOfOrs(userContract, ruleContract.getClauses())) {
        return matchedVariation;
      }
    }

    return matchedVariation;
  }

  private boolean evaluateListOfOrs(UserContract userContract, List<List<PredicateContract>> listOfOrs) {
    boolean result = false;
    for (List<PredicateContract> listOfAnds : listOfOrs) {
      result = result || evaluateListOfAnds(userContract, listOfAnds);
      if (result) {
        return result;
      }
    }
    return result;
  }

  private boolean evaluateListOfAnds(UserContract userContract, List<PredicateContract> listOfAnds) {
    boolean result = true;
    for (PredicateContract predicateContract : listOfAnds) {
      result = result && evaluatePredicate(userContract, predicateContract);
      if (!result) {
        return result;
      }
    }
    return result;
  }

  private boolean evaluatePredicate(UserContract userContract, PredicateContract predicateContract) {
    String keyToMatch = predicateContract.getKeyToMatch();
    String valueToMatch = predicateContract.getValueToMatch();
    Pattern patternToMatch = Pattern.compile(valueToMatch);

    for (Field userField : USER_FIELDS) {
      String userFieldName = userField.getName();
      if (userFieldName.equalsIgnoreCase(keyToMatch)) {
        String userFieldValue = null;
        try {
          userFieldValue = (String) userField.get(userContract);
        } catch (IllegalAccessException e) {
          LOGGER.error("Could not access field {} of userContract: {}", userFieldName, userContract);
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
