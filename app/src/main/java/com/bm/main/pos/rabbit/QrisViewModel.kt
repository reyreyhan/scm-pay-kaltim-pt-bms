package com.bm.main.pos.rabbit

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import javax.inject.Inject

@Keep
class QrisViewModel @Inject constructor(val service: QrisService): ViewModel() {
}