package parkhomov.andrew.lensthicknesscalculator.tabs

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.fragment.dialog.Result
import parkhomov.andrew.lensthicknesscalculator.fragment.glossary.GlossaryDetails
import parkhomov.andrew.lensthicknesscalculator.main.MainActivity
import parkhomov.andrew.lensthicknesscalculator.main.MyApp
import parkhomov.andrew.lensthicknesscalculator.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.utils.Utils

/**
 * Created by MyPC on 29.07.2017.
 */

class Thickness : AbstractTabFragment(), MainActivity.HideKeyboardI {

    @BindView(R.id.new_spinner)
    lateinit var spinner: Spinner
    @BindView(R.id.thicknessCalculateButton)
    lateinit var button: Button

    @BindView(R.id.sphereTxtInptL)
    lateinit var sphereWrapper: TextInputLayout
    @BindView(R.id.curveTxtInptL)
    lateinit var curveWrapper: TextInputLayout
    @BindView(R.id.thicknessTxtInptL)
    lateinit var centerThicknessWrapper: TextInputLayout
    @BindView(R.id.edgeTxtInptL)
    lateinit var edgeThicknessWrapper: TextInputLayout
    @BindView(R.id.diameterTxtInptL)
    lateinit var diameterWrapper: TextInputLayout

    @BindView(R.id.sphereET)
    lateinit var getSpherePower: TextInputEditText
    @BindView(R.id.cylinderET)
    lateinit var getCylinderPower: TextInputEditText
    @BindView(R.id.axisET)
    lateinit var getAxis: TextInputEditText
    @BindView(R.id.curveET)
    lateinit var getBaseCurve: TextInputEditText
    @BindView(R.id.centerThicknessET)
    lateinit var getCenterThickness: TextInputEditText
    @BindView(R.id.edgeThicknessET)
    lateinit var getEdgeThickness: TextInputEditText
    @BindView(R.id.diameterET)
    lateinit var getLensDiameter: TextInputEditText

