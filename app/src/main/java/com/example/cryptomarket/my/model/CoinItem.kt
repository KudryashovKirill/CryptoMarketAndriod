package com.example.cryptomarket.my.model

data class CoinItem(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val changePercent24Hr: Double
)