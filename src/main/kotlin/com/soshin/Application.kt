package com.soshin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


// No need to put main inside a class
// Also, no need to declare main static, as anything outside of a class is static by default
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

// Let SpringBoot that this is our application
@SpringBootApplication
/**
 * Kotlin classes are "Java final" by default
 * Annotated classes must be declared open
 */
open class Application
// And that's it. SpringBoot will take it from here