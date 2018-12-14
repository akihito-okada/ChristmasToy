package com.young.christmastoy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutLeft.transitionToEnd()
        layoutLeft.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                layoutLeft.transitionToEnd()
                layoutBackground.transitionToStart()
                return@setOnTouchListener true
            }
            false
        }

        layoutRight.transitionToEnd()
        layoutRight.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                layoutRight.transitionToEnd()
                layoutBackground.transitionToEnd()
                return@setOnTouchListener true
            }
            false
        }
    }
}
