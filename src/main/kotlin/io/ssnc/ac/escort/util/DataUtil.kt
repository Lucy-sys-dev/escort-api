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
        if (continuousPwd(password)) return false
        val passwordPattern = "[a-zA-Z0-9*&%$#~!@^()?:;',._]{8,16}"
        pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password.trim())
        return matcher.matches()

    }

    fun continuousPwd(pwd: String): Boolean {
        var o = 0
        var d = 0
        var p = 0
        var n = 0
        val limit = 2
        for (i in pwd.indices) {
            val tempVal = pwd[i]
            if (i > 0 && o - tempVal.toInt().also { p = it } > -2 && (if (p == d) n + 1 else 0.also {
                    n = it
                }) > limit - 1) {
                return true
            }
            d = p
            o = tempVal.toInt()
        }
        return false
    }

}