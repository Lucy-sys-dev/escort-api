package me.yaman.can.sample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

@Configuration
@EnableSwagger2WebMvc
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
            .apis(RequestHandlerSelectors.any())
            // .apis(RequestHandlerSelectors.basePackage("me.yaman.can.sample.controller"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun getApiInfo(): ApiInfo {
        val contact = Contact("Can Yaman", "http://", "ssnc.dev@secnc.co.kr")
        return ApiInfoBuilder()
                .title("AccessControl")
                .description("AccessControl  Spring Boot 2.0 Application")
                .version("0.0.1")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build()
    }
}