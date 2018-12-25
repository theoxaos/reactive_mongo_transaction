package com.theoxao.reactive_mongo_mixed

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class UserController(private val userService: UserService) {

    @RequestMapping("trans")
    fun trans(a: Int): User? {
        return userService.trans(a)
    }

    @RequestMapping("anno")
    fun anno(a :Int): User? {
        return userService.transAnnotation(a)
    }

    @RequestMapping("react")
    fun react(): Mono<User> {
        return userService.react()
    }

    @RequestMapping("react_trans")
    fun reactTrans(a:Int): Flux<User> {
        return userService.reactTrans(a)
    }
}