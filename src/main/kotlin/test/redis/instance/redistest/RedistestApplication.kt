package test.redis.instance.redistest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RedistestApplication

fun main(args: Array<String>) {
	runApplication<RedistestApplication>(*args)
}
