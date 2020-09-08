package com.bm.main.scm.feature.profilescm

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_profile_merchant_scm.*

class ProfileSCMActivity : BaseActivity<ProfileSCMPresenter, ProfileSCMContract.View>(), ProfileSCMContract.View {
    override fun createPresenter(): ProfileSCMPresenter {
        return ProfileSCMPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_profile_merchant_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Profile"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backArrow.setTint(resources.getColor(android.R.color.black))
            }
            setHomeAsUpIndicator(backArrow)
        }
    }

    private fun renderView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
//        val list = ArrayList<String>()
//        list.apply {
//            add("378291738973823")
//            add("08123142534")
//            add("robbydz@gmail.com")
//            add("TOKO")
//            add("Usaha Mikro")
//            add("09138091802391")
//            add("Jl. Tropodo Asri No. 66")
//            add("Jawa Timur")
//            add("Kab. Sidoarjo")
//            add("Waru")
//            add("Tropodo")
//        }
        val adapter = ProfileSCMAdapter(emptyList())
        rv_merchant_data.adapter = adapter
        rv_merchant_data.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun setProfileData(list: List<String>) {
        val adapter = rv_merchant_data.adapter as ProfileSCMAdapter
        adapter.updateList(list)
    }

    override fun setProfileHeader(name: String, id: String, url: String) {
        tv_merchant_name.text = name
        tv_merchant_id.text = id
        Glide.with(this).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
            .transform(CenterCrop(), CircleCrop()).into(iv_profile_pict)
    }
}
