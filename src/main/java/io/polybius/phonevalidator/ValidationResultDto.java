package io.polybius.phonevalidator;

import java.util.List;
import java.util.Map;

public class ValidationResultDto {
  public Map<String, List<String>> validPhonesByCountry;
  List<String> invalidPhones;
}
