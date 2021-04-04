package com.shamlou.agewidget.ui

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.widget.CalendarView.OnDateChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shamlou.agewidget.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_user_registererd.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : ViewModelMain by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        setResult(RESULT_CANCELED);
        viewModel.setAppWidgetId(intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID))

//
//
//        val resultValue = Intent()
//        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
//        setResult(Activity.RESULT_OK, resultValue)
//        finish()

        setCalnderStuff()
        observeViewModel()
    }

    private fun setCalnderStuff(){

        calendar_view.date = GregorianCalendar(1998, 5, 9).timeInMillis
        calendar_view.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            viewModel.setSelectedDateButWeAreNotSure(year,month,dayOfMonth)
        }
    }

    private fun observeViewModel(){

        viewModel.userBirthCache.observe(this , Observer {

        })
        viewModel.offerGetName.observe(this , Observer {

        })
    }
}