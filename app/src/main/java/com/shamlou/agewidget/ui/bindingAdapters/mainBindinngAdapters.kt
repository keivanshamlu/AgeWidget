package com.shamlou.agewidget.ui.bindingAdapters

import android.animation.Animator
import android.content.res.Resources
import android.view.View
import androidx.databinding.BindingAdapter
import com.shamlou.agewidget.ANIMS_DURATION
import com.shamlou.agewidget.ui.NotRegisteredStates

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:calenderViewAnimations")
fun calenderViewAnimations(view: View, animState : NotRegisteredStates?) {

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
                transitionY = -view.height.toFloat()
                alpha = 0f
            }
            NotRegisteredStates.NAME_VALIDATED -> {
                transitionY = -view.height.toFloat()
                alpha = 0f
            }
        }
    }
    view.animate()
        .translationY(transitionY)
        .alpha(alpha)
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:dateLayoutViewAnimations")
fun dateLayoutViewAnimations(view: View, animState : NotRegisteredStates?) {

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
                view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:confirmDateViewAnimations")
fun confirmDateViewAnimations(view: View, animState : NotRegisteredStates?) {

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
    view.animate()
        .alpha(alpha)
        .setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}
@BindingAdapter("app:wrongDateButtonViewAnimations")
fun wrongDateViewAnimations(view: View, animState : NotRegisteredStates?) {

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
    view.animate()
        .alpha(alpha)
        .setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                view.visibility = if(alpha == 0f) View.GONE else View.VISIBLE
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                view.visibility = View.VISIBLE
            }
        })
        .duration = ANIMS_DURATION
}