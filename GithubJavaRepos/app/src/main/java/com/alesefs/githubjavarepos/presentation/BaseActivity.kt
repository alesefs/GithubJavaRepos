package com.alesefs.githubjavarepos.presentation

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView

open class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, toolbarTitle: TextView, titleIdRes: String, showBackButton: Boolean = false) {
        toolbarTitle.text = titleIdRes
        setSupportActionBar(toolbar)
        if (showBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowHomeEnabled(true);
        }
    }
}