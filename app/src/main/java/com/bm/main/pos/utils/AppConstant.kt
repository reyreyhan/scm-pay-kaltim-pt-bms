package com.bm.main.pos.utils

object AppConstant {

    const val SPLASH_TIMER = 2000L
    const val LIMIT = 10
    const val LIMIT_HUTANG_PIUTANG = 5

    const val TOKEN = "token"
    const val DEVICE_TOKEN = "device_token"
    const val TITLE = "title"
    const val CODE = "code"
    const val DATA = "data"
    const val USER = "user"
    const val POSITION = "position"

    const val REQUEST_CAMERA_PERMISSION = 1001

    object Code {
        val CODE_PROVINCE = 1
        val CODE_CITY = 2
        val CODE_FILTER_DATE_SELL = 100
        val CODE_FILTER_DATE_HISTORY = 101
        val CODE_TRANSACTION_CUSTOMER = 201
        val CODE_TRANSACTION_SUPPLIER = 202
    }

    object URL {
        val HELP =
            "https://lc-fastpay.fastpay.co.id/phplive/phplive.php?d=0&onpage=livechatimagelink&title=Live+Chat+Image+Link"
        val TERM = "https://www.fastpay.co.id/blog/syarat-kondisi"
        val PRIVACY = "https://www.fastpay.co.id/blog/syarat-kondisi"
    }

    object SCAN {
        val TYPE = "type"
        val SELL = 1
        val SELL_SEARCH = 2
        val SELL_ADD = 3
    }

    object FilterDate {
        val DAILY = 1
        val WEEKLY = 2
        val MONTHLY = 3
    }
}