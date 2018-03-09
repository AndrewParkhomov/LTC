package parkhomov.andrew.lensthicknesscalculator.ui.settings

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
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
import parkhomov.andrew.lensthicknesscalculator.ui.SingleActivity
import parkhomov.andrew.lensthicknesscalculator.utils.const
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import java.util.*

class Settings : Fragment() {


    private var target: SingleActivity? = null

    fun setTarget(mainActivity: SingleActivity) {
        this.target = mainActivity
    }

    companion object {

        fun getInstance(mainActivity: SingleActivity): Settings {
            val bundle = Bundle()
            val settings = Settings()
            settings.arguments = bundle
            settings.setTarget(mainActivity)
            return settings
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)


        return view
    }

    private fun setUpDateInAdapter() {
        val lm = LinearLayoutManager(activity)
        settingsRcycV.layoutManager = lm
        // make divider
        val dividerItemDecoration = DividerItemDecoration(settingsRcycV.context,
                lm.orientation)
        settingsRcycV.addItemDecoration(dividerItemDecoration)

        val headers = ArrayList<String>(3)
        headers.add(0, getString(R.string.header_language))
        headers.add(1, getString(R.string.header_rate_this_app))
        headers.add(2, getString(R.string.header_about))

        val adapter = SimpleAdapter(activity!!, headers, target)
        settingsRcycV.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        header.text = Utils.spacing(getString(R.string.header_settings), const.spacing8)

        setUpDateInAdapter()
        turnBackImgB.setOnClickListener { fragmentManager!!.popBackStack() }
    }


    class SimpleAdapter(private val context: FragmentActivity, val headers: (MutableList<String>?), private val mainActivity: SingleActivity?) :
            RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpData(context, headers!![position], mainActivity!!)
        }

        override fun getItemCount() = headers!!.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun setUpData(context: FragmentActivity,
                          header: String, mainActivity: SingleActivity) {

                itemView.itemNameTxtV.text = header

                itemView.setOnClickListener {
                    when (position) {
                        0 -> {
                            val language = Language.getInstance(mainActivity)
                            language.show(context.supportFragmentManager, const.LANGUAGE)
                        }
                        1 -> rateThisAppClicked(context)
                        2 -> {
                            val aboutDialogActivity = AboutDialogActivity.instance
                            aboutDialogActivity.show(context.supportFragmentManager, const.ABOUT)
                        }
                    }

                }
            }

            private fun rateThisAppClicked(context: FragmentActivity) {
                val dialog = AlertDialog.Builder(context)
                        .setTitle(R.string.rate_app_header)
                        .setMessage(R.string.rate_app_body)

                        .setNegativeButton(R.string.rate_app_dialog_no, null)
                        .setPositiveButton(R.string.rate_app_dialog_ok) { arg0, arg1 ->
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(const.GOOGLE_PLAY_LINK)))
                        }.create()
                dialog.show()
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.TRANSPARENT)
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setPadding(30, 0, 10, 0)
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.blue_700))
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.blue_700))
            }
        }
    }
}