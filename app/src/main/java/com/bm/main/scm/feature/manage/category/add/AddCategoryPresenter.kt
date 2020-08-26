package com.bm.main.scm.feature.manage.category.add

import android.app.Activity
import android.content.Context
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.category.CategoryRestModel

class AddCategoryPresenter(val context: Context, val view: AddCategoryContract.View) : BasePresenter<AddCategoryContract.View>(),
    AddCategoryContract.Presenter, AddCategoryContract.InteractorOutput {


    private var interactor: AddCategoryInteractor = AddCategoryInteractor(this)
    private var categoryRestModel = CategoryRestModel(context)


    override fun onViewCreated() {

    }

    override fun onCheck(name: String) {
        if(name.isBlank() || name.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_category))
            return
        }

        interactor.callAddCategoryAPI(context,categoryRestModel,name)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessAddCategory(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
    }

    override fun onFailedAddCategory(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

}