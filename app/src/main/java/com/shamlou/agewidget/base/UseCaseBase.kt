package com.shamlou.agewidget.base

import androidx.lifecycle.LiveData

abstract class UseCaseBase <in Parameter, Response> {
    abstract operator fun invoke(param: Parameter): LiveData<BirthResource<Response>>
}