package com.shamlou.agewidget.domain

import com.shamlou.agewidget.db.user.UserBirth

data class UserBirthDomain(
    val firstName: String,
    val userBirthDomain: BirthDomain
)

fun UserBirthDomain.toRemote() = UserBirth(1 , firstName ,
    userBirthDomain.birthDay ,
    userBirthDomain.birthMonth ,
    userBirthDomain.birthYear,
    userBirthDomain.birthDateFormated
)