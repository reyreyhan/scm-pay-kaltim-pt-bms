package com.bm.main.scm.feature.manage.product

/*
class ProductViewModel @Inject constructor(
        val apiService: ApiService,
        @Named("token") val token: String,
        val moshi: Moshi
) :
    ViewModel() {

    private var searchQuery = ""
    private var limit = 50
    var datasource: ProductDataSource? = null
    val disposables by lazy { CompositeDisposable() }
    val productDataSourceFactory by lazy {
        object : DataSource.Factory<Int, Product>() {
            override fun create(): DataSource<Int, Product> = ProductDataSource(
                apiService,
                token,
                searchQuery,
                limit
            ).also { datasource = it }
        }
    }
    val productPagedConfig by lazy {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(limit * 2)
            .setPageSize(limit)
            .setPrefetchDistance(5)
            .build()
    }

    val initState by lazy { MutableLiveData<NetworkState>().apply { postValue(NetworkState()) } }
    val loadState by lazy { MutableLiveData<NetworkState>().apply { postValue(NetworkState()) } }
    val productList by lazy {
        LivePagedListBuilder<Int, Product>(
            productDataSourceFactory,
            productPagedConfig
        ).setBoundaryCallback(object : PagedList.BoundaryCallback<Product>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                initState.postValue(NetworkState.FAILED)
            }

            override fun onItemAtEndLoaded(itemAtEnd: Product) {
                super.onItemAtEndLoaded(itemAtEnd)
                loadState.postValue(NetworkState.SUCCESS)
            }
        }).build()
    }

    @SuppressLint("CheckResult")
    fun searchProduct(query: String = "", limit: Int = 50) {
        disposables.add(
            Completable.fromAction {
                searchQuery = query
                this.limit = limit
                datasource?.invalidate()
            }
                .delay(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }

    @SuppressLint("CheckResult")
    fun searchBarcode(code: String = "", then: (result: Product?) -> Unit) {
        disposables.add(
            apiService.searchByBarcode(token, code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    then(res.first())
                }, { e ->
                    then(null)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        disposables.clear()
    }
}*/
