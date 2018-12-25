package com.theoxao.reactive_mongo_mixed

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement()
open class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    open fun demo(mongoTemplate: MongoTemplate): CommandLineRunner {
        return CommandLineRunner {
            mongoTemplate.dropCollection(User::class.java)
            mongoTemplate.createCollection(User::class.java)
        }
    }

    @Bean
    open fun mongoTransactionManager(mongoDbFactory: MongoDbFactory): MongoTransactionManager {
        return MongoTransactionManager(mongoDbFactory)
    }
}