    private var axis: Int = 0
    private var axisView: Int = 0
    private var lensIndex: Double = 0.toDouble()
    private var indexX: Double = 0.toDouble()
    private var spherePower: Double = 0.toDouble()
    private var edgeThickness: Double = 0.toDouble()
    private var realBackRadiusInMM: Double = 0.toDouble()
    private var cylinderPower: Double = 0.toDouble()
    private var lensDiameter: Double = 0.toDouble()
    private var centerThickness: Double = 0.toDouble()
    private var sag1Sphere: Double = 0.toDouble()
    private var sag2Sphere: Double = 0.toDouble()
    private var sag2Cylinder: Double = 0.toDouble()
    private var realBackCylinderRadiusInMM: Double = 0.toDouble()
    private var realFrontBaseCurveDptr: Double = 0.toDouble()
    private var realRadiusMM: Double = 0.toDouble()
    private var recalculatedCylinderCurve: Double = 0.toDouble()
    private var recalculatedSphereCurve: Double = 0.toDouble()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.thickness_fragment, container, false)
        ButterKnife.bind(this, view)
        activity = getActivity()

        button.text = Utils.spacing(getString(R.string.button_text_calculate), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8)

        customizeSpinner()
        setUpTextWatchers()
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

    private fun customizeSpinner() {
        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.index_of_refraction, R.layout.spinner_header)
        adapter.setDropDownViewResource(R.layout.spinner_body)
        spinner.adapter = adapter

    }

    private fun setUpTextWatchers() {
        getSpherePower.addTextChangedListener(GenericTextWatcher())
        getCylinderPower.addTextChangedListener(GenericTextWatcher())
    }

    @OnClick(R.id.indexImgB, R.id.sphereImgB, R.id.cylinderImgB, R.id.axisImgB, R.id.curveImgB, R.id.thicknessImgB, R.id.edgeImgB, R.id.diameterImgB)
    fun onQueryClicked(v: View) {
        var position = -1
        when (v.id) {
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

    @OnClick(R.id.thicknessCalculateButton)
    fun onCalculateBtnClicked() {
        clearData()
        when (spinner.selectedItemPosition) {
            0 -> {
                lensIndex = CONSTANT.INDEX_1498
                indexX = CONSTANT.INDEX_X_1498
            }
            1 -> {
                lensIndex = CONSTANT.INDEX_1560
                indexX = CONSTANT.INDEX_X_1560
            }
            2 -> {
                lensIndex = CONSTANT.INDEX_1530
                indexX = CONSTANT.INDEX_X_1530
            }
            3 -> {
                lensIndex = CONSTANT.INDEX_1590
                indexX = CONSTANT.INDEX_X_1590
            }
            4 -> {
                lensIndex = CONSTANT.INDEX_1610
                indexX = CONSTANT.INDEX_X_1610
            }
            5 -> {
                lensIndex = CONSTANT.INDEX_1670
                indexX = CONSTANT.INDEX_X_1670
            }
            6 -> {
                lensIndex = CONSTANT.INDEX_1740
                indexX = CONSTANT.INDEX_X_1740
            }
        }
        curveCalculation()
    }

    private val reaRadiusInMM: Double
        get() = (CONSTANT.LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000)

    private fun getEdgeThickness(): Double {
        return try {
            java.lang.Double.parseDouble(getEdgeThickness.text.toString())
        } catch (e: NumberFormatException) {
            0.0
        }

    }

    private fun getCylinderPower(): Double {
        return try {
            java.lang.Double.parseDouble(getCylinderPower.text.toString())
        } catch (e: NumberFormatException) {
            0.0
        }

    }

    private fun setCenterThickness() {
        // set center thickness
        val tempDoubleForThickness: Double
        if (spherePower <= 0 && cylinderPower == 0.0) {
            try {
                centerThickness = java.lang.Double.parseDouble(getCenterThickness.text.toString())
            } catch (e: NumberFormatException) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 1")
                Utils.highlightEditText(getCenterThickness, centerThicknessWrapper)
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
                        centerThickness = java.lang.Double.parseDouble(getCenterThickness.text.toString())
                    } catch (e: NumberFormatException) {
                        Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 2")
                        Utils.highlightEditText(getCenterThickness, centerThicknessWrapper)

                    }

                } else {
                    // fix thickness bug, when lens is plus, and center thickness field is not empty,
                    // when you need press calculate button twice, before you got correct CT value
                    centerThickness = Math.pow(lensDiameter / 2, 2.0) * (spherePower + cylinderPower) / (2000 * (lensIndex - 1)) + edgeThickness
                }
            } catch (e: NumberFormatException) {
                if (Math.abs(spherePower) > Math.abs(cylinderPower)) {
                    throw NumberFormatException()
                }
            }

        } else if (spherePower <= 0) {
            try {
                centerThickness = java.lang.Double.parseDouble(getCenterThickness.text.toString())
            } catch (e: NumberFormatException) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 3")
                Utils.highlightEditText(getCenterThickness, centerThicknessWrapper)

            }

        } else if (spherePower > 0) {
            // if cylinder > 0 we add sphere power and cylinder power
            tempDoubleForThickness = if (cylinderPower > 0) {
                spherePower + cylinderPower
            }else 
                spherePower
            
            // ROUGH Formula for calc CT with plano - concave lens, without pay attention
            // on front curve
            centerThickness = Math.pow(lensDiameter / 2, 2.0) * tempDoubleForThickness / (2000 * (lensIndex - 1)) + edgeThickness
        } else {
            throw NumberFormatException()
        }
    }

    private fun recalculatedFrontCurve(): Double = (lensIndex - 1) * 1000 / realRadiusMM

    private fun getAxis(): Int {
        var axis: Int
        try {
            axis = Integer.parseInt(getAxis.text.toString())
            if (axis < 0 || axis > 180) {
                throw NumberFormatException()
            }
        } catch (e: NumberFormatException) {
            if (getAxis.text.toString() == "") {
                axis = 0
            } else {
                Toast.makeText(getActivity(), resources.getText(R.string.tab_thkns_wrong_axis), Toast.LENGTH_LONG).show()
                getAxis.text = null
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
            if (axis + 90 > 180) {
                axis = Math.abs(180 - (axis + 90))
            } else if (axis > 90) {
                axis = 180 - axis
            } else if (axis <= 90) {
                axis = 180 - (axis + 90)
            }
        } else if (cylinderPower < 0) {
            if (axis > 90) axis = 180 - axis
        }
        return axis
    }

    private fun getRecalculatedCylinderCurve(recalculatedFrontCurve: Double):
            Double = (cylinderPower - (recalculatedFrontCurve / (1 - centerThickness / lensIndex / 1000.0 * recalculatedFrontCurve) - spherePower)) * indexX

    private val realCylinderBackRadiusInMM: Double
        get() = (CONSTANT.LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000)

    private fun getSag2Cylinder(): Double =
            Math.abs(realBackCylinderRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackCylinderRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0)) // sag of convex surface;

    private fun getSag2Sphere(): Double =
            Math.abs(realRadiusMM - Math.sqrt(Math.pow(realRadiusMM, 2.0) - Math.pow(lensDiameter / 2, 2.0)))    // sag of convex surface;

    private fun getRecalculatedSphereCurve(recalculatedFrontCurve: Double): Double =
            (spherePower - recalculatedFrontCurve / (1 - centerThickness / lensIndex / 1000.0 * recalculatedFrontCurve)) * indexX

    private fun getRealBackRadiusInMM(): Double =
            (CONSTANT.LAB_INDEX - 1) / (recalculatedSphereCurve / 1000)

    private fun getSag1Sphere(): Double =
            Math.abs(realBackRadiusInMM) - Math.sqrt(Math.pow(Math.abs(realBackRadiusInMM), 2.0) - Math.pow(lensDiameter / 2, 2.0))    // sag of concave surface;


    private fun setUpViewsBehaviourAfter() {
        Utils.enableWrapper(sphereWrapper)
        Utils.enableWrapper(curveWrapper)
        Utils.enableWrapper(centerThicknessWrapper)
        Utils.enableWrapper(edgeThicknessWrapper)
        Utils.enableWrapper(diameterWrapper)
    }

    private fun setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(getSpherePower, sphereWrapper)
        Utils.makeNormalEditText(getBaseCurve, curveWrapper)
        // if field is disable, we don't change it color
        if (getCenterThickness.currentHintTextColor != ContextCompat.getColor(activity, R.color.black) || 
                getCenterThickness.currentTextColor != ContextCompat.getColor(activity, R.color.black))
            Utils.makeNormalEditText(getCenterThickness, centerThicknessWrapper)
        if (getEdgeThickness.currentHintTextColor != ContextCompat.getColor(activity, R.color.black) || 
                getEdgeThickness.currentTextColor != ContextCompat.getColor(activity, R.color.black))
            Utils.makeNormalEditText(getEdgeThickness, edgeThicknessWrapper)
        Utils.makeNormalEditText(getLensDiameter, diameterWrapper)
        Utils.disableWrapper(sphereWrapper)
        Utils.disableWrapper(curveWrapper)
        Utils.disableWrapper(centerThicknessWrapper)
        Utils.disableWrapper(edgeThicknessWrapper)
        Utils.disableWrapper(diameterWrapper)
    }


    private fun curveCalculation() {
        /* this method make like 'event' when disable fields, and after calculation enable again,
        this allow to highlight required field that will be  highlighted in try blocks below*/
        setUpViewsBehaviourBefore()
        // try blocks here to highlight required field
        try {
            spherePower = java.lang.Double.parseDouble(getSpherePower.text.toString())
        } catch (e: NumberFormatException) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " spherePower")
            Utils.highlightEditText(getSpherePower, sphereWrapper)
        }

        try {
            realFrontBaseCurveDptr = java.lang.Double.parseDouble(getBaseCurve.text.toString())
        } catch (e: NumberFormatException) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " realFrontBaseCurveDptr")
            Utils.highlightEditText(getBaseCurve, curveWrapper)
        }

        try {
            lensDiameter = java.lang.Double.parseDouble(getLensDiameter.text.toString())
        } catch (e: NumberFormatException) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " lensDiameter")
            Utils.highlightEditText(getLensDiameter, diameterWrapper)
        }

        // Real radius of front curve in mm
        realRadiusMM = reaRadiusInMM

        edgeThickness = getEdgeThickness()

        cylinderPower = getCylinderPower()

        setCenterThickness()

        // Find D1
        val recalculatedFrontCurve = recalculatedFrontCurve()

        if (cylinderPower > 0 || cylinderPower < 0) {

            // check is axis valid, and get it
            axisView = getAxis()
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
                java.lang.Double.parseDouble(getSpherePower.text.toString())
            } catch (e: NumberFormatException) {
                0.0
            }

            try {
                cylinderPower = java.lang.Double.parseDouble(getCylinderPower.text.toString())
            } catch (e: NumberFormatException) {
                cylinderPower = 0.0
            }

            val value = if (cylinderPower > 0) spherePower + cylinderPower else spherePower

            Utils.disableWrapper(edgeThicknessWrapper)
            Utils.disableWrapper(centerThicknessWrapper)
            if (value > 0) {
                Utils.makeNormalEditText(getEdgeThickness, edgeThicknessWrapper)
                getEdgeThickness.isActivated = true
                getEdgeThickness.isEnabled = true
                getEdgeThickness.isFocusableInTouchMode = true
                Utils.disableThicknessField(getCenterThickness, centerThicknessWrapper)
                getCenterThickness.isActivated = false
                getCenterThickness.isEnabled = false
                getCenterThickness.isFocusableInTouchMode = false
                getCenterThickness.text = null
            } else {
                Utils.makeNormalEditText(getCenterThickness, centerThicknessWrapper)
                getCenterThickness.isActivated = true
                getCenterThickness.isEnabled = true
                getCenterThickness.isFocusableInTouchMode = true
                Utils.disableThicknessField(getEdgeThickness, edgeThicknessWrapper)
                getEdgeThickness.isActivated = false
                getEdgeThickness.isEnabled = false
                getEdgeThickness.isFocusableInTouchMode = false
                getEdgeThickness.text = null
            }
            Utils.enableWrapper(edgeThicknessWrapper)
            Utils.enableWrapper(centerThicknessWrapper)
        }
    }

    private fun hideSoftKeyboard() {
        Utils.inputManager.hideSoftInputFromWindow(getSpherePower.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getCylinderPower.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getAxis.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getBaseCurve.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getCenterThickness.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getEdgeThickness.windowToken, 0)
        Utils.inputManager.hideSoftInputFromWindow(getLensDiameter.windowToken, 0)
    }

    companion object {

        fun getInstance(headers: MutableList<String>, description: MutableList<String>, images: MutableList<Int>): Thickness {
            val bundle = Bundle()
            val thickness = Thickness()
            thickness.arguments = bundle
            Companion.setTitle(thickness, MyApp.getAppContext.getString(R.string.tab_lens_thickness))
            Companion.setHeaders(thickness, headers)
            Companion.setDescription(thickness, description)
            Companion.setImages(thickness, images)
            return thickness
        }


        private fun setTitle(thickness: Thickness, title: String) {
            thickness.title = title
        }

        private fun setHeaders(thickness: Thickness, headers: MutableList<String>) {
            thickness.headers = headers
        }

        private fun setDescription(thickness: Thickness, description: MutableList<String>) {
            thickness.description = description
        }

        private fun setImages(thickness: Thickness, images: MutableList<Int>) {
            thickness.images = images
        }

    }
}
