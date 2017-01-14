package com.soshin

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


/**
 * This controller should return JSON response containing current timestamp
 */

@RestController
class Controller {

    @RequestMapping("/")
    @ResponseBody // Spring will handle the response
    internal fun home(): homeResponse {

        return homeResponse(System.currentTimeMillis())
    }
}

// No need for setters getters for a simple POJO
data class homeResponse(val time: Long, val message: String? = null)
