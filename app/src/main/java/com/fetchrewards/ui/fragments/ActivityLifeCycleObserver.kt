package com.fetchrewards.ui.fragments

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver

class ActivityLifeCycleObserver(private val func: () -> Unit) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycle.removeObserver(this)
        func()
    }
}
