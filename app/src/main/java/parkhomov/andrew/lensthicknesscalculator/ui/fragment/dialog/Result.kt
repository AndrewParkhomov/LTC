package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.dialog_result.*
import parkhomov.andrew.lensthicknesscalculator.R

/**
 * Created by MyPC on 29.07.2017.
 */

class Result : DialogFragment() {

    private var center: String? = null
    private var minimum: String? = null
    private var maximum: String? = null
    private var onAxis: String? = null
    private var axis: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_result, container)

        center = arguments?.getString(TAG + "center")
        minimum = arguments?.getString(TAG + "minimum")
        maximum = arguments?.getString(TAG + "maximum")
        onAxis = arguments?.getString(TAG + "thkOnAxis")
        axis = arguments?.getString(TAG + "axis")

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewsAndListeners()
    }

    private fun setUpViewsAndListeners() {
        centerThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), center, activity!!.getString(R.string.result_mm))
        centerThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), center, activity!!.getString(R.string.result_mm))
        minimumEdgeThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_min_edge_thickness), minimum, activity!!.getString(R.string.result_mm))
        if (maximum != null && maximum != "") {
            maximumEdgeThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_max_edge_thickness), maximum, activity!!.getString(R.string.result_mm))
        } else {
            maximumEdgeThicknessTxtV.visibility = View.GONE
            include_3.visibility = View.GONE
        }
        if (onAxis != null && onAxis != "") {
            dymanicAxisEdgeThicknessTxtV.text = String.format("%s %s°: %s %s", activity!!.getString(R.string.result_on_axis_thickness), axis, onAxis, activity!!.getString(R.string.result_mm))
        } else {
            dymanicAxisEdgeThicknessTxtV.visibility = View.GONE
            include_4.visibility = View.GONE
        }
    }

    companion object {

        val TAG: String = Result::class.java.simpleName
        fun getInstance(center: String,
                        minimum: String,
                        maximum: String,
                        thkOnAxis: String,
                        axis: String
        ) = Result().apply {
            arguments = Bundle(5).apply {
                putString(TAG + "center", center)
                putString(TAG + "minimum", minimum)
                putString(TAG + "maximum", maximum)
                putString(TAG + "thkOnAxis", thkOnAxis)
                putString(TAG + "axis", axis)
            }
        }

        fun getInstance(center: String, minimum: String) = Result().apply {
            arguments = Bundle(2).apply {
                putString(TAG + "center", center)
                putString(TAG + "minimum", minimum)
            }
        }
    }
}