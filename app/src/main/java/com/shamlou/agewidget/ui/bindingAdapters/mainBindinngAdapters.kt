package com.shamlou.agewidget.ui.bindingAdapters

import android.content.res.Resources
import android.view.View
import androidx.databinding.BindingAdapter
import com.shamlou.agewidget.ANIMS_DURATION
import com.shamlou.agewidget.ui.NotRegisteredStates
import kotlinx.android.synthetic.main.layout_user_registererd.*

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:calenderViewAnimations")
fun calenderViewAnimations(calendar_view: View, animState : NotRegisteredStates?) {

    var transitionY = 0f
    var alpha = 0f
    animState?.let { state ->
        when(state){
            NotRegisteredStates.DATE_NOT_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            NotRegisteredStates.DATE_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            NotRegisteredStates.DATE_CONFIRMED -> {
                transitionY = -calendar_view.height.toFloat()
                alpha = 0f
            }
            NotRegisteredStates.NAME_VALIDATED -> {
                transitionY = -calendar_view.height.toFloat()
                alpha = 0f
            }
        }
    }
    calendar_view.animate()
        .translationY(transitionY)
        .alpha(alpha)
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:dateLayoutViewAnimations")
fun dateLayoutViewAnimations(dateLayout: View, animState : NotRegisteredStates?) {

    var transitionY = 0f
    var alpha = 0f
    animState?.let { state ->
        when(state){
            NotRegisteredStates.DATE_NOT_SELECTED -> {
                transitionY = 0f
                alpha = 0f
            }
            NotRegisteredStates.DATE_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            NotRegisteredStates.DATE_CONFIRMED -> {
                transitionY = -300.dp.toFloat()
                alpha = 1f
            }
            NotRegisteredStates.NAME_VALIDATED -> {
                transitionY = -300.dp.toFloat()
                alpha = 1f
            }
        }
    }
    dateLayout.animate()
        .translationY(transitionY)
        .alpha(alpha)
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:confirmDateViewAnimations")
fun confirmDateViewAnimations(confirmDate: View, animState : NotRegisteredStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            NotRegisteredStates.DATE_NOT_SELECTED -> {
                alpha = 1f
            }
            NotRegisteredStates.DATE_SELECTED -> {
                alpha = 1f
            }
            NotRegisteredStates.DATE_CONFIRMED -> {
                alpha = 0f
            }
            NotRegisteredStates.NAME_VALIDATED -> {
                alpha = 0f
            }
        }
    }
    confirmDate.animate()
        .alpha(alpha)
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:wrongDateButtonViewAnimations")
fun wrongDateViewAnimations(wrongDate: View, animState : NotRegisteredStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            NotRegisteredStates.DATE_NOT_SELECTED -> {
                alpha = 0f
            }
            NotRegisteredStates.DATE_SELECTED -> {
                alpha = 0f
            }
            NotRegisteredStates.DATE_CONFIRMED -> {
                alpha = 1f
            }
            NotRegisteredStates.NAME_VALIDATED -> {
                alpha = 1f
            }
        }
    }
    wrongDate.animate()
        .alpha(alpha)
        .duration = ANIMS_DURATION
}