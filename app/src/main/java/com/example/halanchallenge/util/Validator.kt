package com.example.halanchallenge.util

import java.util.regex.Pattern

object Validator {
    val USERNAME_PATTERN =Pattern.compile(
        "^(?=.{6,15}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
    fun validateUsername(string: String):Boolean
    {
        return when {
            string.trim().isBlank() ->
                false
            USERNAME_PATTERN.matcher(string.trim()).matches() ->
                true
            else ->
                false
        }
    }
    fun validatePassword(password: String): Boolean {

        return when {
            password.isBlank() ->
                false
            password.length< 6 ->
                false
            else ->
                true
        }
    }
}