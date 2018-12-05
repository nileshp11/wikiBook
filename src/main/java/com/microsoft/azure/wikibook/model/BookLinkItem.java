package com.microsoft.azure.wikibook.model;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;

@Document
public class BookLinkItem {
  private String id;
  private String title;
  private String wikiLink;
  private String youtubeLink;
  private String externalLink;
  private String subject;
  private String context;

  public BookLinkItem() {
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getWikiLink() {
    return wikiLink;
  }

  public String getYoutubeLink() {
    return youtubeLink;
  }

  public String getExternalLink() {
    return externalLink;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setWikiLink(String wikiLink) {
    this.wikiLink = wikiLink;
  }

  public void setYoutubeLink(String youtubeLink) {
    this.youtubeLink = youtubeLink;
  }

  public void setExternalLink(String externalLink) {
    this.externalLink = externalLink;
  }
}
