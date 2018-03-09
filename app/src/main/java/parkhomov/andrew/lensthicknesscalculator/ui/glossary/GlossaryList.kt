package parkhomov.andrew.lensthicknesscalculator.ui.glossary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary_list.*
import kotlinx.android.synthetic.main.glossary_list_item.view.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.const
import parkhomov.andrew.lensthicknesscalculator.utils.Utils

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class GlossaryList : Fragment() {


    private var headers: MutableList<String>? = null
    private var description: MutableList<String>? = null
    private var images: MutableList<Int>? = null

    fun setHeaders(headers: MutableList<String>) {
        this.headers = headers
    }

    fun setDescription(description: MutableList<String>) {
        this.description = description
    }

    fun setImages(images: MutableList<Int>) {
        this.images = images
    }

    companion object {

        fun getInstance(headers: MutableList<String>, description: MutableList<String>, images: MutableList<Int>): GlossaryList {
            val bundle = Bundle()
            val glossaryList = GlossaryList()
            glossaryList.arguments = bundle
            glossaryList.setHeaders(headers)
            glossaryList.setDescription(description)
            glossaryList.setImages(images)
            return glossaryList
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.glossary_list, container, false)


        return view
    }


    private fun setUpDateInAdapter() {
        val lm = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // make divider
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                lm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = SimpleAdapter(activity!!, headers, description, images)
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        header.text = Utils.spacing(getString(R.string.header_glossary_list), const.spacing8)
        setUpDateInAdapter()
        turnBackImgB.setOnClickListener { fragmentManager!!.popBackStack() }
    }

    class SimpleAdapter(private val context: FragmentActivity, val headers: (MutableList<String>?),
                        private val description: (MutableList<String>?), private val images: (MutableList<Int>?)) :
            RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpData(context, headers!![position], description!![position], images!![position])
        }

        override fun getItemCount() = headers!!.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun setUpData(context: FragmentActivity,
                          header: String,
                          description: String,
                          image: Int) {

                itemView.itemNameTxtV.text = header

                itemView.setOnClickListener {
                    try {
                        context.supportFragmentManager
                                .beginTransaction()
                                .addToBackStack(null)
                                .add(R.id.mainContainerConstr,
                                        GlossaryDetails.getInstance(
                                                header,
                                                description,
                                                image),
                                        const.GLOSSARY_DETAILS)
                                .commit()
                    } catch (e: IllegalStateException) {
                        Log.d(const.MY_EXCEPTION, e.toString())
                    }

                }
            }
        }
    }
}
