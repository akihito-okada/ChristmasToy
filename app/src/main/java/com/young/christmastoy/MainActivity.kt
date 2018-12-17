package com.young.christmastoy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.motion_end.view.*

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

    private fun SpringToyView.initialize(characterImageRes: Int, transitionImage: Runnable) {
        character.setImageResource(characterImageRes)
        listener = object : SpringToyView.InteractionListener {
            override fun onTransitionEnd() {
                transitionImage.run()
            }

            override fun onClickedAnchor() {
                transitionImage.run()
            }
        }
    }
}
