package io.ssnc.ac.escort.config

import org.joda.time.DateTimeZone
import java.util.*

const val BI_ENDECRYPT_PASSWORD = "endecryptPassword"

class SiteConfig {
    private var defaultLocale: Locale? = null
    private var endecryptPassword: String? = null
    private var dropboxCallbackUrl: String? = null

    companion object {
        private var instance: SiteConfig? = null
        fun loadConfiguration() {
            TimeZone.setDefault(DateTimeZone.UTC.toTimeZone())
            DateTimeZone.setDefault(DateTimeZone.UTC)
            instance = SiteConfig()
            instance!!.defaultLocale = Locale.KOREA
            instance!!.endecryptPassword = BI_ENDECRYPT_PASSWORD
        }

        private fun getInstance(): SiteConfig? {
            if (instance == null) {
                loadConfiguration()
            }
            return instance
        }

        fun getDefaultLocale(): Locale {
            return Locale.KOREA
        }

        val enDecryptPassword: String?
            get() = getInstance()!!.endecryptPassword
    }
}