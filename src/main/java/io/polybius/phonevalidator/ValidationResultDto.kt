package io.polybius.phonevalidator;


data class ValidationResultDto(
    val validPhonesByCountry: MutableMap<CountryCode, MutableList<String>> = mutableMapOf(),
    val invalidPhones: MutableList<String> = mutableListOf(),
)
