package com.bm.main.pos.feature.manage.discount.list

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.discount.Discount
import kotlin.collections.ArrayList

class DiscountListPresenter(val context: Context, val view: DiscountListContract.View) : BasePresenter<DiscountListContract.View>(),
    DiscountListContract.Presenter, DiscountListContract.InteractorOutput {

    private var interactor: DiscountListInteractor =
        DiscountListInteractor(this)


    override fun onViewCreated() {
        dummy()
    }

    fun dummy(){
        var news1 = Discount()
        news1.id = "1"
        news1.info = "Potongan 5%"
        news1.name = "promo5"






        val list = ArrayList<Discount>()
        list.add(news1)

        view.setData(list)
    }
}