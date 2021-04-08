package com.shamlou.agewidget.ui

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer.OnChronometerTickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shamlou.agewidget.R
import com.shamlou.agewidget.base.closeSoftKeyboard
import com.shamlou.agewidget.base.showSnackBarTop
import com.shamlou.agewidget.base.updateWidgets
import com.shamlou.agewidget.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_user_not_registererd.*
import kotlinx.android.synthetic.main.layout_user_registered.*
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

        viewModel.setAppWidgetId(
            intent.extras?.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        )

        setCalnderStuff()
        observeViewModel()
    }

    private fun setCalnderStuff() {

        date_picker.updateDate(1998, 5, 9)
        date_picker.setOnDateChangedListener { datePicker, year, month, dayOfMonth ->

            viewModel.setSelectedDateButWeAreNotSure(year, month + 1, dayOfMonth)
        }
    }

    private fun observeViewModel() {

        viewModel.userBirthCache.observe(this, Observer {

        })
        viewModel.selectedBirthDate.observe(this, Observer {

        })
        viewModel.mainPageStates.observe(this, Observer {})
        viewModel.nameIsValid.observe(this, Observer {})
        viewModel.appWidgetId.observe(this, Observer {})
        viewModel.updateWidget.observe(this, Observer {

            it.getContentIfNotHandled()?.let { updateWidgets() }
        })
        viewModel.resultOk.observe(this, Observer {

            it.getContentIfNotHandled()?.let { appWidgetId->

                val resultValue = Intent()

                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                setResult(Activity.RESULT_OK, resultValue)
                this@MainActivity.showSnackBarTop(getString(R.string.widget_created) , 2000 , binding.root)
            }
        })
        viewModel.registeredUser.observe(this, Observer {

            text_view_name_of_user.text = "Dear ${it?.firstName}"
        })
        viewModel.calculatedAge.observe(this, Observer {

            it?.let { setChronometer(it.second) }
        })
        viewModel.checkState.observe(this, Observer {


        })
        viewModel.closeKeyBoard.observe(this, Observer {

            if (it.getContentIfNotHandled() == true) binding.root.closeSoftKeyboard()
        })
        viewModel.showSnackbarHelp.observe(this, Observer {

            if (it.getContentIfNotHandled() == true) showSnackBarTop(getString(R.string.add_widget_help) , 8000 , binding.root , getString(R.string.ok_fine))
        })
    }

    private fun setChronometer(base : Long) {

        chronometer_age.onChronometerTickListener =
            OnChronometerTickListener { cArg ->
                val time = SystemClock.elapsedRealtime() - cArg.base
                val h = (time / 3600000).toInt()
                val m = (time - h * 3600000).toInt() / 60000
                val s = (time - h * 3600000 - m * 60000).toInt() / 1000
                if(h == 0 && m == 0 && (s == 0 || s == 1))viewModel.updateDate()
                text_view_age_hour.text = if (h < 10) "0$h" else h.toString() + ""
                text_view_age_minutes.text = if (m < 10) "0$m" else m.toString() + ""
                text_view_age_seconds.text = if (s < 10) "0$s" else s.toString() + ""
            }
        chronometer_age.base = SystemClock.elapsedRealtime() - base
        chronometer_age.start()
    }

}