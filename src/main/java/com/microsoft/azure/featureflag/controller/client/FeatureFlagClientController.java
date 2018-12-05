package com.microsoft.azure.featureflag.controller.client;

import com.microsoft.azure.featureflag.controller.FeatureFlagContract;
import com.microsoft.azure.featureflag.service.FeatureFlagClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController public class FeatureFlagClientController {
  private static Logger LOGGER = LoggerFactory.getLogger(FeatureFlagClientController.class);

  @Autowired
  private FeatureFlagClientService featureFlagClientService;

  @RequestMapping(value = "/client/featureflags/{featureFlagKey}/variation", method = RequestMethod.PUT, produces =
      MediaType
      .APPLICATION_JSON_VALUE)
  public ResponseEntity<String> evaluateFeatureFlag(
      @PathVariable String featureFlagKey,
      @RequestParam(value = "env", required = true) String env,
      @RequestBody UserContract userContract) {
    LOGGER.info("Received request to evaluate feature-flag with key: {}, user: {}, env: {}",
        featureFlagKey,
        userContract,
        env);
    String variation = featureFlagClientService.findVariation(featureFlagKey, userContract, env);
    LOGGER.info("Evaluated variation: {}", variation);
    return ResponseEntity.ok(variation);
  }
}
