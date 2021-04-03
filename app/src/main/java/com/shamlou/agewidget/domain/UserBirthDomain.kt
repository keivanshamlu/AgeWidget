package com.shamlou.agewidget.domain

data class UserBirthDomain(
    val firstName: String,
    val birthDay: String,
    val birthMonth: String,
    val birthYear: String,
    val birthDateFormated: String
)