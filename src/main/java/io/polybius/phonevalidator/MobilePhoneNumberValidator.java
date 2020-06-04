package io.polybius.phonevalidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobilePhoneNumberValidator {
  public ValidationResultDto validate(List<String> phoneNumbers) {
    ValidationResultDto result = new ValidationResultDto();
    result.invalidPhones = new ArrayList<>();
    result.validPhonesByCountry = new HashMap<>();
    for (int i = 0; i < phoneNumbers.size(); i++) {
      String phoneNumber = phoneNumbers.get(i);
      boolean isValid;
      String country = null;
      if(phoneNumber.startsWith("370")||phoneNumber.startsWith("+370")) {
        country = "LT";
        phoneNumber = phoneNumber.replaceAll("\\)", "").replaceAll("\\(", "").replaceAll(" ", "").replaceAll("-", "");
        if (phoneNumber.startsWith("370")) {
          isValid = phoneNumber.charAt(3) == '6' && phoneNumber.substring(3).length() == 8;
        }else{
          isValid = phoneNumber.charAt(4) == '6' && phoneNumber.substring(4).length() == 8;
        }
      } else if (phoneNumber.startsWith("+371") || phoneNumber.startsWith("371")) {
        country = "LV";
        if (phoneNumber.startsWith("370")) {
          isValid = phoneNumber.charAt(3) == '2' && phoneNumber.substring(3).length() == 8;
        }
        else {
          isValid = phoneNumber.charAt(4) == '2' && phoneNumber.substring(4).length() == 8;
        }
      } else if (phoneNumber.startsWith("372")) {
        country = "EE";
        phoneNumber = phoneNumber.replaceAll("\\)", "").replaceAll("\\(", "").replaceAll(" ", "").replaceAll("-", "");
        if (phoneNumber.startsWith("+372")) {
          isValid = phoneNumber.charAt(4) == '5' && (phoneNumber.substring(4).length() == 7
              || phoneNumber.substring(4).length() == 8);
        } else {
          isValid = phoneNumber.charAt(3) == '5' && phoneNumber.substring(3).length() == 7;
        }
      } else {
        isValid = false;
      }

      if (isValid) {
        if (!result.validPhonesByCountry.containsKey(country)) {
          result.validPhonesByCountry.put(country, new ArrayList<>());
        }

        result.validPhonesByCountry.get(country).add(phoneNumbers.get(0));
      } else {
        result.invalidPhones.add(phoneNumbers.get(i));
      }
    }


    return result;
  }
}
