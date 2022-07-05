package com.example.loans.extensions

object InputValidator {

    fun phoneNumberValidate(phoneNumber: String) : Boolean {
        val regex = Regex("[0-9]{11}")
        return regex.matches(phoneNumber)
    }

    fun userNameValidator(name: String) : Boolean {
        val regex = Regex("[А-Я][а-яА-Я]{2,12}")
        return regex.matches(name)
    }

}