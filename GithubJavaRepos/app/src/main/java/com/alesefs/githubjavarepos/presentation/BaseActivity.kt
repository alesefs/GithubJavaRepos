package com.alesefs.githubjavarepos.presentation

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView

open class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, toolbarTitle: TextView, titleIdRes: String, showBackButton: Boolean = false) {
        toolbarTitle.text = titleIdRes
        toolbarTitle.contentDescription = titleIdRes
        toolbar?.apply {
            setSupportActionBar(toolbar)
            if (showBackButton) {
                navigationContentDescription = "botão, voltar"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowHomeEnabled(true);
            }
        }
    }
}