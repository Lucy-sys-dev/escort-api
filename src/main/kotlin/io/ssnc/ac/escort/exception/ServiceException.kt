package io.ssnc.ac.escort.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ServiceException(message: String): Exception(message) {
}