package com.merseyside.time.activity.view

import android.os.Bundle
import com.merseyside.archy.presentation.activity.BaseActivity
import com.merseyside.time.R

class MainActivity: BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main
    override fun getToolbar() = null
    override fun performInjection(bundle: Bundle?, vararg params: Any) {}
    override fun getFragmentContainer() = null
}