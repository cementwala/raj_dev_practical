package com.app.practical.exceptions

class ServerError : RuntimeException {

    val errorBody: String


    constructor(message: String) : super(message) {
        errorBody = ""
    }

    constructor(message: String, errorBody: String) : super(message) {
        this.errorBody = errorBody
    }
}