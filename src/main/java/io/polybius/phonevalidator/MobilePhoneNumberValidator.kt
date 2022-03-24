package io.polybius.phonevalidator

import io.polybius.phonevalidator.PhoneRestrictions.isRestrictionRespected

class MobilePhoneNumberValidator {
    fun validate(phoneNumbers: List<String>): ValidationResultDto {
        val result = ValidationResultDto()

        phoneNumbers.forEach { number ->
            val phoneNumber = replaceNonNumberSymbols(number)
            val countryCode = resolveCountryCode(phoneNumber)
            if (countryCode != null && isValid(phoneNumber, countryCode)) {
                result.validPhonesByCountry.putIfAbsent(countryCode, mutableListOf())
                result.validPhonesByCountry[countryCode]?.add(number)
            } else {
                result.invalidPhones.add(number)
            }
        }
        return result
    }

    private fun isValid(phoneNumber: String, countryCode: CountryCode): Boolean {
        return if (!hasOnlyAllowedSymbols(phoneNumber)) false
        else isRestrictionRespected(countryCode, phoneNumber)
    }

    private fun resolveCountryCode(phoneNumber: String) =
        CountryCode.values().find { countryCode -> phoneNumber.startsWith(countryCode.code) }

    private fun replaceNonNumberSymbols(phoneNumber: String): String {
        var transformedNumber = phoneNumber
            .replace(" ", "")
            .replace("-", "")

        val indexOfOpenedBrace = transformedNumber.indexOf("(")
        val indexOfClosedBrace = transformedNumber.indexOf(")")
        if (indexOfClosedBrace > indexOfOpenedBrace) {
            transformedNumber = transformedNumber
                .replaceFirst("(", "")
                .replaceFirst(")", "")
        }

        val indexOfPlus = transformedNumber.indexOf("+")
        if (indexOfPlus == 0) {
            transformedNumber = transformedNumber.replaceFirst("+", "")
        }
        return transformedNumber
    }

    private fun hasOnlyAllowedSymbols(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty()) return false
        try {
            phoneNumber.toLong()
        } catch (ex: NumberFormatException) {
            return false
        }
        return true
    }
}