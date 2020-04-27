package io.ssnc.ac.escort

import io.ssnc.ac.escort.service.AuthService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainApiControllerTest {
    private val authService = AuthService()

    @Test
    fun `partially covered endecrypt method test`() {
        val encrypt = authService.passwordEndecrypt("test123!@")
    }
}