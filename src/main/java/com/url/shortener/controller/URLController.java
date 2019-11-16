package com.url.shortener.controller;

import com.url.shortener.model.ShortenerRequest;
import com.url.shortener.model.ShortenerResponse;
import com.url.shortener.service.URLShortenerService;
import com.url.shortener.validator.ShortenerRequestValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin("*")
@RequestMapping("bit.url")
@RestController
@Api(value = "APIs for operations pertaining to shorten URLs")
public class URLController {

  private static final Logger log = LoggerFactory.getLogger(URLController.class);

  @Autowired
  private ShortenerRequestValidator validator;

  @Autowired
  private URLShortenerService urlShortenerService;

  @InitBinder("shortenerRequest")
  public void setupBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @ApiOperation(value = "API to shorten the URL")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShortenerResponse> shortenUrl(
      @RequestBody
      @Valid
      final ShortenerRequest shortenerRequest, HttpServletRequest httpServletRequest) {
    log.info("Received url to shorten: " + shortenerRequest.getUrl());
    String baseURL = httpServletRequest.getRequestURL().toString();
    String shortenedUrl = urlShortenerService.shortenURL(baseURL, shortenerRequest.getUrl());
    log.info("Shortened url to: " + shortenedUrl);
    return new ResponseEntity<>(new ShortenerResponse(shortenedUrl), HttpStatus.CREATED);
  }

  @ApiOperation(value = "API to redirect the shortened URL to the original URL")
  @GetMapping(value = "/{id}")
  public RedirectView redirectUrl(@PathVariable String id) {
    log.info("Received shortened url to redirect: " + id);
    String redirectUrlString = urlShortenerService.getOriginalURLById(id);
    log.info("Original URL: " + redirectUrlString);
    RedirectView redirectView = new RedirectView();

    try {
      new URI(redirectUrlString).toURL().getProtocol();
      redirectView.setUrl(redirectUrlString);
    } catch (Exception e) {
      redirectView.setUrl("http://" + redirectUrlString);
    }
    return redirectView;
  }
}
