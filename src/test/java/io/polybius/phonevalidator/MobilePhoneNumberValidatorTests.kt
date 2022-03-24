package io.polybius.phonevalidator

import io.polybius.phonevalidator.CountryCode.LT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.platform.commons.logging.LoggerFactory

class MobilePhoneNumberValidatorTest {
    private val validator = MobilePhoneNumberValidator()
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "+37061111111.",
            "+37061111111h",
            "+37061111111%",
            "+37061111111!",
            "+37061111111@",
            "+37061111111#",
            "+37061111111^",
            "+37061111111*",
            "+370)6(1111111*",
            "+370(61111111*",
            "+3706)1111111*",
            "370+61111111*",
            "",
            " ",
        ]
    )
    fun testInvalidBySymbols(wrongPhone: String) {
        testInValidPhone(wrongPhone)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "377 6 1111111", // Wrong country code
            "370 2 1111111", // Wrong starting
            "370 6 111111",  // Wrong length
            "370 6 11111111",// Wrong length
        ]
    )
    fun testInvalidByRestrictions(wrongPhone: String) {
        testInValidPhone(wrongPhone)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "37061111111",
            "+37061111111",
            "+370(6)1111111",
            "+370 611 11 111",
            "+370-611-11-111",
        ]
    )
    // Test for examples from readme
    fun testValidPhoneWithAllowedSymbols(validPhone: String) {
        testValidPhone(validPhone, LT)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "370 6 1111111, LT",
            "371 2 1111111, LV",
            "372 5 111111, EE",
            "372 5 1111111, EE",
            "32 456 111111, BE",
            "32 47 6111111, BE",
            "32 48 0111111, BE",
            "32 49 0111111, BE",
        ]
    )
    fun testValidPhonesForDifferentCountries(validPhone: String, countryCode: CountryCode) {
        testValidPhone(validPhone, countryCode)
    }

    @Test
    fun testValidateFewPhones() {
        val validPhonesExpected = listOf("+370 611 11 111", "+370-611-11-111")
        val inValidPhonesExpected = listOf("370 2 1111111", "370 6 111111")
        val (validPhonesActual, invalidPhonesActual) =
            validator.validate(validPhonesExpected + inValidPhonesExpected)

        assertEquals(validPhonesExpected, validPhonesActual[LT])
        assertEquals(inValidPhonesExpected, invalidPhonesActual)
    }

    @Test
    fun checkConfigExistForAllCountries() {
        assertEquals(CountryCode.values().toSet(), PhoneRestrictions.restrictions.keys)
    }


    private fun testValidPhone(phoneNumber: String, shouldBeCounry: CountryCode) {
        val (validPhonesActual, invalidPhonesActual) = validator.validate(listOf(phoneNumber))
        logger.info { "validPhonesActual: $validPhonesActual" }
        logger.info { "invalidPhonesActual: $invalidPhonesActual" }
        assertEquals(
            listOf(phoneNumber),
            validPhonesActual[shouldBeCounry]
        )
        assertFalse(invalidPhonesActual.contains(phoneNumber))
    }

    private fun testInValidPhone(phoneNumber: String) {
        val (validPhonesActual, invalidPhonesActual) = validator.validate(listOf(phoneNumber))
        logger.info { "validPhonesActual: $validPhonesActual" }
        logger.info { "invalidPhonesActual: $invalidPhonesActual" }
        assertTrue(invalidPhonesActual.contains(phoneNumber))
        assertTrue(validPhonesActual.isEmpty())
    }
}
