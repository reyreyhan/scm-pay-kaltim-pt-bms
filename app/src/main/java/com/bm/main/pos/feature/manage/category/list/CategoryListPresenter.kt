package com.bm.main.pos.feature.manage.category.list

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import kotlin.collections.ArrayList

class CategoryListPresenter(val context: Context, val view: CategoryListContract.View) : BasePresenter<CategoryListContract.View>(),
    CategoryListContract.Presenter,
    CategoryListContract.InteractorOutput {

    private var interactor: CategoryListInteractor = CategoryListInteractor(this)
    private var categoryRestModel = CategoryRestModel(context)

    override fun onViewCreated() {
        loadCategories()
    }

    override fun loadCategories() {
        interactor.callGetCategoriesAPI(context, categoryRestModel)
    }

    override fun deleteCategory(id: String) {
        interactor.callDeleteCategoryAPI(context, categoryRestModel, id)
    }

    override fun searchCategory(search: String) {
        interactor.onRestartDisposable()
        if (search.isEmpty() || search.isBlank()) {
            interactor.callGetCategoriesAPI(context, categoryRestModel)
        } else {
            interactor.callSearchCategoryAPI(context, categoryRestModel, search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetCategories(list: List<Category>) {
        view.setData(list)
    }

    override fun onSuccessDeleteCategory(msg: String?) {
        view.showSuccessMessage(msg)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code, msg)
    }
}