package io.ssnc.ac.accessControl.entity.request

import com.sun.istack.NotNull

data class LoginUserRequest(
    @NotNull
    var affiliate: String,
    @NotNull
    var id: String,
    @NotNull
    var pwd: String
)