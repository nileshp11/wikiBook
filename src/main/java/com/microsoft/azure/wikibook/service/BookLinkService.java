package com.microsoft.azure.wikibook.service;

import com.google.common.hash.Hashing;
import com.microsoft.azure.wikibook.dao.LinkItemRepository;
import com.microsoft.azure.wikibook.model.BookLinkItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class BookLinkService {

  @Autowired
  private LinkItemRepository linkItemRepository;

  public BookLinkItem findLinkById(String linkTitle) {
    final String linkUuid = getHashOfText(linkTitle);
    return linkItemRepository.findById(linkUuid).get();
  }

  public BookLinkItem saveBookLink(BookLinkItem bookLinkItem) {
    final String bookLinkId = getHashOfText(bookLinkItem.getTitle());
    bookLinkItem.setId(bookLinkId);
    final BookLinkItem persistedBookLinkItem = linkItemRepository.save(bookLinkItem);
    return persistedBookLinkItem;
  }

  private final String getHashOfText(String text) {
    final String hashString = Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    return hashString;
  }
}
