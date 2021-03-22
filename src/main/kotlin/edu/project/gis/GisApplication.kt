package edu.project.gis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GisApplication

fun main(args: Array<String>) {
    runApplication<GisApplication>(*args)
}
