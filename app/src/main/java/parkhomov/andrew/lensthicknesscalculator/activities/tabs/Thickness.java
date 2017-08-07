package parkhomov.andrew.lensthicknesscalculator.activities.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.dialog.Result;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MainActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MyApp;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;

/**
 * Created by MyPC on 29.07.2017.
 */

public class Thickness extends AbstractTabFragment implements
        MainActivity.HideKeyboardI {

    @BindView(R.id.new_spinner)
    Spinner spinner;
    @BindView(R.id.thicknessCalculateButton)
    Button button;

    @BindView(R.id.sphereTxtInptL)
    TextInputLayout sphereWrapper;
    @BindView(R.id.curveTxtInptL)
    TextInputLayout curveWrapper;
    @BindView(R.id.thicknessTxtInptL)
    TextInputLayout centerThicknessWrapper;
    @BindView(R.id.edgeTxtInptL)
    TextInputLayout edgeThicknessWrapper;
    @BindView(R.id.diameterTxtInptL)
    TextInputLayout diameterWrapper;

    @BindView(R.id.sphereET)
    EditText getSpherePower;
    @BindView(R.id.cylinderET)
    EditText getCylinderPower;
    @BindView(R.id.axisET)
    EditText getAxis;
    @BindView(R.id.curveET)
    EditText getBaseCurve;
    @BindView(R.id.centerThicknessET)
    EditText getCenterThickness;
    @BindView(R.id.edgeThicknessET)
    EditText getEdgeThickness;
    @BindView(R.id.diameterET)
    EditText getLensDiameter;

    private int axis, axisView;
    private double lensIndex, indexX, spherePower, edgeThickness, realBackRadiusInMM, cylinderPower, lensDiameter,
            centerThickness, sag1Sphere, sag2Sphere, sag2Cylinder, realBackCylinderRadiusInMM, realFrontBaseCurveDptr,
            realRadiusMM, recalculatedCylinderCurve, recalculatedSphereCurve;

    public static Thickness getInstance(List<String> headers, List<String> description, List<Integer> images) {
        Bundle bundle = new Bundle();
        Thickness thickness = new Thickness();
        thickness.setArguments(bundle);
        thickness.setTitle(MyApp.getAppContext().getString(R.string.tab_lens_thickness));
        thickness.setHeaders(headers);
        thickness.setDescription(description);
        thickness.setImages(images);
        return thickness;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thickness_fragment, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();

        button.setText(Utils.spacing(getString(R.string.button_text_calculate), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8));

        customizeSpinner();
        setUpTextWatchers();
        setUpListeners();
        return view;
    }

    private void setUpListeners() {
        ViewPager viewPager = ButterKnife.findById(activity, R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideSoftKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void hideKeyboard() {
        hideSoftKeyboard();
    }

    private void customizeSpinner() {
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                    R.array.index_of_refraction, R.layout.spinner_header);
            adapter.setDropDownViewResource(R.layout.spinner_body);
            spinner.setAdapter(adapter);
        }
    }

    private void setUpTextWatchers() {
        getSpherePower.addTextChangedListener(new GenericTextWatcher());
        getCylinderPower.addTextChangedListener(new GenericTextWatcher());
    }

    @OnClick({R.id.indexImgB,
            R.id.sphereImgB,
            R.id.cylinderImgB,
            R.id.axisImgB,
            R.id.curveImgB,
            R.id.thicknessImgB,
            R.id.edgeImgB,
            R.id.diameterImgB})
    public void onQueryClicked(View v) {
        int position = -1;
        switch (v.getId()) {
            case R.id.indexImgB:
                position = 0;
                break;
            case R.id.sphereImgB:
                position = 1;
                break;
            case R.id.cylinderImgB:
                position = 2;
                break;
            case R.id.axisImgB:
                position = 3;
                break;
            case R.id.curveImgB:
                position = 4;
                break;
            case R.id.thicknessImgB:
                position = 5;
                break;
            case R.id.edgeImgB:
                position = 6;
                break;
            case R.id.diameterImgB:
                position = 7;
                break;
        }

        if (position != -1) {
            try {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainContainerConstr,
                                GlossaryDetails.getInstance(
                                        headers.get(position),
                                        description.get(position),
                                        images.get(position)),
                                CONSTANT.GLOSSARY_DETAILS)
                        .commit();
            } catch (IllegalStateException e) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + "");
            }
        }
    }

    @OnClick(R.id.thicknessCalculateButton)
    public void onCalculateBtnClicked() {
        clearData();
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                lensIndex = CONSTANT.INDEX_1498;
                indexX = CONSTANT.INDEX_X_1498;
                break;
            case 1:
                lensIndex = CONSTANT.INDEX_1560;
                indexX = CONSTANT.INDEX_X_1560;
                break;
            case 2:
                lensIndex = CONSTANT.INDEX_1530;
                indexX = CONSTANT.INDEX_X_1530;
                break;
            case 3:
                lensIndex = CONSTANT.INDEX_1590;
                indexX = CONSTANT.INDEX_X_1590;
                break;
            case 4:
                lensIndex = CONSTANT.INDEX_1610;
                indexX = CONSTANT.INDEX_X_1610;
                break;
            case 5:
                lensIndex = CONSTANT.INDEX_1670;
                indexX = CONSTANT.INDEX_X_1670;
                break;
            case 6:
                lensIndex = CONSTANT.INDEX_1740;
                indexX = CONSTANT.INDEX_X_1740;
                break;
        }
        curveCalculation();
    }

    private double getReaRadiusInMM() {
        return (CONSTANT.LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000);
    }

    private double getEdgeThickness() {
        try {
            return Double.parseDouble(String.valueOf(getEdgeThickness.getText()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double getCylinderPower() {
        try {
            return Double.parseDouble(String.valueOf(getCylinderPower.getText()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void setCenterThickness() {
        // set center thickness
        double tempDoubleForThickness;
        if (spherePower <= 0 && cylinderPower == 0) {
            try {
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            } catch (NumberFormatException e) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 1");
                Utils.highlightEditText(getCenterThickness, centerThicknessWrapper);
            }
        } else if (spherePower <= 0 && cylinderPower > 0) {
            if (spherePower == 0) {
                // if sphere power == 0 we change formula, use cylinder power instead sphere power
                // for thickness calculation
                centerThickness = (Math.pow(lensDiameter / 2, 2) * cylinderPower /
                        (2000 * (lensIndex - 1))) + edgeThickness;
            }
            try {
                if (spherePower + cylinderPower < 0) {
                    try {
                        centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
                    } catch (NumberFormatException e) {
                        Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 2");
                        Utils.highlightEditText(getCenterThickness, centerThicknessWrapper);

                    }
                } else {
                    // fix thickness bug, when lens is plus, and center thickness field is not empty,
                    // when you need press calculate button twice, before you got correct CT value
                    centerThickness = (Math.pow(lensDiameter / 2, 2) * (spherePower + cylinderPower) /
                            (2000 * (lensIndex - 1))) + edgeThickness;
                }
            } catch (NumberFormatException e) {
                if (Math.abs(spherePower) > Math.abs(cylinderPower)) {
                    throw new NumberFormatException();
                }
            }
        } else if (spherePower <= 0) {
            try {
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            } catch (NumberFormatException e) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " centerThickness 3");
                Utils.highlightEditText(getCenterThickness, centerThicknessWrapper);

            }
        } else if (spherePower > 0) {
            // if cylinder > 0 we add sphere power and cylinder power
            if (cylinderPower > 0) {
                tempDoubleForThickness = spherePower + cylinderPower;
            } else {
                tempDoubleForThickness = spherePower;
            }
            // ROUGH Formula for calc CT with plano - concave lens, without pay attention
            // on front curve
            centerThickness = (Math.pow(lensDiameter / 2, 2) * tempDoubleForThickness /
                    (2000 * (lensIndex - 1))) + edgeThickness;
        } else {
            throw new NumberFormatException();
        }
    }

    private double recalculatedFrontCurve() {
        return (lensIndex - 1) * 1000 / realRadiusMM;
    }

    private int getAxis() {
        int axis;
        try {
            axis = Integer.parseInt(String.valueOf(getAxis.getText()));
            if (axis < 0 || axis > 180) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            if (String.valueOf(getAxis.getText()).equals("")) {
                axis = 0;
            } else {
                Toast.makeText(getActivity(), getResources().getText(R.string.tab_thkns_wrong_axis), Toast.LENGTH_LONG).show();
                getAxis.setText(null);
                axis = 0;
            }
        }
        return axis;
    }

    private int recalculateAxisInMinusCylinder(int inputAxis) {
        int axis = inputAxis;
        if (cylinderPower > 0) {
            spherePower += cylinderPower;
            cylinderPower = -cylinderPower;
            if (axis + 90 > 180) {
                axis = Math.abs(180 - (axis + 90));
            } else if (axis > 90) {
                axis = 180 - axis;
            } else if (axis <= 90) {
                axis = (180 - (axis + 90));
            }
        } else if (cylinderPower < 0) {
            if (axis > 90) axis = 180 - axis;
        }
        return axis;
    }

    private double getRecalculatedCylinderCurve(double recalculatedFrontCurve) {
        return (cylinderPower - (recalculatedFrontCurve /
                (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve) - spherePower)) * indexX;
    }

    private double getRealCylinderBackRadiusInMM() {
        return (CONSTANT.LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000);
    }

    private double getSag2Cylinder() {
        return Math.abs(realBackCylinderRadiusInMM) -
                Math.sqrt((Math.pow(Math.abs(realBackCylinderRadiusInMM), 2)
                        - Math.pow(lensDiameter / 2, 2))); // sag of convex surface;
    }

    private double getSag2Sphere() {
        return Math.abs(realRadiusMM - Math.sqrt((Math.pow(realRadiusMM, 2)
                - Math.pow(lensDiameter / 2, 2))));    // sag of convex surface;
    }

    private double getRecalculatedSphereCurve(double recalculatedFrontCurve) {
        return (spherePower - (recalculatedFrontCurve /
                (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve))) * indexX;
    }

    private double getRealBackRadiusInMM() {
        return (CONSTANT.LAB_INDEX - 1) / (recalculatedSphereCurve / 1000);
    }

    private double getSag1Sphere() {
        return Math.abs(realBackRadiusInMM) - Math.sqrt((Math.pow(Math.abs(realBackRadiusInMM), 2)
                - Math.pow(lensDiameter / 2, 2)));    // sag of concave surface;
    }


    private void setUpViewsBehaviourAfter() {
        Utils.enableWrapper(sphereWrapper);
        Utils.enableWrapper(curveWrapper);
        Utils.enableWrapper(centerThicknessWrapper);
        Utils.enableWrapper(edgeThicknessWrapper);
        Utils.enableWrapper(diameterWrapper);
    }

    private void setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(getSpherePower, sphereWrapper);
        Utils.makeNormalEditText(getBaseCurve, curveWrapper);
        Utils.makeNormalEditText(getCenterThickness, centerThicknessWrapper);
        Utils.makeNormalEditText(getEdgeThickness, edgeThicknessWrapper);
        Utils.makeNormalEditText(getLensDiameter, diameterWrapper);
        Utils.disableWrapper(sphereWrapper);
        Utils.disableWrapper(curveWrapper);
        Utils.disableWrapper(centerThicknessWrapper);
        Utils.disableWrapper(edgeThicknessWrapper);
        Utils.disableWrapper(diameterWrapper);
    }


    private void curveCalculation() {
        /* this method make like 'event' when disable fields, and after calculation enable again,
        this allow to highlight required field that will be  highlighted in try blocks below*/
        setUpViewsBehaviourBefore();
        // try blocks here to highlight required field
        try {
            spherePower = Double.parseDouble(String.valueOf(getSpherePower.getText()));
        } catch (NumberFormatException e) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " spherePower");
            Utils.highlightEditText(getSpherePower, sphereWrapper);
        }

        try {
            realFrontBaseCurveDptr = Double.parseDouble(String.valueOf(getBaseCurve.getText()));
        } catch (NumberFormatException e) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " realFrontBaseCurveDptr");
            Utils.highlightEditText(getBaseCurve, curveWrapper);
        }

        try {
            lensDiameter = Double.parseDouble(String.valueOf(getLensDiameter.getText()));
        } catch (NumberFormatException e) {
            Log.d(CONSTANT.MY_EXCEPTION, e.toString() + " lensDiameter");
            Utils.highlightEditText(getLensDiameter, diameterWrapper);
        }

        // Real radius of front curve in mm
        realRadiusMM = getReaRadiusInMM();

        edgeThickness = getEdgeThickness();

        cylinderPower = getCylinderPower();

        setCenterThickness();

        // Find D1
        double recalculatedFrontCurve = recalculatedFrontCurve();

        if (cylinderPower > 0 || cylinderPower < 0) {

            // check is axis valid, and get it
            axisView = getAxis();
            axis = recalculateAxisInMinusCylinder(axisView);
//                Log.d(CONSTANT.MY_EXCEPTION, axisView + " asixview");
//                Log.d(CONSTANT.MY_EXCEPTION, axis + " asix");

            recalculatedCylinderCurve = getRecalculatedCylinderCurve(recalculatedFrontCurve);

            realBackCylinderRadiusInMM = getRealCylinderBackRadiusInMM();

            sag2Cylinder = getSag2Cylinder();
        }
        sag2Sphere = getSag2Sphere();

        // Corrected back curve
        recalculatedSphereCurve = getRecalculatedSphereCurve(recalculatedFrontCurve);

        // Real radius of back curve in mm(we need exactly in mm for sag formula)
        realBackRadiusInMM = getRealBackRadiusInMM();

        sag1Sphere = getSag1Sphere();

        sphereThicknessCalculation();

        setUpViewsBehaviourAfter();
    }

    private void clearData() {
        lensIndex = 0;
        indexX = 0;
        spherePower = 0;
        edgeThickness = 0;
        realBackRadiusInMM = 0;
        cylinderPower = 0;
        lensDiameter = 0;
        centerThickness = 0;
        sag1Sphere = 0;
        sag2Sphere = 0;
        sag2Cylinder = 0;
        realBackCylinderRadiusInMM = 0;
        realFrontBaseCurveDptr = 0;
        realRadiusMM = 0;
        recalculatedCylinderCurve = 0;
        recalculatedSphereCurve = 0;
    }

    private void sphereThicknessCalculation() {
        if (lensDiameter == 0) return;
        if (realBackRadiusInMM <= 0) {
            if (spherePower <= 0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThickness;
            } else {
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness;
            }
        } else {
            centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness;
        }

        if (centerThickness != 0 && !Double.isNaN(centerThickness))
            if (cylinderPower > 0 || cylinderPower < 0) {
                cylinderCalculation();
            } else {
                Result result = Result.getInstance(
                        String.format("%.4s", String.valueOf(centerThickness)),
                        String.format("%.4s", String.valueOf(edgeThickness)));
                result.show(getFragmentManager(), "result");
            }
    }

    private void cylinderCalculation() {
        double maxEdgeThickness = 0;
        if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM < 0) {
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        } else if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM > 0) {
            maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness;
        } else if (spherePower >= realFrontBaseCurveDptr && realBackRadiusInMM < 0) {
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        } else if (spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM > 0) {
            maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness;
        } else if (spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM < 0) {
            maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness;
        }
        double etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness;

        if (cylinderPower != 0) {
            Result result = Result.getInstance(
                    String.format("%.4s", String.valueOf(centerThickness)),
                    String.format("%.4s", String.valueOf(edgeThickness)),
                    String.format("%.4s", String.valueOf(maxEdgeThickness)),
                    String.format("%.4s", String.valueOf(etOnCertainAxis)),
                    String.format("%.4s", String.valueOf(axisView)));
            result.show(getFragmentManager(), "result");
        } else {
            Result result = Result.getInstance(
                    String.format("%.4s", String.valueOf(centerThickness)),
                    String.format("%.4s", String.valueOf(edgeThickness)));
            result.show(getFragmentManager(), "result");
        }
    }

    //Declaration
    private class GenericTextWatcher implements TextWatcher {

        private GenericTextWatcher() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            disableField();
        }

        public void afterTextChanged(Editable editable) {

        }

        private void disableField() {
            try {
                spherePower = Double.parseDouble(getSpherePower.getText().toString());
            } catch (NumberFormatException e) {
                spherePower = 0;
            }
            try {
                cylinderPower = Double.parseDouble(getCylinderPower.getText().toString());
            } catch (NumberFormatException e) {
                cylinderPower = 0;
            }

            double value = cylinderPower > 0 ? spherePower + cylinderPower : spherePower;

            Utils.disableWrapper(edgeThicknessWrapper);
            Utils.disableWrapper(centerThicknessWrapper);
            if (value > 0) {
                Utils.makeNormalEditText(getEdgeThickness, edgeThicknessWrapper);
                getEdgeThickness.setActivated(true);
                getEdgeThickness.setEnabled(true);
                getEdgeThickness.setFocusableInTouchMode(true);
                Utils.disableThicknessField(getCenterThickness, centerThicknessWrapper);
                getCenterThickness.setActivated(false);
                getCenterThickness.setEnabled(false);
                getCenterThickness.setFocusableInTouchMode(false);
                getCenterThickness.setText(null);
            } else {
                Utils.makeNormalEditText(getCenterThickness, centerThicknessWrapper);
                getCenterThickness.setActivated(true);
                getCenterThickness.setEnabled(true);
                getCenterThickness.setFocusableInTouchMode(true);
                Utils.disableThicknessField(getEdgeThickness, edgeThicknessWrapper);
                getEdgeThickness.setActivated(false);
                getEdgeThickness.setEnabled(false);
                getEdgeThickness.setFocusableInTouchMode(false);
                getEdgeThickness.setText(null);
            }
            Utils.enableWrapper(edgeThicknessWrapper);
            Utils.enableWrapper(centerThicknessWrapper);
        }
    }

    private void hideSoftKeyboard() {
        Utils.getInputManager().hideSoftInputFromWindow(getSpherePower.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getCylinderPower.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getAxis.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getBaseCurve.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getCenterThickness.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getEdgeThickness.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(getLensDiameter.getWindowToken(), 0);
    }
}