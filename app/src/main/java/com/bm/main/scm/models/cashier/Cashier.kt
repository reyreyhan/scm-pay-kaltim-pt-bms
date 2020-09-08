package com.bm.main.scm.models.cashier

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class AddCashierResponse : Serializable {
    var status: String? = null
    var errCode: String? = null
    var msg: String? = null
    var insert_kasir: Int? = null
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class LoginCashierResponse : Serializable {
    var status:String? = null
    var errCode:String? = null
    var msg:String? = null
    var data: LoginCashier? = null
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class LoginCashier:Serializable {
    var id_session:String? = null
    var no_hp:String? = null
    var nama:String? = null
    var no_telp_owner: String? = null
    var terminal_id: String? = null
    var fastpay_id: String? = null
    var fastpay_pin: String? = null
}


@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class CashierResponse : Serializable {
    var status:Boolean? = null
    var errCode:String? = null
    var msg:String? = null
    var data: List<Cashier>? = null
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Cashier : Serializable {
    var id_kasir:String? = null
    var no_telp_owner:String? = null
    var no_hp:String? = null
    var nama:String? = null
    var password:String? = null
    var terminal_id:String? = null
    var tanggal:String? = null
    var id_session:String? = null
    var is_deleted:String? = null
    var is_block:String? = null
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class CashierSuccessManage: Serializable {
    var id_kasir:String? = null
}