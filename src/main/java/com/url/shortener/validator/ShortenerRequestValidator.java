package com.url.shortener.validator;

import com.url.shortener.model.ShortenerRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShortenerRequestValidator implements Validator {

  private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

  private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

  private boolean isValidUrl(String url) {
    Matcher m = URL_PATTERN.matcher(url);
    return m.matches();
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return ShortenerRequest.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ShortenerRequest shortenerRequest = (ShortenerRequest) o;
    if (!isValidUrl(shortenerRequest.getUrl())) {
      throw new RuntimeException("Please enter a valid URL");
    }
  }
}
