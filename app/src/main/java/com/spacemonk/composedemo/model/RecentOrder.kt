package com.spacemonk.composedemo.model

data class RecentOrder(
    val title: String,
    val distributor: String,
    val imageUrl: String,
    val availability: String,
    val amountBefore: String,
    val amountAfter: String
)