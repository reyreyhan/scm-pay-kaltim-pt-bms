package com.bm.main.scm.feature.manage.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.R
import com.bm.main.scm.feature.manage.product.list.ProductListActivity
import com.bm.main.scm.feature.manage.category.list.CategoryListActivity
import com.bm.main.scm.feature.manage.discount.list.DiscountListActivity
import com.bm.main.scm.feature.manage.customer.list.CustomerListActivity
import com.bm.main.scm.feature.manage.supplier.list.SupplierListActivity
import com.bm.main.scm.feature.manage.hutangpiutang.main.MainActivity
import com.bm.main.scm.feature.kulakan.main.KulakanActivity


import kotlinx.android.synthetic.main.fragment_manage.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class ManageFragment : BaseFragment<ManagePresenter, ManageContract.View>(),
    ManageContract.View {

    private lateinit var _view: View

    companion object {
        @JvmStatic
        fun newInstance() = ManageFragment()
    }

    override fun createPresenter(): ManagePresenter {
        return ManagePresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        _view.btn_product.setOnClickListener {
            openProductPage()
        }

        _view.btn_category.setOnClickListener {
            openCategoryPage()
        }

        _view.btn_customer.setOnClickListener {
            openCustomerPage()
        }

        _view.btn_supplier.setOnClickListener {
            openSupplierPage()
        }

        _view.btn_credit.setOnClickListener {
            openCreditPage()
        }

        _view.btn_kulakan.setOnClickListener {
            openKulakanPage()
        }
    }

    override fun openProductPage() {
        startActivity(Intent(activity, ProductListActivity::class.java))

    }

    override fun openCategoryPage() {
        startActivity(Intent(activity, CategoryListActivity::class.java))
    }

    override fun openDiscountPage() {
        startActivity(Intent(activity, DiscountListActivity::class.java))
    }

    override fun openCustomerPage() {
        startActivity(Intent(activity, CustomerListActivity::class.java))
    }

    override fun openSupplierPage() {
        startActivity(Intent(activity, SupplierListActivity::class.java))
    }

    override fun openCreditPage() {
        startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun openKulakanPage() {
        startActivity(Intent(activity, KulakanActivity::class.java))
    }
}
