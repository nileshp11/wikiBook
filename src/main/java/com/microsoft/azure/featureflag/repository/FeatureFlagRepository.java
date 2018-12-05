package com.microsoft.azure.featureflag.repository;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagRepository extends DocumentDbRepository<FeatureFlagEntity, String> {
}

