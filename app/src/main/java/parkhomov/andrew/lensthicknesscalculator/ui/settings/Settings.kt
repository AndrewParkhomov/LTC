package parkhomov.andrew.lensthicknesscalculator.ui.settings

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary_list_item.view.*
import kotlinx.android.synthetic.main.settings_fragment.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.spacing8
import java.util.*

class Settings : BaseFragment() {

    companion object {
        val TAG: String = Settings::class.java.simpleName
        val instance = Settings()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        return view
    }

    private fun setUpDateInAdapter() {
        val lm = LinearLayoutManager(baseActivity)
        settingsRcycV.layoutManager = lm
        // make divider
        val dividerItemDecoration = DividerItemDecoration(settingsRcycV.context,
                lm.orientation)
        settingsRcycV.addItemDecoration(dividerItemDecoration)

        val headers = ArrayList<String>(3)
        headers.add(0, getString(R.string.header_language))
        headers.add(1, getString(R.string.header_rate_this_app))
        headers.add(2, getString(R.string.header_about))

        settingsRcycV.adapter = SimpleAdapter(baseActivity!!, headers)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        header.text = Utils.spacing(getString(R.string.header_settings), spacing8)

        setUpDateInAdapter()
        turnBackImgB.setOnClickListener { fragmentManager!!.popBackStack() }
    }

    class SimpleAdapter(private val context: BaseActivity, private val headers: (MutableList<String>?)) :
            RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpData(context, position, headers!!)
        }

        override fun getItemCount() = headers!!.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun setUpData(baseActivity: BaseActivity, position: Int, headers: MutableList<String>) {

                itemView.itemNameTxtV.text = headers[position]

                itemView.setOnClickListener {
                    when (position) {
                        0 -> {
                            Language.instance.showDialog(baseActivity.supportFragmentManager, Language.TAG)
                        }
                        1 -> rateThisAppClicked(baseActivity)
                        2 -> {
                            AboutDialogActivity.instance.showDialog(baseActivity.supportFragmentManager, AboutDialogActivity.TAG)
                        }
                    }
                }
            }

            private fun rateThisAppClicked(baseActivity: BaseActivity) {
                val dialog = AlertDialog.Builder(baseActivity)
                        .setTitle(R.string.rate_app_header)
                        .setMessage(R.string.rate_app_body)
                        .setNegativeButton(R.string.rate_app_dialog_no, null)
                        .setPositiveButton(R.string.rate_app_dialog_ok) { _, _ ->
                            try {
                                baseActivity.startActivity(Intent(Intent.ACTION_VIEW,
                                        Uri.parse(baseActivity.getString(R.string.app_google_play_link) + baseActivity.packageName)))
                            } catch (e: ActivityNotFoundException) {
                                baseActivity.startActivity(Intent(Intent.ACTION_VIEW,
                                        Uri.parse(baseActivity.resources.getString(R.string.app_google_play_link) + baseActivity.packageName)))
                            }
                        }.create()
                dialog.show()
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.TRANSPARENT)
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setPadding(30, 0, 10, 0)
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(baseActivity, R.color.blue_700))
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(baseActivity, R.color.blue_700))
            }
        }
    }
}
