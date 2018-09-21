package parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable.Body
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable.Header

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.glossary, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDateInAdapter()
    }

    private fun setUpDateInAdapter() {

        for (item in baseActivity!!.glossaryItem!!.data) {
            val header = Header(item.title)
            header.arrowUp = ContextCompat.getDrawable(baseActivity!!, R.drawable.arrow_gray_up)!!
            header.arrowDown = ContextCompat.getDrawable(baseActivity!!, R.drawable.arrow_gray_down)!!
            expandable.addView(header)
            expandable.addView(Body(
                    ContextCompat.getDrawable(activity!!, item.imageId)!!,
                    item.body
            ))

        }
    }

    companion object {
        val TAG: String = Glossary::class.java.simpleName
        val instance = Glossary()
    }
}
