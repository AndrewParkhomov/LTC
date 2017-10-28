package parkhomov.andrew.lensthicknesscalculator.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import parkhomov.andrew.lensthicknesscalculator.R

/**
 * Created by MyPC on 29.07.2017.
 */

class Result : DialogFragment() {

    @BindView(R.id.centerThicknessTxtV)
    lateinit var centerThicknessHolder: TextView
    @BindView(R.id.minimumEdgeThicknessTxtV)
    lateinit var minimumThicknessHolder: TextView
    @BindView(R.id.maximumEdgeThicknessTxtV)
    lateinit var maximumThicknessHolder: TextView
    @BindView(R.id.dymanicAxisEdgeThicknessTxtV)
    lateinit var onAxisThicknessHolder: TextView
    @BindView(R.id.include_3)
    lateinit var divider3: View
    @BindView(R.id.include_4)
    lateinit var divider4: View

    private var center: String? = null
    private var minimum: String? = null
    private var maximum: String? = null
    private var onAxis: String? = null
    private var axis: String? = null

    fun setCenter(center: String) {
        this.center = center
    }

    fun setMinimum(minimum: String) {
        this.minimum = minimum
    }

    fun setMaximum(maximum: String) {
        this.maximum = maximum
    }

    fun setOnAxis(onAxis: String) {
        this.onAxis = onAxis
    }

    fun setAxis(axis: String) {
        this.axis = axis
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.dialog_result, container)
        ButterKnife.bind(this, view)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)

        setUpViewsAndListeners()

        return view
    }

    private fun setUpViewsAndListeners() {
        centerThicknessHolder.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), center, activity!!.getString(R.string.result_mm))
        centerThicknessHolder.text = String.format("%s %s %s", activity!!.getString(R.string.result_center_thickness), center, activity!!.getString(R.string.result_mm))
        minimumThicknessHolder.text = String.format("%s %s %s", activity!!.getString(R.string.result_min_edge_thickness), minimum, activity!!.getString(R.string.result_mm))
        if (maximum != null && maximum != "") {
            maximumThicknessHolder.text = String.format("%s %s %s", activity!!.getString(R.string.result_max_edge_thickness), maximum, activity!!.getString(R.string.result_mm))
        } else {
            maximumThicknessHolder.visibility = View.GONE
            divider3.visibility = View.GONE
        }
        if (onAxis != null && onAxis != "") {
            onAxisThicknessHolder.text = String.format("%s %s°: %s %s", activity!!.getString(R.string.result_on_axis_thickness), axis, onAxis, activity!!.getString(R.string.result_mm))
        } else {
            onAxisThicknessHolder.visibility = View.GONE
            divider4.visibility = View.GONE
        }
    }

    companion object {

        fun getInstance(center: String, minimum: String, maximum: String, thkOnAxis: String, axis: String): Result {
            val bundle = Bundle()
            val alertDialog = Result()
            alertDialog.arguments = bundle
            alertDialog.setCenter(center)
            alertDialog.setMinimum(minimum)
            alertDialog.setMaximum(maximum)
            alertDialog.setOnAxis(thkOnAxis)
            alertDialog.setAxis(axis)
            return alertDialog
        }

        fun getInstance(center: String, minimum: String): Result {
            val bundle = Bundle()
            val alertDialog = Result()
            alertDialog.arguments = bundle
            alertDialog.setCenter(center)
            alertDialog.setMinimum(minimum)
            return alertDialog
        }
    }
}