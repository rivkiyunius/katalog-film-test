package com.rivki.katalogfilm.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
        observeData()
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)
    protected abstract fun observeData()
}