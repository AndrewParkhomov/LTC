package parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.thickness_fragment.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.*

/**
 * Created by MyPC on 29.07.2017.
 */

class Thickness : BaseFragment(),
        ThicknessI.View {

    private var axis: Int = 0
    private var axisView: Int = 0
    private var lensIndex: Double = 0.0
    private var lensIndexText: String = ""
    private var indexX: Double = 0.0
    private var spherePower: Double = 0.0
    private var edgeThickness: Double = 0.0
    private var realBackRadiusInMM: Double = 0.0
    private var cylinderPower: Double = 0.0
    private var lensDiameter: Double = 0.0
    private var centerThickness: Double = 0.0
    private var sag1Sphere: Double = 0.0
    private var sag2Sphere: Double = 0.0
    private var sag2Cylinder: Double = 0.0
    private var realBackCylinderRadiusInMM: Double = 0.0
    private var realFrontBaseCurveDptr: Double = 0.0
    private var realRadiusMM: Double = 0.0
    private var recalculatedCylinderCurve: Double = 0.0
    private var recalculatedSphereCurve: Double = 0.0

    override val presenter: ThicknessI.Presenter  by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.thickness_fragment, container, false)

        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        customizeSpinner()
        setUpTextWatchers()

        thicknessCalculateButtonOld.setOnClickListener {
            onCalculateBtnClicked()
        }
        thicknessCalculateButton.setOnClickListener {
            presenter.onCalculateBtnClicked(
                    getLensIndex(),
                    sphere_.text.toString(),
                    cylinder_.text.toString(),
                    axis_.text.toString(),
                    curve_.text.toString(),
                    center_thickness.text.toString(),
                    edge_thickness.text.toString(),
                    diameter_.text.toString()
            )
        }
        text_view_spinner.setOnClickListener { spinner.performClick() }
    }

    private fun customizeSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity!!,
                R.array.index_of_refraction, R.layout.spinner_header)
        adapter.setDropDownViewResource(R.layout.spinner_body)
        spinner.adapter = adapter
    }

    private fun setUpTextWatchers() {
        sphere_.addTextChangedListener(GenericTextWatcher())
        cylinder_.addTextChangedListener(GenericTextWatcher())
    }

    private fun getLensIndex(): Triple<Double, Double, String> {
        val spinnerText = spinner.selectedItem.toString()
        return when (spinner.selectedItemPosition) {
            0 -> Triple(INDEX_1498, INDEX_X_1498, spinnerText)
            1 -> Triple(INDEX_1560, INDEX_X_1560, spinnerText)
            2 -> Triple(INDEX_1530, INDEX_X_1530, spinnerText)
            3 -> Triple(INDEX_1590, INDEX_X_1590, spinnerText)
            4 -> Triple(INDEX_1610, INDEX_X_1610, spinnerText)
            5 -> Triple(INDEX_1670, INDEX_X_1670, spinnerText)
            6 -> Triple(INDEX_1740, INDEX_X_1740, spinnerText)
            else -> throw NoSuchElementException("No valid lens index of refraction provided")
        }
    }

    override fun highlightSpherePower(isShowError: Boolean) {
        if (isShowError) {
            wrapper_sphere.isErrorEnabled = true
            wrapper_sphere.error = getString(R.string.tab_thkns_provide_sphere)
        } else {
            wrapper_sphere.error = null
            wrapper_sphere.isErrorEnabled = false
        }
    }

    override fun highlightCenterThickness(isShowError: Boolean) {
        if (isShowError) {
            wrapper_center_thickness.isErrorEnabled = true
            wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)
        } else {
            wrapper_center_thickness.error = null
            wrapper_center_thickness.isErrorEnabled = false
        }
    }

    override fun highlightDiameter(isShowError: Boolean) {
        if (isShowError) {
            wrapper_diameter.isErrorEnabled = true
            wrapper_diameter.error = getString(R.string.tab_thkns_provide_diameter)
        } else {
            wrapper_diameter.error = null
            wrapper_diameter.isErrorEnabled = false
        }
    }

    override fun setCurrentBaseCurve(curveValue: String) {
        curve_.setText(curveValue)
    }

    private fun onCalculateBtnClicked() {
        clearData()
        lensIndexText = spinner.selectedItem.toString()
        when (spinner.selectedItemPosition) {
            0 -> {
                lensIndex = INDEX_1498
                indexX = INDEX_X_1498
            }
            1 -> {
                lensIndex = INDEX_1560
                indexX = INDEX_X_1560
            }
            2 -> {
                lensIndex = INDEX_1530
                indexX = INDEX_X_1530
            }
            3 -> {
                lensIndex = INDEX_1590
                indexX = INDEX_X_1590
            }
            4 -> {
                lensIndex = INDEX_1610
                indexX = INDEX_X_1610
            }
            5 -> {
                lensIndex = INDEX_1670
                indexX = INDEX_X_1670
            }
            6 -> {
                lensIndex = INDEX_1740
                indexX = INDEX_X_1740
            }
        }
        curveCalculation()
    }

    private fun getReaRadiusInMM(): Double = (LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000)

    private fun edgeThicknessET(): Double =
            try {
                edge_thickness.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }

    private fun cylinderET(): Double =
            try {
                cylinder_.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }

    private fun setCenterThickness() {
        // set center thickness
        val tempDoubleForThickness: Double
        if (spherePower <= 0 && cylinderPower == 0.0) {
            try {
                centerThickness = center_thickness.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                wrapper_center_thickness.isErrorEnabled = true
                wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)
            }

        } else if (spherePower <= 0 && cylinderPower > 0) {
            if (spherePower == 0.0) {
                // if sphere power == 0 we change formula, use cylinder power instead sphere power
                // for thickness calculation
                centerThickness = Math.pow(lensDiameter / 2, 2.0) * cylinderPower / (2000 * (lensIndex - 1)) + edgeThickness
            }
            try {
                if (spherePower + cylinderPower < 0) {
                    try {
                        centerThickness = center_thickness.text.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        wrapper_center_thickness.isErrorEnabled = true
                        wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)
                    }

                } else {
                    // fix thickness bug, when lens is plus, and center thickness field is not empty,
                    // when you need press calculate thicknessCalculateButton twice, before you got correct CT value
                    centerThickness = Math.pow(lensDiameter / 2, 2.0) * (spherePower + cylinderPower) / (2000 * (lensIndex - 1)) + edgeThickness
                }
            } catch (e: NumberFormatException) {
                if (Math.abs(spherePower) > Math.abs(cylinderPower)) {
                    throw NumberFormatException()
                }
            }

        } else if (spherePower <= 0) {
            try {
                centerThickness = center_thickness.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                wrapper_center_thickness.isErrorEnabled = true
                wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)

            }

        } else if (spherePower > 0) {
            // if cylinder > 0 we add sphere power and cylinder power
            tempDoubleForThickness = if (cylinderPower > 0) {
                spherePower + cylinderPower
            } else
                spherePower

            // ROUGH Formula for calc CT with plano - concave lens, without pay attention
            // on front curve
            centerThickness = Math.pow(lensDiameter / 2, 2.0) * tempDoubleForThickness / (2000 * (lensIndex - 1)) + edgeThickness
        } else {
            throw NumberFormatException()
        }
    }

    private fun recalculatedFrontCurve(): Double = (lensIndex - 1) * 1000 / realRadiusMM

    private fun axisET(): Int {
        var axis: Int
        try {
            axis = axis_.text.toString().toInt()
            if (axis < 0 || axis > 180) {
                throw NumberFormatException()
            }
        } catch (e: NumberFormatException) {
            if (axis_.text.toString() == "") {
                axis = 0
            } else {
                activity!!.showMessage(R.string.tab_thkns_wrong_axis)
                axis_.text = null
                axis = 0
            }
        }

        return axis
    }

    private fun recalculateAxisInMinusCylinder(inputAxis: Int): Int {
        var axis = inputAxis
        if (cylinderPower > 0) {
            spherePower += cylinderPower
            cylinderPower = -cylinderPower
            when {
                axis + 90 > 180 -> axis = Math.abs(180 - (axis + 90))
                axis > 90 -> axis = 180 - axis
                axis <= 90 -> axis = 180 - (axis + 90)
            }
        } else if (cylinderPower < 0) {
            if (axis > 90) axis = 180 - axis
        }
        return axis
    }

    private fun getRecalculatedCylinderCurve(recalculatedFrontCurve: Double):
            Double = (cylinderPower - (recalculatedFrontCurve / (1 - centerThickness / lensIndex / 1000.0 * recalculatedFrontCurve) - spherePower)) * indexX

    private val realCylinderBackRadiusInMM: Double
        get() = (LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000)

    private fun getSag2Cylinder(): Double =
            Math.abs(realBackCylinderRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackCylinderRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0)) // sag of convex surface;

    private fun getSag2Sphere(): Double =
            Math.abs(realRadiusMM - Math.sqrt(Math.pow(realRadiusMM, 2.0) - Math.pow(lensDiameter / 2, 2.0)))    // sag of convex surface;

    private fun getRecalculatedSphereCurve(recalculatedFrontCurve: Double): Double =
            (spherePower - recalculatedFrontCurve / (1 - centerThickness / lensIndex / 1000.0 * recalculatedFrontCurve)) * indexX

    private fun getRealBackRadiusInMM(): Double =
            (LAB_INDEX - 1) / (recalculatedSphereCurve / 1000)

    private fun getSag1Sphere(): Double =
            Math.abs(realBackRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0))    // sag of concave surface;


    private fun setUpViewsBehaviourAfter() {
        wrapper_sphere.isEnabled = true
        wrapper_curve.isEnabled = true
        wrapper_center_thickness.isEnabled = true
        wrapper_edge_thickness.isEnabled = true
        wrapper_diameter.isEnabled = true
    }

    private fun setUpViewsBehaviourBefore() {
        wrapper_sphere.error = null
        wrapper_curve.error = null
        wrapper_sphere.isErrorEnabled = false
        wrapper_curve.isErrorEnabled = false

        // if field is disable, we don't change it color
//        if (centerThicknessET.currentHintTextColor != ContextCompat.getColor(activity!!, R.color.black) ||
//                centerThicknessET.currentTextColor != ContextCompat.getColor(activity!!, R.color.black))
//            Utils.makeNormalEditText(baseActivity!!, centerThicknessET, thicknessTxtInptL)
//        if (edgeThicknessET.currentHintTextColor != ContextCompat.getColor(activity!!, R.color.black) ||
//                edgeThicknessET.currentTextColor != ContextCompat.getColor(activity!!, R.color.black))
//            Utils.makeNormalEditText(baseActivity!!, edgeThicknessET, edgeTxtInptL)
//        Utils.makeNormalEditText(baseActivity!!, diameterET, diameterTxtInptL)


        wrapper_sphere.isEnabled = false
        wrapper_curve.isEnabled = false
        wrapper_center_thickness.isEnabled = false
        wrapper_edge_thickness.isEnabled = false
        wrapper_diameter.isEnabled = false
    }

    private fun curveCalculation() {
        /* this method make like 'event' when disable fields, and after calculation enable again,
       this allow to highlight required field that will be  highlighted in try blocks below*/
        setUpViewsBehaviourBefore()
        // try blocks here to highlight required field
        var sphereIsEmpty = false
        try {
            spherePower = sphere_.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            wrapper_sphere.isErrorEnabled = true
            wrapper_sphere.error = getString(R.string.tab_thkns_provide_sphere)
            sphereIsEmpty = true
        }

        try {
            realFrontBaseCurveDptr = curve_.text.toString().toDouble()
            if (realFrontBaseCurveDptr == 0.0) throw NumberFormatException()
        } catch (e: NumberFormatException) {
            handleNoBaseCurveBehaviour(sphereIsEmpty)
        }

        try {
            lensDiameter = java.lang.Double.parseDouble(diameter_.text.toString())
        } catch (e: NumberFormatException) {
            wrapper_diameter.isErrorEnabled = true
            wrapper_diameter.error = getString(R.string.tab_thkns_provide_diameter)
        }

        // Real radius of front curve in mm
        realRadiusMM = getReaRadiusInMM()
        Log.i("realRadiusMm", realRadiusMM.toString())

        edgeThickness = edgeThicknessET()

        cylinderPower = cylinderET()

        setCenterThickness()
        Log.i("tempCenterThickness", centerThickness.toString())

        // Find D1
        val recalculatedFrontCurve = recalculatedFrontCurve()
        Log.i("recalculatedFrontCurve", recalculatedFrontCurve.toString())

        if (cylinderPower > 0 || cylinderPower < 0) {

            // check is axis valid, and get it
            axisView = axisET()
            axis = recalculateAxisInMinusCylinder(axisView)
            //                Log.d(CONSTANT.MY_EXCEPTION, axisView + " asixview");
            //                Log.d(CONSTANT.MY_EXCEPTION, axis + " asix");

            recalculatedCylinderCurve = getRecalculatedCylinderCurve(recalculatedFrontCurve)
            Log.i("recalculatedCylinderCu", recalculatedCylinderCurve.toString())

            realBackCylinderRadiusInMM = realCylinderBackRadiusInMM
            Log.i("realCylinderBackRadMM", realBackCylinderRadiusInMM.toString())

            sag2Cylinder = getSag2Cylinder()
            Log.i("sag2Cylinder", sag2Cylinder.toString())
        }
        sag2Sphere = getSag2Sphere()
        Log.i("sag2Sphere", sag2Sphere.toString())

        // Corrected back curve
        recalculatedSphereCurve = getRecalculatedSphereCurve(recalculatedFrontCurve)
        Log.i("recalculatedSphereCurve", recalculatedSphereCurve.toString())

        // Real radius of back curve in mm(we need exactly in mm for sag formula)
        realBackRadiusInMM = getRealBackRadiusInMM()
        Log.i("realBackRadiusInMM", realBackRadiusInMM.toString())

        sag1Sphere = getSag1Sphere()
        Log.i("sag1Sphere", sag1Sphere.toString())

        sphereThicknessCalculation()

        setUpViewsBehaviourAfter()
    }

    private fun handleNoBaseCurveBehaviour(sphereIsEmpty: Boolean) {
        if (!sphereIsEmpty) {
            val cylinder = cylinderET()
            val sphere: Double = if (cylinder > 0) {
                spherePower + cylinder
            } else {
                spherePower
            }

            var tempCurveString = ""
            var tempCurveDouble: Double = 0.toDouble()

            when {
                sphere <= -8.0 -> {
                    tempCurveString = base0.toString()
                    tempCurveDouble = base0
                }
                sphere <= -6.0 && sphere >= -7.99 -> {
                    tempCurveString = base1.toInt().toString()
                    tempCurveDouble = base1
                }
                sphere <= -4.0 && sphere >= -5.99 -> {
                    tempCurveString = base2.toInt().toString()
                    tempCurveDouble = base2
                }
                sphere <= -2.0 && sphere >= -3.99 -> {
                    tempCurveString = base3.toInt().toString()
                    tempCurveDouble = base3
                }
                sphere <= 2.0 && sphere >= -1.99 -> {
                    tempCurveString = base4.toInt().toString()
                    tempCurveDouble = base4
                }
                sphere in 2.01..2.99 -> {
                    tempCurveString = base5.toInt().toString()
                    tempCurveDouble = base5
                }
                sphere in 3.0..4.99 -> {
                    tempCurveString = base6.toInt().toString()
                    tempCurveDouble = base6
                }
                sphere in 5.0..5.99 -> {
                    tempCurveString = base7.toInt().toString()
                    tempCurveDouble = base7
                }
                sphere in 6.0..6.99 -> {
                    tempCurveString = base8.toInt().toString()
                    tempCurveDouble = base8
                }
                sphere in 7.0..7.99 -> {
                    tempCurveString = base9.toInt().toString()
                    tempCurveDouble = base9
                }
                sphere in 8.0..9.99 -> {
                    tempCurveString = base10.toInt().toString()
                    tempCurveDouble = base10
                }
                sphere >= 10.0 -> {
                    tempCurveString = base10_5.toString()
                    tempCurveDouble = base10_5
                }
            }
            curve_.setText(tempCurveString)
            realFrontBaseCurveDptr = tempCurveDouble
        }
    }

    private fun clearData() {
        lensIndex = 0.0
        indexX = 0.0
        spherePower = 0.0
        edgeThickness = 0.0
        realBackRadiusInMM = 0.0
        cylinderPower = 0.0
        lensDiameter = 0.0
        centerThickness = 0.0
        sag1Sphere = 0.0
        sag2Sphere = 0.0
        sag2Cylinder = 0.0
        realBackCylinderRadiusInMM = 0.0
        realFrontBaseCurveDptr = 0.0
        realRadiusMM = 0.0
        recalculatedCylinderCurve = 0.0
        recalculatedSphereCurve = 0.0
    }

    private fun sphereThicknessCalculation() {
        if (lensDiameter == 0.0) return
        if (realBackRadiusInMM <= 0) {
            if (spherePower <= 0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThickness
            } else {
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness
            }
        } else {
            centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness
        }

        Log.d("check parameter", spherePower.toString())
        Log.d("check parameter", cylinderPower.toString())
        Log.d("check parameter", axis.toString())
        Log.d("check parameter", realFrontBaseCurveDptr.toString())
        Log.d("check parameter", centerThickness.toString())
        Log.d("check parameter", edgeThickness.toString())

        if (centerThickness != 0.0 && !java.lang.Double.isNaN(centerThickness))
            if (cylinderPower > 0 || cylinderPower < 0) {
                cylinderCalculation()
            } else {
                presenter.showResultDialog(
                        lensIndexText,
                        spherePower.toString(),
                        ((centerThickness * 1e2).toLong() / 1e2).toString(),
                        ((edgeThickness * 1e2).toLong() / 1e2).toString(),
                        realFrontBaseCurveDptr.toString(),
                        lensDiameter.toString()

                )
            }
    }

    private fun cylinderCalculation() {
        var maxEdgeThickness = 0.0
        if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM < 0) {
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
        } else if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM > 0) {
            maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness
        } else if (spherePower >= realFrontBaseCurveDptr && realBackRadiusInMM < 0) {
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
        } else if (spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM > 0) {
            maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness
        } else if (spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM < 0) {
            maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness
        }
        Log.i("maxEdgeThickness", maxEdgeThickness.toString())
        val etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness
        Log.i("etOnCertainAxis", etOnCertainAxis.toString())
        Log.i("spherePower", spherePower.toString())
        Log.i("cylinderPower", cylinderPower.toString())
        Log.i("axisView", axisView.toString())
        Log.i("(1)", ((etOnCertainAxis * 1e2).toLong() / 1e2).toString())
        Log.i("(2)",  ((centerThickness * 1e2).toLong() / 1e2).toString())
        Log.i("(3)", ((edgeThickness * 1e2).toLong() / 1e2).toString())
        Log.i("(4)", ((maxEdgeThickness * 1e2).toLong() / 1e2).toString())
        Log.i("realFrontBaseCurveDptr", realFrontBaseCurveDptr.toString())

        if (cylinderPower != 0.0) {
            presenter.showResultDialog(
                    lensIndexText,
                    spherePower.toString(),
                    cylinderPower.toString(),
                    axisView.toString(),
                    ((etOnCertainAxis * 1e2).toLong() / 1e2).toString(),
                    ((centerThickness * 1e2).toLong() / 1e2).toString(),
                    ((edgeThickness * 1e2).toLong() / 1e2).toString(),
                    ((maxEdgeThickness * 1e2).toLong() / 1e2).toString(),
                    realFrontBaseCurveDptr.toString(),
                    lensDiameter.toString()
            )
        } else {
            presenter.showResultDialog(
                    lensIndexText,
                    spherePower.toString(),
                    ((centerThickness * 1e2).toLong() / 1e2).toString(),
                    ((edgeThickness * 1e2).toLong() / 1e2).toString(),
                    realFrontBaseCurveDptr.toString(),
                    lensDiameter.toString()
            )
        }
    }

    private inner class GenericTextWatcher : TextWatcher {

        val enabled = R.style.HintTextAppearanceEnable
        val disabled = R.style.HintTextAppearanceDisable
        val colorTextEnable = activity!!.getColorFromId(R.color.text_color_enable)
        val colorEnable = activity!!.getColorFromId(R.color.accent)
        val colorDisable = activity!!.getColorFromId(R.color.gray_400)

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            val spherePower = try {
                sphere_.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            val cylinderPower = try {
                cylinder_.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            if (spherePower != null) {
                val value = if (cylinderPower != null)
                    spherePower + cylinderPower
                else
                    spherePower
                wrapper_center_thickness.isEnabled = value <= 0
                wrapper_edge_thickness.isEnabled = value > 0
                highlightCenterThickness(false)
                center_thickness.setTextColor(if (value <= 0) colorTextEnable else colorDisable)
                edge_thickness.setTextColor(if (value > 0) colorTextEnable else colorDisable)
                wrapper_center_thickness.boxStrokeColor = if (value <= 0) colorEnable else colorDisable
                wrapper_edge_thickness.boxStrokeColor = if (value > 0) colorEnable else colorDisable
                wrapper_center_thickness.setHintTextAppearance(if (value <= 0) enabled else disabled)
                wrapper_edge_thickness.setHintTextAppearance(if (value > 0) enabled else disabled)
            } else {
                wrapper_center_thickness.isEnabled = true
                wrapper_edge_thickness.isEnabled = true
                highlightCenterThickness(false)
                wrapper_center_thickness.boxStrokeColor = colorEnable
                wrapper_edge_thickness.boxStrokeColor = colorEnable
                wrapper_center_thickness.setHintTextAppearance(enabled)
                wrapper_edge_thickness.setHintTextAppearance(enabled)
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }

    companion object {
        val TAG: String = Thickness::class.java.name
        val instance = Thickness()
    }
}
