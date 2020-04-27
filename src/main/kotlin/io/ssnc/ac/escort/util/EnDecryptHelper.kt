package io.ssnc.ac.escort.util

import io.ssnc.ac.escort.config.SiteConfig
import org.jasypt.exceptions.EncryptionOperationNotPossibleException
import org.jasypt.util.password.StrongPasswordEncryptor
import org.jasypt.util.text.BasicTextEncryptor

object EnDecryptHelper {
    private val strongEncryptor = StrongPasswordEncryptor()
    private var basicTextEncryptor = BasicTextEncryptor()

    init {
        basicTextEncryptor.setPassword(SiteConfig.enDecryptPassword)
    }

    /**
     * Encrypt password
     *
     * @param password
     * @return
     */
    @JvmStatic
    fun encryptSaltPassword(password: String): String = strongEncryptor.encryptPassword(password)

    @JvmStatic
    fun encryptText(text: String): String = basicTextEncryptor.encrypt(text)


    @JvmStatic
    fun decryptText(text: String): String = try {
        basicTextEncryptor.decrypt(text)
    } catch (e: EncryptionOperationNotPossibleException) {
        throw RuntimeException("Can not decrypt the text--$text---")
    }

    /**
     * Check password `inputPassword` match with
     * `expectedPassword` in case `inputPassword` encrypt
     * or not
     *
     * @param inputPassword
     * @param expectedPassword
     * @param isPasswordEncrypt flag to denote `inputPassword` is encrypted or not
     * @return
     */
    @JvmStatic
    fun checkPassword(inputPassword: String, expectedPassword: String, isPasswordEncrypt: Boolean): Boolean = when {
        isPasswordEncrypt -> inputPassword == expectedPassword
        else -> strongEncryptor.checkPassword(inputPassword, expectedPassword)
    }
}
