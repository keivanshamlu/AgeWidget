package com.shamlou.agewidget.ui

import androidx.lifecycle.ViewModel
import com.shamlou.agewidget.usecases.UseCaseCheckUserBirthCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelMain
@Inject constructor(private val useCaseCheckUserBirthCache: UseCaseCheckUserBirthCache) : ViewModel() {
}