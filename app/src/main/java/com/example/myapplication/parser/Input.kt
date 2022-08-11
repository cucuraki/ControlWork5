package com.example.myapplication.parser

data class Input(
    val fieldId: Int,
    val hint: String,
    val fieldType: String,
    val required: Boolean,
    var keyboard: String? = null,
    val isActive: Boolean,
    val icon: String
)
