package parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable.Body
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable.Header
import parkhomov.andrew.lensthicknesscalculator.utils.getDrawableFromId

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        for (item in baseActivity!!.glossaryItem!!.data) {
            val header = Header(item.title)
            header.arrowUp = activity!!.getDrawableFromId(R.drawable.arrow_gray_up)
            header.arrowDown = activity!!.getDrawableFromId(R.drawable.arrow_gray_down)
            expandable.addView(header)
            expandable.addView(Body(activity!!.getDrawableFromId(item.imageId), item.body) {
                expandable.collapseAll()
            })
        }
    }

    companion object {
        val TAG: String = Glossary::class.java.name
        val instance = Glossary()
    }
}
