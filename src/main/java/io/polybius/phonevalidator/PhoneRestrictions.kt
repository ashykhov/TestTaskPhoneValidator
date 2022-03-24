package io.polybius.phonevalidator

import io.polybius.phonevalidator.CountryCode.BE
import io.polybius.phonevalidator.CountryCode.EE
import io.polybius.phonevalidator.CountryCode.LT
import io.polybius.phonevalidator.CountryCode.LV

object PhoneRestrictions {
    val restrictions: Map<CountryCode, PhoneRestriction> = mapOf(
        LT to PhoneRestriction(listOf("6"), 8..8),
        LV to PhoneRestriction(listOf("2"), 8..8),
        EE to PhoneRestriction(listOf("5"), 7..8),
        BE to PhoneRestriction(listOf("456", "47", "48", "49"), 9..9),
    )

    fun isRestrictionRespected(countryCode: CountryCode, phone: String): Boolean {
        val restriction = restrictions[countryCode] ?: return false
        if (!phone.startsWith(countryCode.code)) return false

        val phoneWithoutCode = phone.substring(countryCode.code.length)

        if (!phoneWithoutCode.startsWithAny(restriction.startsWith)) return false
        if (!restriction.phoneLength.contains(phoneWithoutCode.length)) return false

        return true
    }

    private fun String.startsWithAny(list: List<String>) =
        !list.find { startChars -> this.startsWith(startChars) }.isNullOrEmpty()


    class PhoneRestriction(
        val startsWith: List<String>,
        val phoneLength: IntRange,
    )
}