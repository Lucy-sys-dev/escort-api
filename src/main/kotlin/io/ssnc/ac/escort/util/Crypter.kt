package io.ssnc.ac.escort.util

interface Crypter {
    /** Decrypts a String  */
    @Throws(RuntimeException::class)
    fun decrypt(value: String?): String

    /** Encrypts a String  */
    @Throws(RuntimeException::class)
    fun encrypt(value: String?): String
}