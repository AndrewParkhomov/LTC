package parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.content.Context

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class GlossaryList : Parent() {

    @BindView(R2.id.recyclerView)
    lateinit var recyclerView: RecyclerView
    @BindView(R2.id.header)
    lateinit var header: TextView

    private var headers: List<String>? = null
    private var description: List<String>? = null
    private var images: List<Int>? = null

    fun setHeaders(headers: List<String>) {
        this.headers = headers
    }

    fun setDescription(description: List<String>) {
        this.description = description
    }

    fun setImages(images: List<Int>) {
        this.images = images
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.glossary_list, container, false)
        ButterKnife.bind(this, view)

        header.text = Utils.spacing(getString(R.string.header_glossary_list), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8)
        setUpDateInAdapter()

        return view
    }


    private fun setUpDateInAdapter() {
//        val lm = LinearLayoutManager(activity)
//        recyclerView!!.layoutManager = lm
//        // make divider
//        val dividerItemDecoration = DividerItemDecoration(recyclerView!!.context,
//                lm.orientation)
//        recyclerView!!.addItemDecoration(dividerItemDecoration)
//        var simple = Simple(this, headers, description, images)
//
//        recyclerView!!.adapter = simple
    }

    @OnClick(R.id.turnBackImgB)
    fun onBackPressed() {
        fragmentManager.popBackStack()
    }

//    class Simple(context: Context, headers: List<String>?, description: List<String>, images: List<Int>) : BaseAdapter() {
//
//
//
//        private companion object {
//            private val CONTENTS = "The quick brown fox jumps over the lazy dog"
//                    .split(" ".toRegex())
//                    .dropLastWhile { it.isEmpty() }
//                    .toTypedArray()
//        }
//
//        private val inflater = LayoutInflater.from(context)
//
//        override fun getCount() = CONTENTS.size
//        override fun getItem(position: Int) = CONTENTS[position]
//        override fun getItemId(position: Int) = position.toLong()
//
//        override fun getView(position: Int, savedView: View?, parent: ViewGroup): View {
//            var view = savedView
//
//            val holder: ViewHolder
//            if (view != null) {
//                holder = view.tag as ViewHolder
//            } else {
//                view = inflater.inflate(R.layout.simple_list_item, parent, false)
//                holder = ViewHolder(view)
//                view!!.tag = holder
//            }
//
//            val word = getItem(position)
//
//            // Note: don't actually do string concatenation like this in an adapter's getView.
//            holder.word.text = "Word: " + word
//            holder.length.text = "Length: " + word.length
//            holder.position.text = "Position: " + position
//
//            return view
//        }
//
//
//
//        private val inflater = LayoutInflater.from(context)
//        // create headers in list
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlossaryList.Simple.ListViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
//            return GlossaryList.Simple.ListViewHolder(view)
//        }
//
//        // set images and names to each list item
//        override fun onBindViewHolder(holder: GlossaryList.Simple.ListViewHolder, position: Int) {
//            val docName = headers!![position]
//            holder.itemName!!.text = docName
//            holder.itemName!!.setOnClickListener {
//                try {
//                    fragmentManager
//                            .beginTransaction()
//                            .addToBackStack(null)
//                            .add(R.id.mainContainerConstr,
//                                    GlossaryDetails.getInstance(
//                                            headers[position],
//                                            description[position],
//                                            images[position]),
//                                    CONSTANT.GLOSSARY_DETAILS)
//                            .commit()
//                } catch (e: IllegalStateException) {
//                    Log.d(CONSTANT.MY_EXCEPTION, e.toString() + "")
//                }
//            }
//        }
//
//
//        internal class ListViewHolder(view: View) {
//            @BindView(R2.id.itemNameTxtV)
//            lateinit var itemName: TextView
//
//            init {
//                ButterKnife.bind(this, view)
//            }
//        }
//    }
//
    companion object {

        fun getInstance(headers: List<String>, description: List<String>, images: List<Int>): GlossaryList {
            val bundle = Bundle()
            val glossaryList = GlossaryList()
            glossaryList.arguments = bundle
            glossaryList.setHeaders(headers)
            glossaryList.setDescription(description)
            glossaryList.setImages(images)
            return glossaryList
        }
    }
}
