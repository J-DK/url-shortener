package com.url.shortener.service.impl;

import com.url.shortener.entity.Url;
import com.url.shortener.repository.URLRepository;
import com.url.shortener.service.URLShortenerService;
import com.url.shortener.util.IDConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLShortenerImpl implements URLShortenerService {

  private static final Logger log = LoggerFactory.getLogger(URLShortenerImpl.class);

  @Autowired
  private URLRepository urlRepository;

  @Override
  public String shortenURL(String baseURL, String urlToShorten) {
    log.info("Shortening {}", urlToShorten);
    Long id = urlRepository.count() + 1;
    String uniqueID = IDConverter.createUniqueID(id);
    Url url = new Url();
    url.setKey(id.toString());
    url.setValue(urlToShorten);
    urlRepository.save(url);
    return baseURL + "/" + uniqueID;
  }

  @Override
  public String getOriginalURLById(String id) {
    Long dictionaryKey = IDConverter.getDictionaryKeyFromUniqueID(id);
    Url url = urlRepository.getUrlByKey(dictionaryKey.toString());
    log.info("Converting shortened URL back to {}", url.getValue());
    return url.getValue();
  }
}
