package io.ssnc.ac.accessControl

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration(exclude = [(RepositoryRestMvcAutoConfiguration::class)])
class AccessControlApplication

fun main(args: Array<String>) {
    runApplication<AccessControlApplication>(*args)
}
