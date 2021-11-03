package com.tentwenty.assignment.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.tentwenty.assignment.R


class SplashActivity : BaseActivity<ViewDataBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_splash)
        gotoMainActivity()
    }


    private fun gotoMainActivity() {

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Main-Activity. */
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, 3000)

    }
}