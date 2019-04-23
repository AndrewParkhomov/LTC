package parkhomov.andrew.glossary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.glossary.*
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.glossary.R
import parkhomov.andrew.glossary.adapter.AdapterGlossary

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_expandable.layoutManager = LinearLayoutManager(activity!!)
        recycler_view_expandable.adapter = AdapterGlossary(baseActivity!!.glossaryItem!!.data)
    }

    companion object {
        val TAG: String = Glossary::class.java.name
        val instance = Glossary()
    }
}
