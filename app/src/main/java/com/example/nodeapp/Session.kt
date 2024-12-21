package com.example.nodeapp

import com.google.firebase.auth.FirebaseUser

class Session private constructor() {
    private var mUser: FirebaseUser? = null
    var user
        get() = mUser
        set(value) {
            mUser = value
        }
    fun logOut() {
        mUser = null
    }
    companion object {
        val instance: Session by lazy { Session() }
    }
}