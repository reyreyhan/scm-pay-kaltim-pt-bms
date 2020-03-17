package com.bm.main.pos.feature.newhome.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bm.main.pos.feature.sell.main.SellFragment


const val PENJUALAN_FRAGMENT_INDEX = 0
const val TOKOKU_FRAGMENT_INDEX = 1
const val PPOB_FRAGMENT_INDEX = 2

class NewHomeFragmentStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val childFragments: Array<Fragment>

    init {
        childFragments = arrayOf(
            SellFragment.newInstance()  //0
        )
    }

    override fun getItem(position: Int): Fragment {
        return childFragments[position]
    }

    override fun getCount(): Int {
        return childFragments.size //3 items
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Penjualan"
        }
        return null
    }
}
