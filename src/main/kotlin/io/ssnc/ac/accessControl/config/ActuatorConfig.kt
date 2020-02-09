package me.yaman.can.sample.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.actuate.redis.RedisHealthIndicator
import javax.sql.DataSource

@Configuration
class ActuatorConfig() {

    @Autowired
    lateinit var dataSource: DataSource

}