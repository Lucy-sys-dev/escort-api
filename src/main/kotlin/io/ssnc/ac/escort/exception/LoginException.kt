package io.ssnc.ac.escort.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class LoginException(message: String?) : RuntimeException(message) {
    var result = HashMap<String, String>()
//    result.put("ko", "dfdfdf")
//    result.put("en", "sdsdsds")
}

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
class PasswordException(message: String?) : RuntimeException(message)