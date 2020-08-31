package com.bm.main.scm.feature.notification

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity

class NotificationSCMActivity : BaseActivity<NotificationSCMPresenter, NotificationSCMContract.View>(), NotificationSCMContract.View {
    override fun createPresenter(): NotificationSCMPresenter {
        return NotificationSCMPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_notification_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
//        getPresenter()?.onViewCreated()
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
            title = "Notifikasi"
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
        val list = mutableListOf<NotificationSCM>()
        list.add(NotificationSCM(1, "Notification Test 1", "1 September 2020"))
        list.add(NotificationSCM(2, "Notification Test 2", "1 September 2020"))
        list.add(NotificationSCM(3, "Notification Test 3", "2 September 2020"))
        val adapter = NotificationSCMAdapter(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}

data class NotificationSCM(
    var id:Int? = null,
    var content:String? = null,
    var date:String? = null
)