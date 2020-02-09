package io.ssnc.ac.accessControl.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Configuration
class ControllerConfig {
    private val log: Logger = LoggerFactory.getLogger(ControllerConfig::class.java)

}