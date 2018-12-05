package com.microsoft.azure.featureflag.controller;

import com.microsoft.azure.featureflag.service.FeatureFlagAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.awt.PageAttributes;

@RestController
public class FeatureFlagAdminController {
  private static Logger LOGGER = LoggerFactory.getLogger(FeatureFlagAdminController.class);

  @Autowired
  private FeatureFlagAdminService featureFlagAdminService;

  @PutMapping(
      value = "/admin/update",
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateFeatureFlag(@RequestBody FeatureFlagContract featureFlagContract) {
    LOGGER.info("Received featureFlagContract: {}", featureFlagContract);
    featureFlagAdminService.SaveFetureFlag(featureFlagContract);
    return;
  }
}
