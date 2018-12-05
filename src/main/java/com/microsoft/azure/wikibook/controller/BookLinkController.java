package com.microsoft.azure.wikibook.controller;

import com.google.common.hash.Hashing;
import com.microsoft.azure.Routes;
import com.microsoft.azure.wikibook.dao.LinkItemRepository;
import com.microsoft.azure.wikibook.model.BookLinkItem;
import com.microsoft.azure.wikibook.service.BookLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class BookLinkController {

  @Autowired
  private BookLinkService bookLinkService;

  public BookLinkController() {
  }

 /* @RequestMapping(Routes.HOME)
  public Map<String, Object> home() {
    final Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "home");
    return model;
  }*/

  @GetMapping(value = Routes.LINK)
  public ResponseEntity<?> getLink(@PathVariable("linkTitle") String linkTitle) {
    try {
      return new ResponseEntity<>(bookLinkService.findLinkById(linkTitle), HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(linkTitle + " not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = Routes.LINKS)
  public ResponseEntity<BookLinkItem> addNewBookLink(@RequestBody BookLinkItem bookLinkItem) {
    try {
      final BookLinkItem persistedBookLinkItem = bookLinkService.saveBookLink(bookLinkItem);
      return new ResponseEntity<>(persistedBookLinkItem, HttpStatus.CREATED);
    } catch (Exception ex) {
      return new ResponseEntity<>(bookLinkItem, HttpStatus.CONFLICT);
    }
  }
}
