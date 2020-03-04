package io.ssnc.ac.accessControl.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class LoginException(message: String?) : RuntimeException(message)