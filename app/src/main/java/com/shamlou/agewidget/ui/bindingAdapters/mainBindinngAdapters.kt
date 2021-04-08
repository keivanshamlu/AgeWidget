package com.shamlou.agewidget.ui.bindingAdapters

import android.animation.Animator
import android.content.res.Resources
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.shamlou.agewidget.ANIMS_DURATION
import com.shamlou.agewidget.R
import com.shamlou.agewidget.base.closeSoftKeyboard
import com.shamlou.agewidget.ui.MainPageStates

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
internal val Float.dp: Float get() = (this * Resources.getSystem().displayMetrics.density)

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:calenderViewAnimations")
fun calenderViewAnimations(view: View, animState : MainPageStates?) {

    var transitionY = 0f
    var alpha = 0f
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            MainPageStates.DATE_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            MainPageStates.DATE_CONFIRMED -> {
                transitionY = -view.height.toFloat()
                alpha = 0f
            }
            MainPageStates.NAME_VALIDATED -> {
                transitionY = -view.height.toFloat()
                alpha = 0f
            }
            MainPageStates.REGISTERED -> {
                transitionY = -view.height.toFloat()
                alpha = 0f
            }
        }
    }
    view.animate()
        .translationY(transitionY)
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
@BindingAdapter("app:dateLayoutViewAnimations")
fun dateLayoutViewAnimations(view: View, animState : MainPageStates?) {

    var transitionY = 0f
    var alpha = 0f
    val datePickerHeight = view.resources.getDimension(R.dimen.date_picker_height)
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                transitionY = 0f
                alpha = 0f
            }
            MainPageStates.DATE_SELECTED -> {
                transitionY = 0f
                alpha = 1f
            }
            MainPageStates.DATE_CONFIRMED -> {
                transitionY = -datePickerHeight
                alpha = 1f
            }
            MainPageStates.NAME_VALIDATED -> {
                transitionY = -datePickerHeight
                alpha = 1f
            }
            MainPageStates.REGISTERED -> {
                transitionY = -datePickerHeight
                alpha = 0f
            }
        }
    }
    view.animate()
        .translationY(transitionY)
        .alpha(alpha)
        .setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                if(alpha == 1f)view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:confirmDateViewAnimations")
fun confirmDateViewAnimations(view: View, animState : MainPageStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                alpha = 1f
            }
            MainPageStates.DATE_SELECTED -> {
                alpha = 1f
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

                view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                if(alpha == 1f)view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:wrongDateButtonViewAnimations")
fun wrongDateViewAnimations(view: View, animState : MainPageStates?) {

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
                alpha = 1f
            }
            MainPageStates.NAME_VALIDATED -> {
                alpha = 1f
            }
            MainPageStates.REGISTERED -> {
                alpha = 0f
            }
        }
    }

    Handler().postDelayed({

        view.animate()
            .alpha(alpha)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationEnd(p0: Animator?) {

                    view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {
                    if(alpha == 1f)view.visibility = View.VISIBLE
                }
            })
            .duration = ANIMS_DURATION
    } , ANIMS_DURATION)

}
@BindingAdapter("enterNameViewAnimations")
fun enterNameViewAnimations(view: View, animState : MainPageStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                alpha = 0f
                view.closeSoftKeyboard()
            }
            MainPageStates.DATE_SELECTED -> {
                alpha = 0f
                view.closeSoftKeyboard()
            }
            MainPageStates.DATE_CONFIRMED -> {
                alpha = 1f
            }
            MainPageStates.NAME_VALIDATED -> {
                alpha = 1f
            }
            MainPageStates.REGISTERED -> {
                alpha = 0f
            }
        }
    }
    view.animate()
        .alpha(alpha)
        .setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                if(alpha == 1f)view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}
@BindingAdapter("letsGoViewAnimations")
fun letsGoViewAnimations(view: View, animState : MainPageStates?) {

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
                alpha = 1f
            }
            MainPageStates.REGISTERED -> {
                alpha = 0f
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
@BindingAdapter("welcomeViewAnimations")
fun welcomeViewAnimations(view: View, animState : MainPageStates?) {

    var alpha = 0f
    animState?.let { state ->
        when(state){
            MainPageStates.DATE_NOT_SELECTED -> {
                alpha = 1f
            }
            MainPageStates.DATE_SELECTED -> {
                alpha = 1f
            }
            MainPageStates.DATE_CONFIRMED -> {
                alpha = 1f
            }
            MainPageStates.NAME_VALIDATED -> {
                alpha = 1f
            }
            MainPageStates.REGISTERED -> {
                alpha = 0f
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