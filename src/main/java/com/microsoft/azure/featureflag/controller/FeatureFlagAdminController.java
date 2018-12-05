package com.microsoft.azure.featureflag.controller;

import com.microsoft.azure.featureflag.service.FeatureFlagAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController public class FeatureFlagAdminController {
  private static Logger LOGGER = LoggerFactory.getLogger(FeatureFlagAdminController.class);

  @Autowired
  private FeatureFlagAdminService featureFlagAdminService;

  @PutMapping(
      value = "/admin/featureflag",
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateFeatureFlag(@RequestBody FeatureFlagContract featureFlagContract) {
    LOGGER.info("Received featureFlagContract: {}", featureFlagContract);
    featureFlagAdminService.saveFeatureFlag(featureFlagContract);
    return;
  }

  @RequestMapping(
      value = "/admin/featureflags",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<FeatureFlagContract>> getFeatureFlags() {
    LOGGER.info("Received request to get all feature flags");
    List<FeatureFlagContract> featureFlagContracts = null;
    LOGGER.info("Fetched feature flag: {}", featureFlagContracts);
    featureFlagContracts = featureFlagAdminService.getAllFeatureFlags();
    return ResponseEntity.ok(featureFlagContracts);
  }

}
