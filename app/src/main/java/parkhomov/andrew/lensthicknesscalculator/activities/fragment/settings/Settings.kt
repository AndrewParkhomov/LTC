package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.glossary_list_item.view.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2
import parkhomov.andrew.lensthicknesscalculator.activities.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils

class Settings : Parent() {

    @BindView(R2.id.settingsRcycV)
    lateinit var recyclerView: RecyclerView
    @BindView(R2.id.header)
    lateinit var header: TextView

    private var target: MainActivity? = null

    fun setTarget(mainActivity: MainActivity) {
        this.target = mainActivity
    }

    companion object {

        fun getInstance(mainActivity: MainActivity): Settings {
            val bundle = Bundle()
            val settings = Settings()
            settings.arguments = bundle
            settings.setTarget(mainActivity)
            return settings
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.settings_fragment, container, false)
        ButterKnife.bind(this, view)

        header.text = Utils.spacing(getString(R.string.header_settings), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8)

        setUpDateInAdapter()
        return view
    }

    private fun setUpDateInAdapter() {
        val lm = LinearLayoutManager(activity)
        recyclerView.layoutManager = lm
        // make divider
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                lm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val headers = ArrayList<String>(3)
        headers.add(0, getString(R.string.header_language))
        headers.add(1, getString(R.string.header_rate_this_app))
        headers.add(2, getString(R.string.header_about))

        val adapter = SimpleAdapter(activity, headers, target)
        recyclerView.adapter = adapter
    }

    @OnClick(R.id.turnBackImgB)
    fun onBackPressed() {
        fragmentManager.popBackStack()
    }

    class SimpleAdapter(private val context: FragmentActivity, val headers: (MutableList<String>?), private val mainActivity: MainActivity?) :
            RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
            return SimpleAdapter.ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpData(context, headers!![position], mainActivity!!)
        }

        override fun getItemCount() = headers!!.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun setUpData(context: FragmentActivity,
                          header: String, mainActivity: MainActivity){

                itemView.itemNameTxtV.text = header

                itemView.setOnClickListener {
                    when (position) {
                        0 -> {
                            val language = Language.getInstance(mainActivity)
                            language.show(context.supportFragmentManager, CONSTANT.LANGUAGE)
                        }
                        1 -> rateThisAppClicked(context)
                        2 -> {
                            val aboutDialogActivity = AboutDialogActivity.instance
                            aboutDialogActivity.show(context.supportFragmentManager, CONSTANT.ABOUT)
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
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(CONSTANT.GOOGLE_PLAY_LINK))) }.create()
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
