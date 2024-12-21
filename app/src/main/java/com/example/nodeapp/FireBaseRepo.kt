package com.example.nodeapp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FireBaseRepo private constructor(){
    private val auth = Firebase.auth
    private val database = Firebase.database.reference

    suspend fun createAccount(email: String, password: String) {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Session.instance.user = result.user
        }
        catch (e: Exception) {
            Log.e("Create Account", "createAccount: ${e.message}", )
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Session.instance.user = result.user
        }
        catch (e: Exception) {
            Log.e("Login", "login: ${e.message}" )
        }
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun addTodos(todos: List<Todo>) {
        try {
            val uid = auth.uid!!
            database.child(Todo.PATH).child(uid).setValue(todos).await()
        }
        catch (e: Exception) {
            Log.e("Add Todo", "addTodo: ${e.message}" )
        }
    }

    suspend fun getTodos(uid: String): List<Todo> {
        return try {
            val snapshot = database.child(Todo.PATH).child(uid).get().await()
            val genericTypeIndicator = object : GenericTypeIndicator<List<Todo>>() {}
            snapshot.getValue(genericTypeIndicator) ?: emptyList()
        }
        catch (e: Exception) {
            Log.e("Get Todo", "getTodo: ${e.message}" )
            emptyList()
        }
    }

    companion object {
        val instance: FireBaseRepo by lazy {
            FireBaseRepo()
        }

    }

}