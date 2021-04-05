package com.shamlou.agewidget.base

abstract class UseCaseBaseSuspend<T, R> {
    abstract suspend fun invoke(param: T): R
}