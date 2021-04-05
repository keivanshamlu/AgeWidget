package com.shamlou.agewidget.ui.bindingAdapters

import android.animation.Animator
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shamlou.agewidget.ANIMS_DURATION
import com.shamlou.agewidget.ui.MainPageStates
import java.time.Period

@BindingAdapter("app:setAgeYear")
fun setAgeYear(view: TextView, age: Pair<Period, Long>?) {

    age?.first?.years?.let { view.text = it.toString() }
}
@BindingAdapter("app:setAgeMonth")
fun setAgeMonth(view: TextView, age: Pair<Period, Long>?) {

    age?.first?.months?.let { view.text = it.toString() }
}
@BindingAdapter("app:setAgeDay")
fun setAgeDay(view: TextView, age: Pair<Period, Long>?) {

    age?.first?.days?.let { view.text = it.toString() }
}


@BindingAdapter("app:registeredViewAnimations")
fun registeredViewAnimations(view: View, animState : MainPageStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                alpha = 0f
            }
            MainPageStates.DATE_SELECTED -> {
                alpha = 0f
            }
            MainPageStates.DATE_CONFIRMED -> {
                alpha = 0f
            }
            MainPageStates.NAME_VALIDATED -> {
                alpha = 0f
            }
            MainPageStates.REGISTERED -> {
                alpha = 1f
            }
        }
    }
    view.animate()
        .alpha(alpha)
        .setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                view.visibility = if(alpha == 0f) View.INVISIBLE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                if(alpha == 1f)view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}