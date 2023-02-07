package com.mukjipsa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class MukjipsaApplication

fun main(args: Array<String>) {
    runApplication<MukjipsaApplication>(*args)
}
