package parkhomov.andrew.lensthicknesscalculator.ui.glossary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary_detail.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.const
import parkhomov.andrew.lensthicknesscalculator.utils.Utils

/**
 * Created by MyPC on 29.07.2017.
 */

class GlossaryDetails : Fragment() {


    private var title: String? = null
    private var description: String? = null
    private var imageId: Int = 0

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setImageId(imageId: Int) {
        this.imageId = imageId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.glossary_detail, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        header.text = Utils.spacing(title!!.toUpperCase(), const.spacing8)
        header.ellipsize = TextUtils.TruncateAt.MARQUEE
        header.marqueeRepeatLimit = -1
        header.setSingleLine(true)
        header.isSelected = true
        //set data in holder
        glossaryImageView.setImageDrawable(ContextCompat.getDrawable(activity!!, imageId))
        glossaryTitleTextView.text = title
        glossaryDescriptionTextView.text = description
        turnBackImgB.setOnClickListener {  fragmentManager!!.popBackStack() }

    }

    companion object {

        fun getInstance(title: String, description: String, imageId: Int): GlossaryDetails {
            val bundle = Bundle()
            val glossaryDetails = GlossaryDetails()
            glossaryDetails.arguments = bundle
            glossaryDetails.setTitle(title)
            glossaryDetails.setDescription(description)
            glossaryDetails.setImageId(imageId)
            return glossaryDetails
        }
    }
}
