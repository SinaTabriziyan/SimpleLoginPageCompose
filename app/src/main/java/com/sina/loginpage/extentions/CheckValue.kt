package com.sina.loginpage.extentions

fun isValidUsername(username: String): Boolean {
    val regex = Regex("^[a-zA-Z0-9_]+$")
    return regex.matches(username)
}

fun isValidPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[A-Z])(?=.*\\d).+$")
    return regex.matches(password)
}