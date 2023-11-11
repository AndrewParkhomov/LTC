package parkhomov.andrew.lensthicknesscalculator.extencions

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.fragment.app.Fragment

fun Context.dip(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
fun Fragment.dip(dp: Int): Int = requireContext().dip(dp)

fun Context.openWebsite(site: String) {
    try {
        var url = site
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    } catch (ignore: Exception) {

    }
}