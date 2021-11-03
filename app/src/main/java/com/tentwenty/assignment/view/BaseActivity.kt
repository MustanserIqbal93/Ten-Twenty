package com.tentwenty.assignment.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tentwenty.assignment.MainApplication


abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity() {


    var binding: Binding? = null

    var appController: MainApplication? = null
    var mCurrentFragment: Fragment? = null

    protected fun bindView(layoutId: Int) {
        binding = DataBindingUtil.setContentView<Binding>(this, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appController = application as MainApplication
    }

    fun gotoActivityfinish(mIntent: Intent) {
        startActivity(mIntent)
        finish()
    }

    fun gotoActivity(mIntent: Intent) {
        startActivity(mIntent)
    }

    override fun onResume() {
        super.onResume()
    }

}