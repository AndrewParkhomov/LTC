package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.dialog_result.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

/**
 * Created by MyPC on 29.07.2017.
 */

class Result : DialogFragment() {

    private var data: CalculatedData? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_result, container)

        data = arguments?.getParcelable(TAG)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewsAndListeners()
    }

    private fun setUpViewsAndListeners() {
        centerThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), data?.thicknessCenter, activity!!.getString(R.string.result_mm))
        centerThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), data?.thicknessCenter, activity!!.getString(R.string.result_mm))
        minimumEdgeThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_min_edge_thickness), data?.thicknessEdge, activity!!.getString(R.string.result_mm))
        if (data?.thicknessMax != null && data?.thicknessMax != "") {
            maximumEdgeThicknessTxtV.text = String.format("%s %s %s", activity!!.getString(R.string.result_max_edge_thickness), data?.thicknessMax, activity!!.getString(R.string.result_mm))
        } else {
            maximumEdgeThicknessTxtV.visibility = View.GONE
            include_3.visibility = View.GONE
        }
        if (data?.thicknessOnAxis != null && data?.thicknessOnAxis != "") {
            dymanicAxisEdgeThicknessTxtV.text = String.format("%s %s°: %s %s", activity!!.getString(R.string.result_on_axis_thickness), data?.axis, data?.thicknessOnAxis, activity!!.getString(R.string.result_mm))
        } else {
            dymanicAxisEdgeThicknessTxtV.visibility = View.GONE
            include_4.visibility = View.GONE
        }
    }

    companion object {
        val TAG: String = Result::class.java.simpleName
        fun getInstance(calculatedData: CalculatedData) = Result().apply {
            arguments = Bundle(1).apply {
                putParcelable(TAG, calculatedData)
            }
        }
    }
}