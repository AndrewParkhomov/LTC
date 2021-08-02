package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.glossary.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.adapter.AdapterGlossary
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_expandable.layoutManager = LinearLayoutManager(requireContext())
        recycler_view_expandable.adapter = AdapterGlossary(baseActivity!!.glossaryItem!!.data)
    }

    companion object {
        val TAG: String = Glossary::class.java.name
        val instance = Glossary()
    }
}
