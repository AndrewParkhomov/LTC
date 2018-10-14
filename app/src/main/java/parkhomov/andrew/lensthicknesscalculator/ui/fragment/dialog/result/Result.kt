package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_result.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

class Result : BaseDialog(),
        ResultI.View {

    override val presenter: ResultI.Presenter by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
                R.layout.dialog_result,
                container,
                false
        )

        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View) {
        presenter.setResult(arguments!!.getParcelable(TAG)!!)
    }

    override fun showCylinderViews(isShow: Boolean) {
        view_cylinder.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_cylinder_power.visibility = if (isShow) View.VISIBLE else View.GONE
        view_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        view_thickness_on_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_thickness_on_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        view_max_thickness.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_max_thickness.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun setRefractionIndex(refractionIndex: String) {
        text_view_index_of_refraction.text = getString(R.string.result_index_of_refraction, refractionIndex)
    }

    override fun setSpherePower(spherePower: String) {
        text_view_sphere_power.text = getString(R.string.result_sphere_power, spherePower)
    }

    override fun setCylinderPower(cylinderPower: String) {
        text_view_cylinder_power.text = getString(R.string.result_cylinder_power, cylinderPower)
    }

    override fun setAxis(axis: String?) {
        text_view_axis.text = getString(R.string.result_axis, axis)
    }

    override fun setThicknessOnAxis(axis: String?, thicknessOnAxis: String?) {
        text_view_thickness_on_axis.text = getString(R.string.result_on_axis_thickness, axis, thicknessOnAxis)
    }

    override fun setCenterThickness(thicknessCenter: String) {
        text_view_center_thickness.text = getString(R.string.result_center_thickness, thicknessCenter)
    }

    override fun setEdgeThickness(thicknessEdge: String) {
        text_view_min_thickness.text = getString(R.string.result_min_edge_thickness, thicknessEdge)
    }

    override fun setMaxThickness(thicknessMax: String?) {
        text_view_max_thickness.text = getString(R.string.result_max_edge_thickness, thicknessMax)
    }

    override fun setBaseCurve(realBaseCurve: String) {
        text_view_real_base_curve.text = getString(R.string.result_base_curve, realBaseCurve)
    }

    override fun setDiameter(diameter: String) {
        text_view_diameter.text = getString(R.string.result_diameter, diameter)
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    companion object {
        val TAG: String = Result::class.java.name
        fun getInstance(calculatedData: CalculatedData) = Result().apply {
            arguments = Bundle(1).apply {
                putParcelable(TAG, calculatedData)
            }
        }
    }
}
