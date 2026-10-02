package com.example.cryptomarket.my.model

import java.io.Serializable

data class Account(
    val login: String,
    val isMale: Boolean
) : Serializable