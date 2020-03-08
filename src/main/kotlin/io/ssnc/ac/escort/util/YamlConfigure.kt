package io.ssnc.ac.escort.util

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.PathResource

@Configuration
open class YamlConfigure {
    @Bean
    open fun properties(): PropertySourcesPlaceholderConfigurer? {
        val propertySourcesPlaceholderConfigurer = PropertySourcesPlaceholderConfigurer()
        val yaml = YamlPropertiesFactoryBean()
        yaml.setResources(*(
                listOf(
                    ClassPathResource("application.yml"),
                    ClassPathResource("config/application.yml"),
                    PathResource("application.yml"),
                    PathResource("config/application.yml")
                ).filter { it.exists() }
                    .toTypedArray()
                )
        )
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject()!!)
        return propertySourcesPlaceholderConfigurer
    }
}