package org.kimbs.uracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrackerApplication

fun main(args: Array<String>) {
	runApplication<UrackerApplication>(*args)
}
