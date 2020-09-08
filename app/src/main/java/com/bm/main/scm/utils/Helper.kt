package com.bm.main.scm.utils

//import okhttp3.MediaType.Companion.toMediaTypeOrNull
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bm.main.scm.R
import com.bm.main.scm.SBFApplication
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Helper {

    fun convertToCurrency(amount: Double): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(amount).replace(",", ".")
    }

    fun getIdLocale(context: Context): Locale {
        return Locale(
            context.getString(R.string.id_lang),
            context.getString(R.string.id_country)
        )
    }

    @Throws(ParseException::class)
    fun getDateFormat(
            context: Context,
            date: String,
            formatFrom: String,
            formatTo: String
    ): String {
        val sdfBefore = SimpleDateFormat(formatFrom, getIdLocale(context))
        val dateBefore = sdfBefore.parse(date)

        val sdfAfter = SimpleDateFormat(formatTo, getIdLocale(context))
        return sdfAfter.format(dateBefore)
    }

    fun getInisialName(fullName: String?): String {
        var fullName: String? = fullName ?: return ""
        fullName = fullName!!.trim { it <= ' ' }
        val separateName =
            fullName.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (separateName.isEmpty()) {
            return ""
        }
        return if (separateName.size > 1) {
            separateName[0][0] + "" + separateName[1][0]
        } else {
            separateName[0][0].toString().toUpperCase()
        }
    }

    fun getMaskedPhoneNumber(phoneNumber: String): String {
        var result = ""
        val visibleChar = 4
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length < 5) {
            return result
        }

        val builder = StringBuilder()
        for (i in 0 until phoneNumber.length - visibleChar) {
            builder.append("*")
        }
        result = builder.toString() + phoneNumber.substring(phoneNumber.length - visibleChar)
        return result
    }

    @Throws(ParseException::class)
    fun getDateFormat(context: Context, date: Date, formatTo: String): String {
        val sdfAfter = SimpleDateFormat(formatTo, getIdLocale(context))
        //        SimpleDateFormat sdfAfter = new SimpleDateFormat(formatTo, Locale.getDefault());
        return sdfAfter.format(date)
    }

    fun isWhatsappInstalled(uri: String, context: Context): Boolean {
        val pm = context.packageManager
        var isInstalled = false
        isInstalled = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        return isInstalled
    }

    fun convertToCurrency(value: String, format: String): String {
        var currentValue: Double?
        currentValue = try {
            java.lang.Double.parseDouble(value)
        } catch (nfe: NumberFormatException) {
            0.0
        }

        val formatter = DecimalFormat(format)
        return formatter.format(currentValue).replace(",", ".")
    }

    fun convertToCurrency(value: String): String {
        var currentValue: Double?
        currentValue = try {
            java.lang.Double.parseDouble(value)
        } catch (nfe: NumberFormatException) {
            0.0
        }

        val formatter = DecimalFormat("#,###,###")
        return formatter.format(currentValue).replace(",", ".")
    }

    fun convertToCurrency(value: Int, format: String): String {
        var currentValue: Double?
        try {
            currentValue = value.toDouble()
        } catch (nfe: NumberFormatException) {
            currentValue = 0.0
        }

        val formatter = DecimalFormat(format)
        return formatter.format(currentValue).replace(",", ".")
    }

    fun openAppSettings(activity: Activity) {
        val packageUri = Uri.fromParts("package", activity.applicationContext.packageName, null)
        val applicationDetailsSettingsIntent = Intent()
        applicationDetailsSettingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        applicationDetailsSettingsIntent.data = packageUri
        applicationDetailsSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.applicationContext.startActivity(applicationDetailsSettingsIntent)
    }

    fun sendMessageByWhatsapp(phoneNumber: String, context: Context) {
        val isWhatsappInstalled = isWhatsappInstalledOrNot("com.whatsapp", context)
        if (isWhatsappInstalled) {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        } else {
            sendMessageDefault(phoneNumber, context)
        }
    }

    fun sendMessageDefault(phoneNumber: String, context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null)))
    }

    fun sendMessageByWhatsappWithText(phoneNumber: String, message: String, context: Context) {
        val isWhatsappInstalled = isWhatsappInstalledOrNot("com.whatsapp", context)
        if (isWhatsappInstalled) {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$message"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        } else {
            /*Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?withdrawalId=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(goToMarket);*/
            sendMessageDefault(phoneNumber, context)
        }
    }

    fun sendMail(email:String, pathUri: Uri, context: Context) {
        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto:")
        mIntent.putExtra(Intent.EXTRA_EMAIL, email)
        mIntent.type = "image/*"

        //mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        mIntent.putExtra(Intent.EXTRA_STREAM, pathUri)
        try {
            context.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun phoneCall(phone: String, context: Context) {
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)))
    }

    fun isWhatsappInstalledOrNot(uri: String, context: Context): Boolean {
        val pm = context.packageManager
        var app_installed = false
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            app_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            app_installed = false
        }

        return app_installed
    }

    @JvmStatic
    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    @JvmStatic
    fun getUniqueIMEI(context: Context): String {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_PHONE_STATE
                    ) !== PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                return ""
            }
            val imei = telephonyManager.deviceId
            return if (imei != null && !imei.isEmpty()) {
                imei
            } else {
                android.os.Build.SERIAL
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun clearImages(context: Context) {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return
        clearImageFiles(dir.absolutePath)
    }

    private fun clearImageFiles(filename: String) {
        val file = File(filename)
        if (!file.exists())
            return
        if (!file.isDirectory) {
            file.delete()
            return
        }

        val files = file.list()
        for (item in files!!) {
            clearImageFiles("$filename/$item")
        }
        file.delete()
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager =
            SBFApplication.getInstance()!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //MyApplication.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    fun getMaskedString(value: String?): String {
        if (value.isNullOrEmpty()) {
            return "-"
        }
        var result = ""
        val lastVisibleNum = 3
        if (value.length < 5) {
            return value
        }

        val maskedString = value.substring(2, value.length - lastVisibleNum)
        val builder = StringBuilder()
        for (i in 0 until maskedString.length) {
            builder.append("X")
        }
        result = value.substring(
            0,
            2
        ) + builder.toString() + value.substring(value.length - lastVisibleNum)
        return result
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(phone: String): Boolean {
        if (phone.length < 8) {
            return false
        }
        val prefix = phone.substring(0, 2)
        if ("08" == prefix) {
            return true
        }
        return false
    }

    fun getJsonStringFromAssets(context: Context, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun createPartFromString(text: String): RequestBody {
//        return RequestBody.create("text/plain".toMediaTypeOrNull(), text)
        return RequestBody.create(MediaType.parse("text/plain"), text)

    }

    fun createPartFromFile(path: String?, key: String): MultipartBody.Part? {
        if (path == null) {
            return null
        }
        val file = File(path)
//        val request = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val request = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(key, file.name, request)
    }

    fun shareBitmapToApps(context: Context, pathUri: Uri) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        i.putExtra(Intent.EXTRA_STREAM, pathUri)
        context.startActivity(Intent.createChooser(i, "Bagikan ke ..."))
    }

    fun keyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }


    fun expand(v: View) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.layoutParams.height = if (interpolatedTime == 1f) WindowManager.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean = true
        }.apply {
            duration = ((targetHeight / v.context.resources.displayMetrics.density).toLong())
        }.also {
            v.startAnimation(it)
        }
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight

        object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == -1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = (initialHeight - (initialHeight * interpolatedTime)).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean = true
        }.apply {
            duration = (initialHeight / v.getContext().getResources().getDisplayMetrics().density).toLong()
        }.also {
            v.startAnimation(it)
        }
    }
}