package com.bm.main.pos.feature.scan

import android.content.Context
import android.content.Intent
import android.view.View
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.utils.AppConstant

class ScanCodePresenter(val context: Context, val view: ScanCodeContract.View) : BasePresenter<ScanCodeContract.View>(),
    ScanCodeContract.Presenter, ScanCodeContract.InteractorOutput {

    private val interactor = ScanCodeInteractor(this)

    override fun onViewCreated(intent: Intent) {
        val type = intent.getIntExtra(AppConstant.SCAN.TYPE,-1)
        if(type == AppConstant.SCAN.SELL){
            view.hideShowSearchHeader(View.VISIBLE)
        }
        else{
            view.hideShowSearchHeader(View.GONE)
        }
    }

    override fun onDestroy() {
        interactor.destroy()
    }
}