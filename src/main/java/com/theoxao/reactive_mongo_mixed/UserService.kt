package com.theoxao.reactive_mongo_mixed

import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
open class UserService(private val mongoTemplate: MongoTemplate,
                       private val reactiveMongoTemplate: ReactiveMongoTemplate,
                       private val mongoTransactionManager: MongoTransactionManager,
                       private val reactiveMongoOperations: ReactiveMongoOperations) {

    /**
     * native transaction
     */
    fun trans(a: Int): User? {
        if (!mongoTemplate.collectionExists(User::class.java)) {
            mongoTemplate.createCollection(User::class.java)
        }
        val transactionTemplate = TransactionTemplate(mongoTransactionManager)
        return transactionTemplate.execute<User> {
            mongoTemplate.save(randomUser())
            val b = 1 / a
            mongoTemplate.save(randomUser())
        }
    }

    /**
     * transaction with annotation
     */
    @Transactional
    open fun transAnnotation(a: Int): User? {
        mongoTemplate.save(randomUser())
        val b = 1 / a
        return mongoTemplate.save(randomUser())
    }

    /**
     * reactive mongo
     */
    open fun react(): Mono<User> {
        return reactiveMongoTemplate.save(randomUser())
    }

    /**
     * reactive mongo transaction
     */
    open fun reactTrans(a: Int): Flux<User> {
        return reactiveMongoOperations.inTransaction().execute {
            it.save(randomUser())
                    .doOnNext { _ ->
                        val b = 1 / a
                        it.save(randomUser())
                    }.flatMap { _ ->
                        it.save(randomUser())
                    }
//                    .then(Mono.just(User("mistake", 1 / a)))
//                    .then()
        }
    }

    companion object {
        private val names = "theo,theoxao,sophia,dorothy,theodore,pandora".split(",")
        fun randomUser(): User {
            return User(names[Random.nextInt(0, names.size - 1)], Random.nextInt(10, 40))
        }

    }

}