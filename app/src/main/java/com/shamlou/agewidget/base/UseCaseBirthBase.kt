package com.shamlou.agewidget.base

import kotlinx.coroutines.flow.Flow

abstract class UseCaseBirthBase <in Parameter, Response> {
    abstract operator fun invoke(param: Parameter): Flow<BirthResource<Response>>
}