package com.microsoft.azure.wikibook.dao;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.microsoft.azure.wikibook.model.BookLinkItem;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkItemRepository extends DocumentDbRepository<BookLinkItem, String> {
}
