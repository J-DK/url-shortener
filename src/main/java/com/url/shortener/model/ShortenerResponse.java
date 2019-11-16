package com.url.shortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortenerResponse {
  private String shortenedURL;
}
