package parkhomov.andrew.lensthicknesscalculator.activities.tabs

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.hanks.htextview.HTextView
import com.hanks.htextview.HTextViewType
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails
import parkhomov.andrew.lensthicknesscalculator.activities.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.activities.main.MyApp
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils
import java.text.DecimalFormat

/**
 * Created by MyPC on 29.07.2017.
 */


class Diameter : AbstractTabFragment(), MainActivity.HideKeyboardI {

    @BindView(R2.id.textTest)
    lateinit var textTest: HTextView
    @BindView(R.id.diameterCalculateButton)
    lateinit var button: Button

    @BindView(R2.id.edTxtInptL)
    lateinit var edWrapper: TextInputLayout
    @BindView(R2.id.dblTxtInptL)
    lateinit var dblWrapper: TextInputLayout
    @BindView(R2.id.pdTxtInptL)
    lateinit var pdWrapper: TextInputLayout

    @BindView(R2.id.edET)
    lateinit var edET: EditText
    @BindView(R2.id.dblET)
    lateinit var dblET: EditText
    @BindView(R2.id.pdET)
    lateinit var pdET: EditText


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.diameter_fragment, container, false)
        ButterKnife.bind(this, view)
        activity = getActivity()

        button.text = Utils.spacing(getString(R.string.button_text_calculate), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8)
        setUpListeners()
        return view
    }

    private fun setUpListeners() {
        val viewPager = ButterKnife.findById<ViewPager>(activity!!, R.id.viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                hideSoftKeyboard()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun hideKeyboard() {
        hideSoftKeyboard()
    }

    @OnClick(R.id.diameterCalculateButton)
    fun onButtonClicked() {
        setUpViewsBehaviourBefore()
        var edValue = -1.0
        var dblValue = -1.0
        var pdValue = -1.0
        try {
            edValue = java.lang.Double.valueOf(edET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(edET, edWrapper)
        }

        try {
            dblValue = java.lang.Double.valueOf(dblET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(dblET, dblWrapper)
        }

        try {
            pdValue = java.lang.Double.valueOf(pdET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(pdET, pdWrapper)
        }

        if (edValue != -1.0 && dblValue != -1.0 && pdValue != -1.0) {
            val diam = Math.ceil(edValue * 2 + dblValue - pdValue)
            val df = DecimalFormat("#")
            val result = String.format("%s%s %s",
                    resources.getString(R.string.diam_activ_textView_result_formula),
                    df.format(diam).toString(),
                    resources.getString(R.string.result_mm))

            // get random true or false and show animation according to type
            val animationType = if (Math.random() < 0.5) HTextViewType.ANVIL else HTextViewType.LINE
            textTest.setAnimateType(animationType)
            // need to repeat animation
            textTest.animateText("")
            textTest.animateText(result)
        }
        setUpViewsBehaviourAfter()
    }

    private fun setUpViewsBehaviourAfter() {
        Utils.enableWrapper(edWrapper)
        Utils.enableWrapper(dblWrapper)
        Utils.enableWrapper(pdWrapper)
    }

    private fun setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(edET, edWrapper)
        Utils.makeNormalEditText(dblET, dblWrapper)
        Utils.makeNormalEditText(pdET, pdWrapper)
        Utils.disableWrapper(edWrapper)
        Utils.disableWrapper(dblWrapper)
        Utils.disableWrapper(pdWrapper)
    }


    @OnClick(R.id.edImgB, R.id.dblImgB, R.id.pdImgB)
    fun onQueryClicked(v: View) {
        var position = -1
        when (v.id) {
            R.id.edImgB -> position = 8
            R.id.dblImgB -> position = 9
            R.id.pdImgB -> position = 10
        }
        if (position != -1) {
            try {
                fragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainContainerConstr,
                                GlossaryDetails.getInstance(
                                        headers?.get(position)!!,
                                        description?.get(position)!!,
                                        images?.get(position)),
                                CONSTANT.GLOSSARY_DETAILS)
                        .commit()
            } catch (e: IllegalStateException) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + "")
            }

            hideSoftKeyboard()
        }
    }

    private fun hideSoftKeyboard() {
        Utils.inputManager.hideSoftInputFromWindow(edET.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(dblET.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(pdET.windowToken, 0)
    }

    companion object {

        fun getInstance(headers: MutableList<String>, description: MutableList<String>, images: MutableList<Int>): Diameter {
            val bundle = Bundle()
            val diameter = Diameter()
            diameter.arguments = bundle
            Companion.setTitle(diameter, MyApp.getAppContext.getString(R.string.tab_diameter))
            Companion.setHeaders(diameter, headers)
            Companion.setDescription(diameter, description)
            Companion.setImages(diameter, images)
            return diameter
        }

        private fun setTitle(diameter: Diameter, title: String) {
            diameter.title = title
        }

        private fun setHeaders(diameter: Diameter, headers: MutableList<String>) {
            diameter.headers = headers
        }

        private fun setDescription(diameter: Diameter, description: MutableList<String>) {
            diameter.description = description
        }

        private fun setImages(diameter: Diameter, images: MutableList<Int>) {
            diameter.images = images
        }

    }
}