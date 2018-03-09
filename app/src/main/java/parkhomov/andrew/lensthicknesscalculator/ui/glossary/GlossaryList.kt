package parkhomov.andrew.lensthicknesscalculator.ui.glossary

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary_list.*
import kotlinx.android.synthetic.main.glossary_list_item.view.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class GlossaryList : BaseFragment() {

    companion object {
        val TAG: String = GlossaryList::class.java.simpleName
        val instance = GlossaryList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.glossary_list, container, false)


        return view
    }

    private fun setUpDateInAdapter() {
        val lm = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // make divider
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                lm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = SimpleAdapter(baseActivity!!, headers!!, description!!, images!!)
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        header.text = Utils.spacing(getString(R.string.header_glossary_list), const.spacing8)
        setUpDateInAdapter()
        turnBackImgB.setOnClickListener { fragmentManager!!.popBackStack() }
    }

    class SimpleAdapter(private val context: BaseActivity,
                        private val headers: List<String> = listOf(),
                        private val description: List<String> = listOf(),
                        private val images: List<Int> = listOf()) :
            RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.glossary_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpData(context, position, headers, description, images)
        }

        override fun getItemCount() = headers.count()

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun setUpData(context: BaseActivity,
                          position: Int,
                          headers: List<String>,
                          description: List<String>,
                          images: List<Int>) {

                itemView.itemNameTxtV.text = headers[position]

                itemView.setOnClickListener {
                    try {
                        context.supportFragmentManager
                                .beginTransaction()
                                .addToBackStack(null)
                                .add(R.id.mainContainerConstr, GlossaryDetails.getInstance(
                                        headers[position],
                                        description[position],
                                        images[position]
                                ), GlossaryDetails.TAG)
                                .commit()
                    } catch (e: IllegalStateException) {
                    }

                }
            }
        }
    }
}
