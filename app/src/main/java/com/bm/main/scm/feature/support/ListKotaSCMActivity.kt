package com.bm.main.scm.feature.support

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.utils.StringJson
import com.bm.main.scm.R
import com.bm.main.scm.di.Simulator
import com.bm.main.scm.models.support.Kota
import com.bm.main.scm.models.support.SupportRestInterface
import com.bm.main.scm.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scm_list_wilayah.*
import okhttp3.RequestBody
import javax.inject.Inject

@AndroidEntryPoint
class ListKotaSCMActivity : AppCompatActivity(), ListKotaAdapter.OnItemClickListener {
    @Inject
    @Simulator
    lateinit var apiService: SupportRestInterface
    var disposable: Disposable? = null
    lateinit var adapter: ListKotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scm_list_wilayah)
        renderView()
    }

    private fun renderView() {
        initRecyclerView()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Pilih Kota/Kabupaten"
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

    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        shimmer_view_container.visibility = View.VISIBLE
        adapter = ListKotaAdapter(emptyList(), this)
        recyclerViewWilayah.adapter = adapter
        recyclerViewWilayah.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        loadData()
    }

    override fun onItemClick(item: Kota) {
        setResult(RESULT_OK, Intent().putExtra("kabCode", item.city_code).putExtra("kabName", item.city_name))
        Helper.closeKeyboard(this, currentFocus)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED, Intent())
        Helper.closeKeyboard(this, currentFocus)
        finish()
    }

    fun loadData() {
        disposable?.dispose()
        disposable = apiService.getKota(createJsonRequestBody())
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({ result ->
                shimmer_view_container.visibility = View.GONE
                if (result.response_value == null) {
                    layout_data_empty.visibility = View.VISIBLE
                } else {
                    recyclerViewWilayah.visibility = View.VISIBLE
                    adapter.list = result.response_value!!
                    adapter.notifyDataSetChanged()
                }
            }, { error ->
                shimmer_view_container.visibility = View.GONE
                layout_no_connection.visibility = View.VISIBLE
                showToast(error.message!!)
            })
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createJsonRequestBody() =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            StringJson(this).requestKota(intent.getStringExtra("propCode")))
}