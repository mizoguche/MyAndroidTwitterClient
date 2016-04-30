package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import com.gordonwong.materialsheetfab.AnimatedFab
import com.gordonwong.materialsheetfab.R


const val FAB_ANIM_DURATION = 200L;

class AnimatedFloatingActionButton(context: Context, attrbuteSet: AttributeSet) : FloatingActionButton(context, attrbuteSet), AnimatedFab {
    override fun show() {
        show(0f, 0f)
    }

    override fun show(translationX: Float, translationY: Float) {
        // Set FAB's translation
        setTranslation(translationX, translationY)

        // Only use scale animation if FAB is hidden
        if (visibility != View.VISIBLE) {
            // Pivots indicate where the animation begins from
            pivotX += translationX
            pivotY += translationY

            // If pivots are 0, that means the FAB hasn't been drawn yet so just use the
            // center of the FAB
            val anim =  if (pivotX == 0f || pivotY == 0f) {
                ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            } else {
                ScaleAnimation(0f, 1f, 0f, 1f, pivotX, pivotY)
            }

            // Animate FAB expanding
            anim.duration = FAB_ANIM_DURATION
            anim.interpolator = getInterpolator()
            startAnimation(anim)
        }
        visibility = View.VISIBLE
    }

    override fun hide() {
        // Only use scale animation if FAB is visible
        if (visibility == View.VISIBLE) {
            // Pivots indicate where the animation begins from
            pivotX += translationX
            pivotY += translationY

            // Animate FAB shrinking
            val anim = ScaleAnimation(1f, 0f, 1f, 0f, pivotX, pivotY)
            anim.setDuration(FAB_ANIM_DURATION)
            anim.interpolator = getInterpolator()
            startAnimation(anim)
        }
        visibility = View.INVISIBLE
    }

    private fun setTranslation(translationX: Float, translationY: Float) {
        animate().setInterpolator(getInterpolator()).setDuration(FAB_ANIM_DURATION)
                .translationX(translationX).translationY(translationY)
    }

    private fun getInterpolator(): Interpolator {
        return AnimationUtils.loadInterpolator(context, R.interpolator.msf_interpolator)
    }
}

