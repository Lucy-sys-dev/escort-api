package io.ssnc.ac.escort.util

object DataUtil {
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

}