package me.soshin.kotlinSpringBoot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinSpringBootApplication

fun main(args: Array<String>) {
    // That part of boilerplate you still cannot get rid of
    SpringApplication.run(KotlinSpringBootApplication::class.java, *args)
}
