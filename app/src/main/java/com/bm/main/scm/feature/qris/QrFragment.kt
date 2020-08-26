package com.bm.main.scm.feature.qris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bm.main.scm.R
import com.bm.main.scm.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_qris.*

class QrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_qris, container, false)

    private val myQr by lazy { MyQrFragment() }
    private val trxQr by lazy { QrTransactionFragment() }
    private val saldoQr by lazy { QrSaldoFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = ViewPagerAdapter(childFragmentManager).apply {
            addFragment(myQr, "QR Ku")
            addFragment(trxQr, "Transaksi")
            addFragment(saldoQr, "Mutasi")
        }

        tabs.setupWithViewPager(viewpager)
    }
}