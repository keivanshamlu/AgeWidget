package com.shamlou.agewidget.ui

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shamlou.agewidget.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_user_registererd.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModelMain by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val view = binding.root
        setContentView(view)

//        setResult(RESULT_CANCELED);
        viewModel.setAppWidgetId(
            intent.extras?.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        )
        button_thats_my_birthday.setOnClickListener {

            calendar_view.animate()
                .translationY(-calendar_view.height.toFloat())
                .alpha(0.0f).duration = 600
            button_thats_my_birthday.animate()
                .alpha(0.0f).duration = 600
            button_wrong_date.animate()
                .alpha(1f).duration = 600
            layout_not_registered_bottom_part.animate()
                .translationY(-calendar_view.height.toFloat())
                .duration = 600

        }
        button_wrong_date.setOnClickListener {

            viewModel.deleteSelectedDate()

            calendar_view.animate()
                .translationY(0f)
                .alpha(1f).duration = 600
            button_thats_my_birthday.animate()
                .alpha(1f).duration = 600
            button_wrong_date.animate()
                .alpha(0.0f).duration = 600
            layout_not_registered_bottom_part.animate()
                .translationY(0f)
                .duration = 600
        }
//
//
//        val resultValue = Intent()
//        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
//        setResult(Activity.RESULT_OK, resultValue)
//        finish()

        setCalnderStuff()
        observeViewModel()
    }

    private fun setCalnderStuff() {

        calendar_view.date = GregorianCalendar(1998, 5, 9).timeInMillis
        calendar_view.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            viewModel.setSelectedDateButWeAreNotSure(year, month, dayOfMonth)
        }
    }

    private fun observeViewModel() {

        viewModel.userBirthCache.observe(this, Observer {

        })
        viewModel.selectedBirthDate.observe(this, Observer {

            showNotRegisteredBottomPart(it != null)
        })
    }

    private fun showNotRegisteredBottomPart(show: Boolean) {

        layout_not_registered_bottom_part.animate()
            .alpha(if (show) 1f else 0.0f)
            .duration = 600
    }
}