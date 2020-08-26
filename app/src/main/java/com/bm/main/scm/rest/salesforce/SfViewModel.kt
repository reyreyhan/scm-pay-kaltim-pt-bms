package com.bm.main.scm.rest.salesforce

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SfViewModel @Inject constructor(val apiService: ApiSalesForce) : ViewModel()