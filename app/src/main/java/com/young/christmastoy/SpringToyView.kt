package com.young.christmastoy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.constraintlayout.motion.widget.MotionLayout

private const val JUDGE_CLICK_THRESHOLD = 50
private const val TRANSITION_END = 1F

class SpringToyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : MotionLayout(context, attrs, defStyleAttr) {

    private val touchableArea: View
    private val clickableArea: View

    private var startX: Float? = null
    private var startY: Float? = null

    var listener: InteractionListener? = null

    var motionLayout: MotionLayout = (LayoutInflater.from(context).inflate(R.layout.motion_start, this, false) as MotionLayout).also {
        it.progress = TRANSITION_END
        it.setOnTouchListener(OnTouchListener { _, ev ->
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                }
                MotionEvent.ACTION_UP   -> {
                    val endX = ev.x
                    val endY = ev.y
                    if (isClickedAnchor(startX!!, endX, startY!!, endY)) {
                        listener?.onClickedAnchor()
                        return@OnTouchListener true
                    }
                    it.transitionToEnd()
                    listener?.onTransitionEnd()
                    return@OnTouchListener true
                }
            }
            false
        })
        it.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // nothing to do
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.layout.motion_start) {
                    it.transitionToEnd()
                    listener?.onTransitionEnd()
                }
            }
        })
    }

    init {
        addView(motionLayout)
        touchableArea = motionLayout.findViewById(R.id.character)
        clickableArea = motionLayout.findViewById(R.id.character)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val isInProgress = (motionLayout.progress > 0.0f && motionLayout.progress < 1.0f)
        val isInAnchor = touchEventInsideAnchorView(touchableArea, ev)
        return if (isInProgress || isInAnchor) {
            // 子のmotionLayoutにイベントを流す
            super.onInterceptTouchEvent(ev)
        } else {
            // 子のmotionLayoutにイベントを流さない
            true
        }
    }

    private fun touchEventInsideAnchorView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    private fun isClickedAnchor(startX: Float?, endX: Float, startY: Float?, endY: Float): Boolean {
        startX ?: return false
        startY ?: return false
        val differenceX = Math.abs(startX - endX)
        val differenceY = Math.abs(startY - endY)
        return !/* =5 */(differenceX > JUDGE_CLICK_THRESHOLD || differenceY > JUDGE_CLICK_THRESHOLD)
    }

    interface InteractionListener {
        fun onClickedAnchor()
        fun onTransitionEnd()
    }
}
