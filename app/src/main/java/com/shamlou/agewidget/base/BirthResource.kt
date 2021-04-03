package com.shamlou.agewidget.base

data class BirthResource<out T>(val status: Status, val data: T?) {
    companion object {
        fun <T> registered(data: T?): BirthResource<T> {
            return BirthResource(
                    Status.REGISTERED,
                    data
            )
        }

        fun <T> notRegistered(data: T?): BirthResource<T> {
            return BirthResource(
                    Status.NOT_REGISTERED,
                    data
            )
        }

        fun <T> loading(data: T?): BirthResource<T> {
            return BirthResource(
                    Status.LOADING,
                    data
            )
        }
    }

    enum class Status {
        REGISTERED,
        NOT_REGISTERED,
        LOADING
    }
}