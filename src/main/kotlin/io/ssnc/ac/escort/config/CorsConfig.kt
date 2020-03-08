package io.ssnc.ac.escort.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD")
//                .allowedOrigins("*")
    }
}

@Configuration
class DataRestCorsConfigurer : RepositoryRestConfigurerAdapter() {
    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
        config.corsRegistry.addMapping(config.basePath.path + "/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
    }
}