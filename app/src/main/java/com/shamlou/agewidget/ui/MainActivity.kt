package com.shamlou.agewidget.ui

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shamlou.agewidget.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : ViewModelMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setResult(RESULT_CANCELED);
//        val mAppWidgetId = intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//
//
//        val resultValue = Intent()
//        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
//        setResult(Activity.RESULT_OK, resultValue)
//        finish()


        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.userBirthCache.observe(this , Observer {

        })
    }
}