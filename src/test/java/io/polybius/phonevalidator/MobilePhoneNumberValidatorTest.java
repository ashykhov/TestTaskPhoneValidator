package io.polybius.phonevalidator;

import com.sun.tools.javac.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MobilePhoneNumberValidatorTest {

  private MobilePhoneNumberValidator validator = new MobilePhoneNumberValidator();

  @Test
  public void validate() {
    ValidationResultDto result = validator.validate(List.of("+37061234567"));
    assertEquals(List.of("+37061234567"), result.validPhonesByCountry.get("LT"));

    result = validator.validate(List.of("+37091234567"));
    assertEquals(List.of("+37091234567"), result.invalidPhones);

    result = validator.validate(List.of("+3706123456"));
    assertEquals(List.of("+3706123456"), result.invalidPhones);
  }
}