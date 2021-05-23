package com.example.calendar2.adpater

import android.util.Log
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CalenderViewPagerAdapter(
    val fragmentList: MutableList<Fragment>,
    val fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addtoRightList(fragment: Fragment, index: Int) {
        var result = fragmentList.find {
            it == fragment
        }
        if (result == null) {
            fragmentList.set(index, fragment)
        }
        notifyItemChanged(index)
    }

    fun addToLeftList(fragment: Fragment, index: Int) {
        var result = fragmentList.find {
            it == fragment
        }
        if (result == null) {
            fragmentList.set(index, fragment)

        }
        notifyItemChanged(index)
    }

    fun reloadFragment(index: Int) {
        var fragmentManager = fm.beginTransaction()
        fragmentManager.remove(fragmentList[index])
        fragmentManager.commitNow()
    }
}