package com.young.christmastoy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.motion_end.view.*

private const val TRANSITION_END = 1F

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutLeft.initialize(R.drawable.image_tonakai, Runnable {
            layoutBackground.transitionToStart()
        })
        layoutRight.initialize(R.drawable.image_inoshishi, Runnable {
            layoutBackground.transitionToEnd()
        })
    }

    private fun SpringToyView.initialize(characterImageRes: Int, onTransitionToEnd: Runnable) {
        character.setImageResource(characterImageRes)
        listener = object : SpringToyView.InteractionListener {
            override fun onClickedAnchor() {
                onTransitionToEnd.run()
            }
        }
        motionLayout.also {
            it.progress = TRANSITION_END
            it.setOnTouchListener { _, event ->
                if (event.action == android.view.MotionEvent.ACTION_UP) {
                    it.transitionToEnd()
                    onTransitionToEnd.run()
                    return@setOnTouchListener true
                }
                false
            }
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
                        onTransitionToEnd.run()
                    }
                }
            })
        }
    }
}
