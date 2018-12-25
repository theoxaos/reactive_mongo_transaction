package com.theoxao.reactive_mongo_mixed

class User {
    var name: String? = null
    var age: Int? = null

    constructor()

    constructor(name: String?, age: Int?) {
        this.name = name
        this.age = age
    }

}