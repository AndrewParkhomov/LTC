package parkhomov.andrew.lensthicknesscalculator.ui.glossary

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.glossary_detail.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const

/**
 * Created by MyPC on 29.07.2017.
 */

class GlossaryDetails : BaseFragment() {


    private lateinit var title: String
    private lateinit var body: String
    private var imageId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.glossary_detail, container, false)

        title = arguments?.getString(TAG + "header")!!
        body = arguments?.getString(TAG + "description")!!
        imageId = arguments?.getInt(TAG + "imageId")!!

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
        glossaryDescriptionTextView.text = body
        turnBackImgB.setOnClickListener { fragmentManager!!.popBackStack() }

    }

    companion object {
        val TAG: String = GlossaryDetails::class.java.simpleName
        fun getInstance(header: String, description: String, imageId: Int) = GlossaryDetails().apply {
            arguments = Bundle(3).apply {
                putString(TAG + "header", header)
                putString(TAG + "description", description)
                putInt(TAG + "imageId", imageId)
            }
        }
    }
}
