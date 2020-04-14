package io.ssnc.ac.escort.util

import java.lang.Long
import java.util.regex.Matcher
import java.util.regex.Pattern

object DataUtil {
    val lessData: (String, String) -> String = { a, b -> Long.min(a.toLong(), b.toLong()).toString() }

    fun IPisValid(ipAddressString: String?): Boolean {
        if (ipAddressString == null) {
            return false
        }

        val splits = ipAddressString.split(".")
        return splits.size == 4
                && splits.all { it.toSafeInt(0) in (0..255) }
    }

    fun validateIP(ipAddressString: String): String {
        val octets = ipAddressString.split('.')
        var result: String = ""
        for (octet in octets) {
            result += String.format("%03d", octet.toInt())
            result += "."
        }
        return result.substring(0, result.length-1)
    }

    fun IPStringToNumber(ipAddressString: String): String {
        val octets = ipAddressString.split('.')
        var result: String = ""
        for (octet in octets) {
            result += octet.toInt()
            result += "."
        }
        return result.substring(0, result.length-1)
    }

    fun CharSequence?.toSafeInt(defaultValue: Int = 0): Int {
        return try {
            this!!.filter { char ->
                char.isDigit() ||
                        char == '-' ||
                        char == '.'
            }.toString().toInt()
        } catch (t: Throwable) {
            defaultValue
        }
    }

    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        // val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,9}$"
        if (isContinuousPattern(password)) return false
        val passwordPattern = "[a-zA-Z0-9*&%$#~!@^(){}?:;',._\"]{8,16}"
        pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password.trim())
        return matcher.matches()

    }

    /** * 3개 이상 연속된 문자 패턴 체크 * * @param password * @return */
    fun isContinuousPattern(password: String): Boolean {
        val p2 = Pattern.compile("(\\w)\\1\\1")
        val m2 = p2.matcher(password)

        val p3 =
            Pattern.compile("([\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"])\\1\\1")
        val m3 = p3.matcher(password)

        if (m2.find() || m3.find()) {
            return true;
        }

        return false
    }

}