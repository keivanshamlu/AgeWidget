package com.shamlou.agewidget.repository.birth

import com.shamlou.agewidget.db.user.UserBirth
import com.shamlou.agewidget.domain.BirthDomain
import com.shamlou.agewidget.domain.UserBirthDomain

val fakeUserBirth = UserBirth(1 , "first_name" , "birth_day" , "birth_month" , "birth_year","birth_date_formated")
val fakeUserBirthDomain = UserBirthDomain("firstname" , BirthDomain("birth_day" , "birth_month" , "birth_year","birth_date_formated"))
