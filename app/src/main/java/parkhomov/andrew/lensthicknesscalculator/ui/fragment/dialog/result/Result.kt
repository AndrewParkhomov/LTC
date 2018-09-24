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

    override val presenter: ResultI.Presenter  by inject()

    private var data: CalculatedData? = null

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

        data = arguments?.getParcelable(TAG)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View) {
        centerThicknessTxtV.text = getString(R.string.result_center_thickness, data?.thicknessCenter)
        centerThicknessTxtV.text = getString(R.string.result_center_thickness, data?.thicknessCenter)
        minimumEdgeThicknessTxtV.text = getString(R.string.result_min_edge_thickness, data?.thicknessEdge)
        if (data?.thicknessMax?.isNotEmpty() == true) {
            maximumEdgeThicknessTxtV.text = getString(R.string.result_max_edge_thickness, data?.thicknessMax)
        } else {
            maximumEdgeThicknessTxtV.visibility = View.GONE
            include_3.visibility = View.GONE
        }
        if (data?.thicknessOnAxis?.isNotEmpty() == true) {
            dymanicAxisEdgeThicknessTxtV.text = getString(R.string.result_on_axis_thickness, data?.axis, data?.thicknessOnAxis)
        } else {
            dymanicAxisEdgeThicknessTxtV.visibility = View.GONE
        }
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
