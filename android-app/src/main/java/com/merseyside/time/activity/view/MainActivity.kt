package com.merseyside.time.activity.view

import android.os.Bundle
import com.merseyside.archy.presentation.activity.BaseActivity
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.time.R
import com.merseyside.time.databinding.ActivityMainBinding

class MainActivity: BaseActivity() {

    override fun performInjection(bundle: Bundle?) {}
    override fun getLayoutId() = R.layout.activity_main
    override fun getToolbar() = null
    override fun getFragmentContainer() = null
}