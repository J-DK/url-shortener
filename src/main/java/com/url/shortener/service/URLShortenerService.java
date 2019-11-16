package com.url.shortener.service;

public interface URLShortenerService {

  String shortenURL(String baseURL, String urlToShorten);

  String getOriginalURLById(String id);
}
