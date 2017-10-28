package parkhomov.andrew.lensthicknesscalculator.fragment.glossary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.utils.Utils

/**
 * Created by MyPC on 29.07.2017.
 */

class GlossaryDetails : Fragment() {

    @BindView(R.id.glossaryImageView)
    lateinit var imageHolder: ImageView
    @BindView(R.id.glossaryTitleTextView)
    lateinit var titleHolder: TextView
    @BindView(R.id.glossaryDescriptionTextView)
    lateinit var descriptionHolder: TextView
    @BindView(R.id.header)
    lateinit var header: TextView

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.glossary_detail, container, false)
        ButterKnife.bind(this, view)

        header.text = Utils.spacing(title!!.toUpperCase(), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8)
        header.ellipsize = TextUtils.TruncateAt.MARQUEE
        header.marqueeRepeatLimit = -1
        header.setSingleLine(true)
        header.isSelected = true
        //set data in holder
        imageHolder.setImageDrawable(ContextCompat.getDrawable(activity, imageId))
        titleHolder.text = title
        descriptionHolder.text = description

        return view
    }

    @OnClick(R.id.turnBackImgB)
    fun onBackPressed() {
        fragmentManager.popBackStack()
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
