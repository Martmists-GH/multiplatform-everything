package com.martmists.multiplatform.validation

import kotlin.jvm.JvmInline


@JvmInline
value class CreditCard private constructor(private val digits: String) {
    fun validate() {
        val issuer = this.issuer()

        require(issuer != Issuer.BANKCARD ||
                issuer != Issuer.LASER ||
                issuer != Issuer.NPS_PRIDNESTROVIE ||
                issuer != Issuer.SOLO ||
                issuer != Issuer.SWITCH
        ) { "Issuer ($issuer) is no longer active." }

        validateLength(issuer)

        if (issuer != Issuer.UZ_CARD &&
            issuer != Issuer.HUMO &&
            issuer != Issuer.NAPAS
        ) {
            validateLuhn()
        }
    }

    private fun validateLength(issuer: Issuer) {
        if (issuer == Issuer.VISA || issuer == Issuer.VISA_ELECTRON) {
            require(digits.length == 13 || digits.length == 16 || digits.length == 19) { "Invalid length for issuer $issuer." }
            return
        }

        val lengthRange = when (issuer) {
            Issuer.AMERICAN_EXPRESS, Issuer.UATP -> 15..15
            Issuer.BANKCARD, Issuer.DINERS_CLUB_US_CA, Issuer.RU_PAY, Issuer.INSTA_PAYMENT,
            Issuer.DANKORT, Issuer.BORICA, Issuer.MASTERCARD, Issuer.TROY,
            Issuer.LANKA_PAY, Issuer.UZ_CARD, Issuer.HUMO -> 16..16
            Issuer.CHINA_T_UNION -> 19..19
            Issuer.CHINA_UNIONPAY, Issuer.DISCOVER_CARD, Issuer.UKR_CARD, Issuer.INTER_PAYMENT,
            Issuer.DINERS_CLUB_INTERNATIONAL -> 14..19
            Issuer.MAESTRO_UK, Issuer.MAESTRO -> 12..19
            else -> 16..19
        }

        require(digits.length in lengthRange) { "Invalid length for issuer $issuer." }

        if (issuer == Issuer.GPN || issuer == Issuer.NAPAS || issuer == Issuer.UATP) {
            require(digits.length != 17) { "Invalid length for issuer $issuer." }
        }

        if (issuer == Issuer.NAPAS) {
            require(digits.length != 18) { "Invalid length for issuer $issuer." }
        }
    }

    private fun validateLuhn() {
        val last = digits.last().digitToInt()
        val check = digits.dropLast(1).reversed().mapIndexed { index, c ->
            val digit = c.digitToInt()
            val result = digit * if (index % 2 == 0) 2 else 1
            if (result > 9) result - 9 else result
        }.sum()
        require((check + last) % 10 == 0) { "Invalid Luhn checksum." }
    }

    override fun toString() = digits

    enum class Issuer {
        AMERICAN_EXPRESS,
        BANKCARD,
        CHINA_T_UNION,
        CHINA_UNIONPAY,
        DINERS_CLUB_INTERNATIONAL,
        DINERS_CLUB_US_CA,
        DISCOVER_CARD,
        UKR_CARD,
        RU_PAY,
        INTER_PAYMENT,
        INSTA_PAYMENT,
        JCB,
        LASER,
        MAESTRO_UK,
        MAESTRO,
        DANKORT,
        MIR,
        BORICA,
        NPS_PRIDNESTROVIE,
        MASTERCARD,
        SOLO,
        SWITCH,
        TROY,
        VISA,
        VISA_ELECTRON,
        UATP,
        VERVE,
        LANKA_PAY,
        UZ_CARD,
        HUMO,
        GPN,
        NAPAS,
    }

    fun issuer(): Issuer {
        val firstTwo = digits.take(2).toInt()
        val firstThree = digits.take(3).toInt()
        val firstFour = digits.take(4).toInt()
        val firstSix = digits.take(6).toInt()
        val firstSeven = digits.take(7).toInt()
        val firstEight = digits.take(8).toInt()

        // FIXME: Find a reliable source for this
        // The only option I've found is to purchase the data for $500 which I'm not willing to do
        return when {
            firstTwo == 34 || firstTwo == 37 -> Issuer.AMERICAN_EXPRESS
            firstFour == 5610 || (firstSix in 560221..560225) -> Issuer.BANKCARD
            firstTwo == 31 -> Issuer.CHINA_T_UNION
            firstTwo == 62 -> Issuer.CHINA_UNIONPAY
            firstTwo == 36 || firstTwo == 38 || (firstThree in 300..305) -> Issuer.DINERS_CLUB_INTERNATIONAL
            firstTwo == 55 -> Issuer.DINERS_CLUB_US_CA
            firstFour == 6011 || (firstThree in 644..649) || firstTwo == 65 || (firstSix in 622126..622925) -> Issuer.DISCOVER_CARD
            firstEight in 60400100..60420099 -> Issuer.UKR_CARD
            firstTwo == 60 || firstTwo == 65 || firstTwo == 81 || firstTwo == 82 || firstThree == 508 || firstThree == 353 || firstThree == 356 -> Issuer.RU_PAY
            firstThree == 636 -> Issuer.INTER_PAYMENT
            firstThree in 637..639 -> Issuer.INSTA_PAYMENT
            firstFour in 3528..3589 -> Issuer.JCB
            firstFour == 6304 || firstFour == 6706 || firstFour == 6771 || firstFour == 6709 -> Issuer.LASER
            firstFour == 6759 || firstSix == 676770 || firstSix == 676774 -> Issuer.MAESTRO_UK
            firstFour == 5018 || firstFour == 5020 || firstFour == 5038 || firstFour == 5893 || firstFour == 6304 || firstFour == 6759 || firstFour == 6761 || firstFour == 6762 || firstFour == 6763 -> Issuer.MAESTRO
            firstFour == 5019 || firstFour == 4571 -> Issuer.DANKORT
            firstFour in 2200..2204 -> Issuer.MIR
            firstSeven in 6054740..6054744 -> Issuer.NPS_PRIDNESTROVIE
            (firstFour in 2221..2720) || (firstTwo in 51..55) -> Issuer.MASTERCARD
            firstFour == 6334 || firstFour == 6767 -> Issuer.SOLO
            firstFour == 4903 || firstFour == 4905 || firstFour == 4911 || firstFour == 4936 || firstSix == 564182 || firstSix == 633110 || firstFour == 6333 || firstFour == 6759 -> Issuer.SWITCH
            firstTwo == 65 || firstFour == 9792 -> Issuer.TROY
            digits.first() == '4' -> Issuer.VISA
            firstFour == 4026 || firstSix == 417500 || firstFour == 4508 || firstFour == 4844 || firstFour == 4913 || firstFour == 4917 -> Issuer.VISA_ELECTRON
            digits.first() == '1' -> Issuer.UATP
            firstSix in 506099..506198 || firstSix in 650002..650027 || firstSix in 507865..507964 -> Issuer.VERVE
            firstSix == 357111 -> Issuer.LANKA_PAY
            firstFour == 8600 || firstFour == 5614 -> Issuer.UZ_CARD
            firstFour == 9860 -> Issuer.HUMO
            firstFour == 1946 || firstTwo == 50 || firstTwo == 56 || firstTwo == 58 || (firstTwo in 60..63) -> Issuer.GPN
            firstFour == 9704 -> Issuer.NAPAS
            else -> throw IllegalArgumentException("Unknown issuer.")
        }
    }

    companion object {
        operator fun invoke(digits: String): CreditCard {
            return CreditCard(digits).also { it.validate() }
        }

        operator fun invoke(digits: Long): CreditCard {
            return CreditCard(digits.toString()).also { it.validate() }
        }

        @UnsafeSkipValidation
        fun fromValid(digits: String): CreditCard {
            return CreditCard(digits)
        }

        @UnsafeSkipValidation
        fun fromValid(digits: Long): CreditCard {
            return CreditCard(digits.toString())
        }
    }
}
