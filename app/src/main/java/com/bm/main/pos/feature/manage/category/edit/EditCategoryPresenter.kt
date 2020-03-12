package com.bm.main.pos.feature.manage.category.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.utils.AppConstant

class EditCategoryPresenter(val context: Context, val view: EditCategoryContract.View) : BasePresenter<EditCategoryContract.View>(),
    EditCategoryContract.Presenter, EditCategoryContract.InteractorOutput {


    private var interactor: EditCategoryInteractor = EditCategoryInteractor(this)
    private var categoryRestModel = CategoryRestModel(context)
    private var data:Category? = null


    override fun onViewCreated(intent: Intent) {
        data = intent.getSerializableExtra(AppConstant.DATA) as Category
        if(data == null){
            view.onClose(null,Activity.RESULT_CANCELED)
            return
        }

        data?.let {
            view.setCategoryName(it.nama_kategori)
        }
    }

    override fun onCheck(name: String) {
        if(name.isBlank() || name.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_category))
            return
        }

        interactor.callEditCategoryAPI(context,categoryRestModel,data?.id_kategori!!,name)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessEditCategory(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
    }

    override fun onFailedEditCategory(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

}