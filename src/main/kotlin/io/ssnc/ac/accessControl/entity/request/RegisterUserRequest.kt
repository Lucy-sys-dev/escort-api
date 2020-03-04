package io.ssnc.ac.accessControl.entity.request

import com.sun.istack.NotNull

data class RegisterUserRequest (
    @NotNull
    var affiliate: String,
    @NotNull
    var id: String,
    @NotNull
    var authentication_code: String
)