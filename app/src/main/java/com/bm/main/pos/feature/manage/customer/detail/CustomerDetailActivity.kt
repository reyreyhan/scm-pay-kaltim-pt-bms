package com.bm.main.pos.feature.manage.customer.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.manage.customer.credit.CustomerCreditFragment
import com.bm.main.pos.feature.manage.customer.edit.EditCustomerActivity
import com.bm.main.pos.feature.manage.customer.transaction.CustomerTransactionFragment
import com.bm.main.pos.models.TabModel
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.ui.ViewPagerAdapter
import com.bm.main.pos.utils.AppConstant
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_customer_detail.*

class CustomerDetailActivity : BaseActivity<CustomerDetailPresenter, CustomerDetailContract.View>(),
    CustomerDetailContract.View {

    private val fragmentManager = supportFragmentManager
    private val viewPagerAdapter = ViewPagerAdapter(fragmentManager)
    private val CODE_OPEN_EDIT = 102
    private var status = Activity.RESULT_CANCELED


    override fun createPresenter(): CustomerDetailPresenter {
        return CustomerDetailPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_customer_detail
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
        setupTab()
    }

    private fun renderView() {
        setupToolbar()
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {

                var title = ""
                getPresenter()?.getTitleName()?.let {
                    title = it
                }
                ctl.title = title

            } else {
                ctl.title = ""

            }
        })
        ctl.title = ""

        btn_edit.setOnClickListener {
            openEditPage()
        }
    }

    private fun setupTab(){
        val type : ArrayList<TabModel> = ArrayList()
        val tab1 = TabModel()
        tab1.fragment = CustomerTransactionFragment.newInstance(getPresenter()?.getCustomerData())
        tab1.title = "Transaksi"
        val tab2 = TabModel()
        tab2.fragment = CustomerCreditFragment.newInstance(getPresenter()?.getCustomerData())
        tab2.title = "Piutang"
        type.add(tab1)
        type.add(tab2)
        setupViewPager(type)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun hideShowToolbar(isShow: Boolean) {
        appBar.setExpanded(isShow)
    }

    private fun setupViewPager(list: List<TabModel>) {
        for (type in list) {
            if (type != null) {
                viewPagerAdapter.addFragment(type.fragment, type.title)
            }
        }
        viewpager.setAdapter(viewPagerAdapter)
        tabs.tabMode = TabLayout.MODE_FIXED
        tabs.setupWithViewPager(viewpager)

    }

    override fun setCustomer(name: String?, email: String?, phone: String?, address: String?, url:String?)
    {
        name?.let {
            tv_name.text = it
        }

        email?.let {
            tv_email.text = it
        }

        phone?.let {
            tv_phone.text = it
        }

        address?.let {
            tv_address.text = it
        }

        url?.let {
            val into: Any = Glide.with(this)
                .load(it)
                .error(R.drawable.ic_user_pos)
                .transform(CenterCrop(), CircleCrop())
                .into(iv_photo)
            into
        }


    }

    override fun onClose(status: Int) {
        setResult(status,intent)
        finish()
    }

    override fun openEditPage() {
        val intent = Intent(this, EditCustomerActivity::class.java)
        intent.putExtra(AppConstant.DATA,getPresenter()?.getCustomerData())
        startActivityForResult(intent,CODE_OPEN_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == CODE_OPEN_EDIT) {
            status = Activity.RESULT_OK
            val dt = data?.getSerializableExtra(AppConstant.DATA) as Customer
            dt?.let {
                getPresenter()?.setCustomerData(dt)
            }
        }
    }

    override fun onBackPressed() {
        onClose(status)
    }

}
