package parkhomov.andrew.lensthicknesscalculator.ui.tabs

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.thickness_fragment.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.ui.dialog.Result
import parkhomov.andrew.lensthicknesscalculator.ui.glossary.GlossaryDetails
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const
import timber.log.Timber

/**
 * Created by MyPC on 29.07.2017.
 */

class Thickness : BaseFragment() {

    private var axis: Int = 0
    private var axisView: Int = 0
    private var lensIndex: Double = 0.0
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.thickness_fragment, container, false)

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        headers = arguments?.getStringArrayList(TAG + "headers") ?: listOf()
        description = arguments?.getStringArrayList(TAG + "images") ?: listOf()
        images = arguments?.getIntegerArrayList(TAG + "description") ?: listOf()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        thicknessCalculateButton.text = Utils.spacing(getString(R.string.button_text_calculate), const.spacing8)

        customizeSpinner()
        setUpTextWatchers()

        thicknessCalculateButton.setOnClickListener { onCalculateBtnClicked() }
        indexImgB.setOnClickListener(onQueryClicked)
        sphereImgB.setOnClickListener(onQueryClicked)
        cylinderImgB.setOnClickListener(onQueryClicked)
        axisImgB.setOnClickListener(onQueryClicked)
        curveImgB.setOnClickListener(onQueryClicked)
        thicknessImgB.setOnClickListener(onQueryClicked)
        edgeImgB.setOnClickListener(onQueryClicked)
        diameterImgB.setOnClickListener(onQueryClicked)
    }

    private fun customizeSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.index_of_refraction, R.layout.spinner_header)
        adapter.setDropDownViewResource(R.layout.spinner_body)
        new_spinner.adapter = adapter

    }

    private fun setUpTextWatchers() {
        sphereET.addTextChangedListener(GenericTextWatcher())
        cylinderET.addTextChangedListener(GenericTextWatcher())
    }


    private val onQueryClicked = View.OnClickListener { view ->
        var position = -1
        when (view.id) {
            R.id.indexImgB -> position = 0
            R.id.sphereImgB -> position = 1
            R.id.cylinderImgB -> position = 2
            R.id.axisImgB -> position = 3
            R.id.curveImgB -> position = 4
            R.id.thicknessImgB -> position = 5
            R.id.edgeImgB -> position = 6
            R.id.diameterImgB -> position = 7
        }

        if (position != -1) {
            try {
                fragmentManager!!
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainContainerConstr, GlossaryDetails.getInstance(
                                headers!![position],
                                description!![position],
                                images!![position]
                        ), GlossaryDetails.TAG)
                        .commit()
            } catch (e: IllegalStateException) {
                Timber.i(e.toString())
            }
            hideKeyboard()
        }
    }

    private fun onCalculateBtnClicked() {
        clearData()
        when (new_spinner.selectedItemPosition) {
            0 -> {
                lensIndex = const.INDEX_1498
                indexX = const.INDEX_X_1498
            }
            1 -> {
                lensIndex = const.INDEX_1560
                indexX = const.INDEX_X_1560
            }
            2 -> {
                lensIndex = const.INDEX_1530
                indexX = const.INDEX_X_1530
            }
            3 -> {
                lensIndex = const.INDEX_1590
                indexX = const.INDEX_X_1590
            }
            4 -> {
                lensIndex = const.INDEX_1610
                indexX = const.INDEX_X_1610
            }
            5 -> {
                lensIndex = const.INDEX_1670
                indexX = const.INDEX_X_1670
            }
            6 -> {
                lensIndex = const.INDEX_1740
                indexX = const.INDEX_X_1740
            }
        }
        curveCalculation()
    }

    private fun getReaRadiusInMM(): Double = (const.LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000)

    private fun edgeThicknessET(): Double {
        return try {
            edgeThicknessET.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    private fun cylinderET(): Double {
        return try {
            cylinderET.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    private fun setCenterThickness() {
        // set center thickness
        val tempDoubleForThickness: Double
        if (spherePower <= 0 && cylinderPower == 0.0) {
            try {
                centerThickness = java.lang.Double.parseDouble(centerThicknessET.text.toString())
            } catch (e: NumberFormatException) {
                Utils.highlightEditText(centerThicknessET, thicknessTxtInptL)
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
                        centerThickness = java.lang.Double.parseDouble(centerThicknessET.text.toString())
                    } catch (e: NumberFormatException) {
                        Utils.highlightEditText(centerThicknessET, thicknessTxtInptL)
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
                centerThickness = java.lang.Double.parseDouble(centerThicknessET.text.toString())
            } catch (e: NumberFormatException) {
                Utils.highlightEditText(centerThicknessET, thicknessTxtInptL)

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
            axis = Integer.parseInt(axisET.text.toString())
            if (axis < 0 || axis > 180) {
                throw NumberFormatException()
            }
        } catch (e: NumberFormatException) {
            if (axisET.text.toString() == "") {
                axis = 0
            } else {
                Toast.makeText(activity, resources.getText(R.string.tab_thkns_wrong_axis), Toast.LENGTH_LONG).show()
                axisET.text = null
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
        get() = (const.LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000)

    private fun getSag2Cylinder(): Double =
            Math.abs(realBackCylinderRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackCylinderRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0)) // sag of convex surface;

    private fun getSag2Sphere(): Double =
            Math.abs(realRadiusMM - Math.sqrt(Math.pow(realRadiusMM, 2.0) - Math.pow(lensDiameter / 2, 2.0)))    // sag of convex surface;

    private fun getRecalculatedSphereCurve(recalculatedFrontCurve: Double): Double =
            (spherePower - recalculatedFrontCurve / (1 - centerThickness / lensIndex / 1000.0 * recalculatedFrontCurve)) * indexX

    private fun getRealBackRadiusInMM(): Double =
            (const.LAB_INDEX - 1) / (recalculatedSphereCurve / 1000)

    private fun getSag1Sphere(): Double =
            Math.abs(realBackRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0))    // sag of concave surface;


    private fun setUpViewsBehaviourAfter() {
        Utils.enableWrapper(sphereTxtInptL)
        Utils.enableWrapper(curveTxtInptL)
        Utils.enableWrapper(thicknessTxtInptL)
        Utils.enableWrapper(edgeTxtInptL)
        Utils.enableWrapper(diameterTxtInptL)
    }

    private fun setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(sphereET, sphereTxtInptL)
        Utils.makeNormalEditText(curveET, curveTxtInptL)
        // if field is disable, we don't change it color
        if (centerThicknessET.currentHintTextColor != ContextCompat.getColor(activity!!, R.color.black) ||
                centerThicknessET.currentTextColor != ContextCompat.getColor(activity!!, R.color.black))
            Utils.makeNormalEditText(centerThicknessET, thicknessTxtInptL)
        if (edgeThicknessET.currentHintTextColor != ContextCompat.getColor(activity!!, R.color.black) ||
                edgeThicknessET.currentTextColor != ContextCompat.getColor(activity!!, R.color.black))
            Utils.makeNormalEditText(edgeThicknessET, edgeTxtInptL)
        Utils.makeNormalEditText(diameterET, diameterTxtInptL)
        Utils.disableWrapper(sphereTxtInptL)
        Utils.disableWrapper(curveTxtInptL)
        Utils.disableWrapper(thicknessTxtInptL)
        Utils.disableWrapper(edgeTxtInptL)
        Utils.disableWrapper(diameterTxtInptL)
    }


    private fun curveCalculation() {
        /* this method make like 'event' when disable fields, and after calculation enable again,
       this allow to highlight required field that will be  highlighted in try blocks below*/
        setUpViewsBehaviourBefore()
        // try blocks here to highlight required field
        var sphereIsEmpty = false
        try {
            spherePower = sphereET.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(sphereET, sphereTxtInptL)
            sphereIsEmpty = true
        }

        try {
            realFrontBaseCurveDptr = curveET.text.toString().toDouble()
            if (realFrontBaseCurveDptr == 0.0) throw NumberFormatException()
        } catch (e: NumberFormatException) {
            handleNoBaseCurveBehaviour(sphereIsEmpty)
        }

        try {
            lensDiameter = java.lang.Double.parseDouble(diameterET.text.toString())
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(diameterET, diameterTxtInptL)
        }

        // Real radius of front curve in mm
        realRadiusMM = getReaRadiusInMM()

        edgeThickness = edgeThicknessET()

        cylinderPower = cylinderET()

        setCenterThickness()

        // Find D1
        val recalculatedFrontCurve = recalculatedFrontCurve()

        if (cylinderPower > 0 || cylinderPower < 0) {

            // check is axis valid, and get it
            axisView = axisET()
            axis = recalculateAxisInMinusCylinder(axisView)
            //                Log.d(CONSTANT.MY_EXCEPTION, axisView + " asixview");
            //                Log.d(CONSTANT.MY_EXCEPTION, axis + " asix");

            recalculatedCylinderCurve = getRecalculatedCylinderCurve(recalculatedFrontCurve)

            realBackCylinderRadiusInMM = realCylinderBackRadiusInMM

            sag2Cylinder = getSag2Cylinder()
        }
        sag2Sphere = getSag2Sphere()

        // Corrected back curve
        recalculatedSphereCurve = getRecalculatedSphereCurve(recalculatedFrontCurve)

        // Real radius of back curve in mm(we need exactly in mm for sag formula)
        realBackRadiusInMM = getRealBackRadiusInMM()

        sag1Sphere = getSag1Sphere()

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
                    tempCurveString = const.base0.toString()
                    tempCurveDouble = const.base0
                }
                sphere <= -6.0 && sphere >= -7.99 -> {
                    tempCurveString = const.base1.toInt().toString()
                    tempCurveDouble = const.base1
                }
                sphere <= -4.0 && sphere >= -5.99 -> {
                    tempCurveString = const.base2.toInt().toString()
                    tempCurveDouble = const.base2
                }
                sphere <= -2.0 && sphere >= -3.99 -> {
                    tempCurveString = const.base3.toInt().toString()
                    tempCurveDouble = const.base3
                }
                sphere <= 2.0 && sphere >= -1.99 -> {
                    tempCurveString = const.base4.toInt().toString()
                    tempCurveDouble = const.base4
                }
                sphere in 2.01..2.99 -> {
                    tempCurveString = const.base5.toInt().toString()
                    tempCurveDouble = const.base5
                }
                sphere in 3.0..4.99 -> {
                    tempCurveString = const.base6.toInt().toString()
                    tempCurveDouble = const.base6
                }
                sphere in 5.0..5.99 -> {
                    tempCurveString = const.base7.toInt().toString()
                    tempCurveDouble = const.base7
                }
                sphere in 6.0..6.99 -> {
                    tempCurveString = const.base8.toInt().toString()
                    tempCurveDouble = const.base8
                }
                sphere in 7.0..7.99 -> {
                    tempCurveString = const.base9.toInt().toString()
                    tempCurveDouble = const.base9
                }
                sphere in 8.0..9.99 -> {
                    tempCurveString = const.base10.toInt().toString()
                    tempCurveDouble = const.base10
                }
                sphere >= 10.0 -> {
                    tempCurveString = const.base10_5.toString()
                    tempCurveDouble = const.base10_5
                }
            }
            curveET.setText(tempCurveString)
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

        if (centerThickness != 0.0 && !java.lang.Double.isNaN(centerThickness))
            if (cylinderPower > 0 || cylinderPower < 0) {
                cylinderCalculation()
            } else {
                val result = Result.getInstance(
                        ((centerThickness * 1e2).toLong() / 1e2).toString(),
                        ((edgeThickness * 1e2).toLong() / 1e2).toString())
                result.show(fragmentManager, "result")
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
        val etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness

        if (cylinderPower != 0.0) {
            val result = Result.getInstance(
                    ((centerThickness * 1e2).toLong() / 1e2).toString(),
                    ((edgeThickness * 1e2).toLong() / 1e2).toString(),
                    ((maxEdgeThickness * 1e2).toLong() / 1e2).toString(),
                    ((etOnCertainAxis * 1e2).toLong() / 1e2).toString(),
                    axisView.toString())
            result.show(fragmentManager, "result")
        } else {
            val result = Result.getInstance(
                    ((centerThickness * 1e2).toLong() / 1e2).toString(),
                    ((edgeThickness * 1e2).toLong() / 1e2).toString())
            result.show(fragmentManager, "result")
        }
    }

    //Declaration
    private inner class GenericTextWatcher : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            disableField()
        }

        override fun afterTextChanged(editable: Editable) {

        }

        private fun disableField() {
            spherePower = try {
                java.lang.Double.parseDouble(sphereET.text.toString())
            } catch (e: NumberFormatException) {
                0.0
            }

            cylinderPower = try {
                java.lang.Double.parseDouble(cylinderET.text.toString())
            } catch (e: NumberFormatException) {
                0.0
            }

            val value = if (cylinderPower > 0) spherePower + cylinderPower else spherePower

            Utils.disableWrapper(edgeTxtInptL)
            Utils.disableWrapper(thicknessTxtInptL)
            if (value > 0) {
                Utils.makeNormalEditText(edgeThicknessET, edgeTxtInptL)
                edgeThicknessET.isActivated = true
                edgeThicknessET.isEnabled = true
                edgeThicknessET.isFocusableInTouchMode = true
                Utils.disableThicknessField(centerThicknessET, thicknessTxtInptL)
                centerThicknessET.isActivated = false
                centerThicknessET.isEnabled = false
                centerThicknessET.isFocusableInTouchMode = false
                centerThicknessET.text = null
            } else {
                Utils.makeNormalEditText(centerThicknessET, thicknessTxtInptL)
                centerThicknessET.isActivated = true
                centerThicknessET.isEnabled = true
                centerThicknessET.isFocusableInTouchMode = true
                Utils.disableThicknessField(edgeThicknessET, edgeTxtInptL)
                edgeThicknessET.isActivated = false
                edgeThicknessET.isEnabled = false
                edgeThicknessET.isFocusableInTouchMode = false
                edgeThicknessET.text = null
            }
            Utils.enableWrapper(edgeTxtInptL)
            Utils.enableWrapper(thicknessTxtInptL)
        }
    }

    companion object {
        val TAG: String = Thickness::class.java.simpleName
        val instance = Thickness()
    }
}
