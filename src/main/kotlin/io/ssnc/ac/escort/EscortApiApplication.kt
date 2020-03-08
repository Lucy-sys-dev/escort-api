package io.ssnc.ac.escort

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration(exclude = [(RepositoryRestMvcAutoConfiguration::class)])
class EscortApiApplication

fun main(args: Array<String>) {
    runApplication<EscortApiApplication>(*args)
}
