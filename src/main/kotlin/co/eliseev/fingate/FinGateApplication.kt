package co.eliseev.fingate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FinGateApplication

fun main(args: Array<String>) {
    runApplication<FinGateApplication>(*args)
}
