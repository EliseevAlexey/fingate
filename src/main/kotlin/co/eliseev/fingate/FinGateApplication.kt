package co.eliseev.fingate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinGateApplication

fun main(args: Array<String>) {
    runApplication<FinGateApplication>(*args)
}
