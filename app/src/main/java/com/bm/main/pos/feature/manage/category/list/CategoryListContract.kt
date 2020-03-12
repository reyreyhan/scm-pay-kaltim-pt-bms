package com.bm.main.pos.feature.manage.category.list

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel


interface CategoryListContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Category>)
        fun showErrorMessage(code: Int, msg: String?)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openAddCategoryPage()
        fun openEditCategoryPage(data:Category)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadCategories()
        fun deleteCategory(id:String)
        fun searchCategory(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetCategoriesAPI(context: Context, restModel:CategoryRestModel)
        fun callDeleteCategoryAPI(context: Context, restModel:CategoryRestModel, id:String)
        fun callSearchCategoryAPI(context: Context, restModel:CategoryRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetCategories(list:List<Category>)
        fun onSuccessDeleteCategory(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}