package com.bm.main.scm.feature.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bm.main.scm.feature.home.tokoku.TokokuFragment
import com.bm.main.scm.feature.sell.main.SellFragment


const val PENJUALAN_FRAGMENT_INDEX = 0
const val TOKOKU_FRAGMENT_INDEX = 1
const val PPOB_FRAGMENT_INDEX = 2

class NewHomeFragmentStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val childFragments: Array<Fragment>

    init {
        childFragments = arrayOf(
            SellFragment.newInstance(),
            TokokuFragment.newInstance(),
            Fragment()//0
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
            1 -> return "Toko"
            2 -> return "PPOB"
        }
        return null
    }
}
