package io.ssnc.ac.accessControl.controller

import java.lang.RuntimeException

class NotFoundException(message: String,
                        val httpCode : Int = 400
) : RuntimeException(message)