package io.ssnc.ac.escort.entity.request

import com.sun.istack.NotNull

data class RegisterUserPwRequest (
    @NotNull var affiliate: String,
    @NotNull var id: String,
    @NotNull var pwd: String
)