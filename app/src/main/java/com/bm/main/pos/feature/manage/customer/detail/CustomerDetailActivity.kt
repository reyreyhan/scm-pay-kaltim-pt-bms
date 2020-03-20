package com.bm.main.pos.feature.manage.customer.detail

//import com.bm.main.pos.utils.glide.GlideApp
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.manage.customer.edit.EditCustomerActivity
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.ui.ViewPagerAdapter
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_customer_detail_new.*


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
        return R.layout.activity_customer_detail_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        setupToolbar()
        btn_tambah.setOnClickListener {
            val newintent:Intent = intent
            newintent.putExtra(AppConstant.DATA, getPresenter()?.getCustomerData())
            setResult(RESULT_OK, newintent)
            finish()
        }
//        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
//
//                var title = ""
//                getPresenter()?.getTitleName()?.let {
//                    title = it
//                }
//                ctl.title = title
//
//            } else {
//                ctl.title = ""
//
//            }
//        })
//        ctl.title = ""
    }

//    private fun setupTab(){
//        val type : ArrayList<TabModel> = ArrayList()
//        val tab1 = TabModel()
//        tab1.fragment = CustomerTransactionFragment.newInstance(getPresenter()?.getCustomerData())
//        tab1.title = "Transaksi"
//        val tab2 = TabModel()
//        tab2.fragment = CustomerCreditFragment.newInstance(getPresenter()?.getCustomerData())
//        tab2.title = "Piutang"
//        type.add(tab1)
//        type.add(tab2)
//        setupViewPager(type)
//    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_edit -> {
                openEditPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail Pelanggan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

//    override fun hideShowToolbar(isShow: Boolean) {
//        appBar.setExpanded(isShow)
//    }

//    private fun setupViewPager(list: List<TabModel>) {
//        for (type in list) {
//            if (type != null) {
//                viewPagerAdapter.addFragment(type.fragment, type.title)
//            }
//        }
//        viewpager.setAdapter(viewPagerAdapter)
//        tabs.tabMode = TabLayout.MODE_FIXED
//        tabs.setupWithViewPager(viewpager)
//
//    }

    override fun setCustomer(name: String?, email: String?, phone: String?, address: String?, url:String?)
    {
        name?.let {
            tv_customer_name_detail.text = it
        }
        email?.let {
            tv_customer_email_detail.text = it
        }
        phone?.let {
            tv_customer_no_hp_detail.text = it
        }
        address?.let {
            //tv_address.text = it
        }
        url?.let {
//            val into: Any = Glide.with(this)
//                .load(it)
//                .error(R.drawable.ic_user_pos)
//                .transform(CenterCrop(), CircleCrop())
//                .into(iv_photo)
//            into
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_customer_detail, menu)
        val item = menu!!.getItem(0)
        val s = SpannableString(item.title)
        s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, 0)
        item.title = s
        return true
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

    override fun showButtonTransaction() {
        btn_tambah.visibility = View.VISIBLE
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
