package com.example.nodeapp

data class Todo(
    var id: String? = null,
    var header: String? = null,
    var content: String? = null,
    var isDone: Boolean? = null
) {
    companion object {
        const val PATH = "Todos"
    }
}