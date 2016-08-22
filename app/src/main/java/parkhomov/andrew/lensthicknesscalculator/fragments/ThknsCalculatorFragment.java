package parkhomov.andrew.lensthicknesscalculator.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.GlossaryActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.MainActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.ThicknessResultActivity;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;

/**
 * Class with thickness calculation logic.
 */

public class ThknsCalculatorFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Intent intent;
    private TextView tv;
    private String centerThicknessResult, edgeThicknessResult, stringCenterThickness, stringEdgeThickness,
            onlySphereStringResult, formatForNumbers;
    private boolean isScreenLarge;
    private int axis, axisView;
    private double lensIndex, indexX, spherePower, edgeThickness,realBackRadiusInMM,cylinderPower,
            centerThickness, sag1Sphere, sag2Sphere, sag2Cylinder, realBackCylinderRadiusInMM, realFrontBaseCurveDptr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if((getResources().getConfiguration().screenLayout & UtilsDevice.getDisplaySize()) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & UtilsDevice.getDisplaySize()) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE){
            view = inflater.inflate(R.layout.activity_calculator_large, container, false);
            isScreenLarge = true;
        }else{
            view = inflater.inflate(R.layout.activity_calculator, container, false);
            intent = new Intent(getActivity(), ThicknessResultActivity.class);
            isScreenLarge = false;
        }
        setUpButtonsAndListeners();
        return view;
    }

    /**
     * Set up all buttons in fragment activity, create listeners, and manage 'calculate'
     * button behaviour.
     */
    private void setUpButtonsAndListeners() {
        Button calculateButton = (Button)view.findViewById(R.id.thicknessCalculateButton);
        ImageButton imageButtonLensIndex = (ImageButton) view.findViewById(R.id.imageButtonLensIndex);
        ImageButton imageButtonSpherePower = (ImageButton) view.findViewById(R.id.imageButtonSpherePower);
        ImageButton imageButtonCylinderPower = (ImageButton) view.findViewById(R.id.imageButtonCylinderPower);
        ImageButton imageButtonAxis = (ImageButton) view.findViewById(R.id.imageButtonAxis);
        ImageButton imageButtonRealBaseCurve = (ImageButton) view.findViewById(R.id.imageButtonRealBaseCurve);
        ImageButton imageButtonCenterThickness = (ImageButton) view.findViewById(R.id.imageButtonCenterThickness);
        ImageButton imageButtonEdgeThickness = (ImageButton) view.findViewById(R.id.imageButtonEdgeThickness);
        ImageButton imageButtonLensDiameter = (ImageButton) view.findViewById(R.id.imageButtonLensDiameter);
        calculateButton.setTransformationMethod(null);

        // manage 'calculate' button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex();
            }
        });

        imageButtonLensIndex.setOnClickListener(this);
        imageButtonSpherePower.setOnClickListener(this);
        imageButtonCylinderPower.setOnClickListener(this);
        imageButtonAxis.setOnClickListener(this);
        imageButtonRealBaseCurve.setOnClickListener(this);
        imageButtonCenterThickness.setOnClickListener(this);
        imageButtonEdgeThickness.setOnClickListener(this);
        imageButtonLensDiameter.setOnClickListener(this);
    }

    /**
     * Manage on query button pressed behaviour.
     */
    @Override
    public void onClick(View v) {
        // need for getting data from SQL
        int content = Integer.valueOf(String.valueOf(v.getContentDescription()));
        Intent intent = new Intent(getActivity(), GlossaryActivity.class);
        intent.putExtra(GlossaryActivity.QUERY_MARK_BUTON_ID, v.getId());
        intent.putExtra(GlossaryActivity.QUERY_MARK_ID_FOR_SQL, content);
        startActivity(intent);
    }

    /**
     * Main logic(get parameters, recalculate some of them, and create variables,
     * called sag1Sphere, sag2Sphere, sag2Cylinder).
     */
    private void curveCalculation() {
        EditText getSpherePower = (EditText)view.findViewById(R.id.editTextSpherePower);
        EditText getAxis = (EditText)view.findViewById(R.id.editTextAxis);
        EditText getBaseCurve = (EditText)view.findViewById(R.id.editTextBaseCurve);
        EditText getCenterThickness = (EditText)view.findViewById(R.id.editTextCenterThickness);
        EditText getLensDiameter = (EditText)view.findViewById(R.id.editTextLensDiameter);
        EditText getEdgeThickness = (EditText)view.findViewById(R.id.editTextEdgeThickness);
        EditText getCylinderPower = (EditText)view.findViewById(R.id.editTextCylinderPower);

        double lensDiameter, realRadiusMM, tempDoubleForThickness, recalculatedCylinderCurve,
                recalculatedSphereCurve ;
        final double LAB_INDEX = 1.53;  // Constant index 1.53

        try {
            spherePower = Double.parseDouble(String.valueOf(getSpherePower.getText()));
            realFrontBaseCurveDptr = Double.parseDouble(String.valueOf(getBaseCurve.getText()));
            lensDiameter = Double.parseDouble(String.valueOf(getLensDiameter.getText()));

            // Real radius of front curve in mm
            realRadiusMM = (LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000);
            
            try {
                edgeThickness = 0;
                edgeThickness = Double.parseDouble(String.valueOf(getEdgeThickness.getText()));
            }catch (NumberFormatException e){}

            // get cylinder power
            try {
                cylinderPower = 0;
                cylinderPower = Double.parseDouble(String.valueOf(getCylinderPower.getText()));
            }catch (NumberFormatException e) {}

            // set center thickness
            if (spherePower <= 0 && cylinderPower == 0) {
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            } else if (spherePower <= 0 && cylinderPower > 0) {
                if (spherePower == 0) {
                    // if sphere power == 0 we change formula, use cylinder power instead sphere power
                    // for thickness calculation
                    centerThickness = (Math.pow(lensDiameter / 2, 2) * cylinderPower /
                            (2000 * (lensIndex - 1))) + edgeThickness;
                }
                try {
                    if (spherePower + cylinderPower < 0) {
                        centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
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
            }else if(spherePower < 0){
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            }else if(spherePower >= 0){
                // if cylinder > 0 we add sphere power and cylinder power
                if(cylinderPower > 0){
                    tempDoubleForThickness = spherePower + cylinderPower;
                }else{
                    tempDoubleForThickness = spherePower;
                }
                // ROUGH Formula for calc CT with plano - concave lens, without pay attention
                // on front curve
                centerThickness = (Math.pow(lensDiameter / 2, 2) * tempDoubleForThickness /
                        (2000 * (lensIndex - 1))) + edgeThickness;
            }else{
                throw new NumberFormatException();
            }

            // Find D1
            double recalculatedFrontCurve = (lensIndex - 1) * 1000 / realRadiusMM;

            if(cylinderPower > 0 || cylinderPower < 0) {
                try{
                    axis = Integer.parseInt(String.valueOf(getAxis.getText()));
                    if (axis < 0 || axis > 180) {
                        throw new NumberFormatException();
                    }
                }catch(NumberFormatException e){
                    if(String.valueOf(getAxis.getText()).equals("")){
                        axis = 0;
                    }else{
                        Toast.makeText(getActivity(), getResources().getText(R.string.thkns_activ_wrong_axis), Toast.LENGTH_LONG).show();
                        getAxis.setText(null);
                        axis = 0;
                    }
                }
                axisView = axis;


                if (cylinderPower > 0) {
                    spherePower += cylinderPower;
                    cylinderPower = - cylinderPower;
                    if (axis + 90 > 180) {
                        axis = Math.abs(180 - (axis + 90));
                    } else if (axis > 90) {
                        axis = 180 - axis;
                    } else if (axis <= 90) {
                        axis = (180 - (axis + 90));
                    }
                }else if (cylinderPower < 0) {
                    if (axis > 90) axis = 180 - axis;
                }

                recalculatedCylinderCurve = (cylinderPower - (recalculatedFrontCurve /
                        (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve) - spherePower)) * indexX;

                realBackCylinderRadiusInMM = (LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000);

                sag2Cylinder = Math.abs(realBackCylinderRadiusInMM) -
                        Math.sqrt((Math.pow(Math.abs(realBackCylinderRadiusInMM), 2)
                                - Math.pow(lensDiameter / 2, 2))); // sag of convex surface;
            }
            sag2Sphere = Math.abs(realRadiusMM - Math.sqrt((Math.pow(realRadiusMM, 2)
                    - Math.pow(lensDiameter / 2, 2))));    // sag of convex surface;

            // Corrected back curve
            recalculatedSphereCurve = (spherePower - (recalculatedFrontCurve /
                    (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve))) * indexX;

            // Real radius of back curve in mm(we need exactly in mm for sag formula)
            realBackRadiusInMM = (LAB_INDEX - 1) / (recalculatedSphereCurve / 1000);

            sag1Sphere = Math.abs(realBackRadiusInMM) - Math.sqrt((Math.pow(Math.abs(realBackRadiusInMM), 2)
                    - Math.pow(lensDiameter / 2, 2)));    // sag of concave surface;

            sphereThicknessCalculation();
        }catch(NumberFormatException e){
            Toast.makeText(getActivity(), getResources().getText(R.string.thkns_activ_wrong_data),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set index of material which user was selected.
     */
    private void selectIndex() {
        final double INDEX_1498 = 1.498;
        final double INDEX_1560 = 1.535;
        final double INDEX_1530 = 1.53;
        final double INDEX_1590 = 1.586;
        final double INDEX_1610 = 1.59;
        final double INDEX_1670 = 1.66;
        final double INDEX_1740 = 1.727;
        final double INDEX_X_1498 = 1.06425;
        final double INDEX_X_1560 = 0.9909;
        final double INDEX_X_1530 = 0.97;
        final double INDEX_X_1590 = 0.9044;
        final double INDEX_X_1610 = 0.8983;
        final double INDEX_X_1670 = 0.803;
        final double INDEX_X_1740 = 0.729;

        Spinner getLensIndex = (Spinner)view.findViewById(R.id.spinner);
        int getSpinnerNumber = getLensIndex.getSelectedItemPosition();
        switch(getSpinnerNumber) {
            case 0:
                lensIndex = INDEX_1498;
                indexX = INDEX_X_1498;
                break;
            case 1:
                lensIndex = INDEX_1560;
                indexX = INDEX_X_1560;
                break;
            case 2:
                lensIndex = INDEX_1530;
                indexX = INDEX_X_1530;
                break;
            case 3:
                lensIndex = INDEX_1590;
                indexX = INDEX_X_1590;
                break;
            case 4:
                lensIndex = INDEX_1610;
                indexX = INDEX_X_1610;
                break;
            case 5:
                lensIndex = INDEX_1670;
                indexX = INDEX_X_1670;
                break;
            case 6:
                lensIndex = INDEX_1740;
                indexX = INDEX_X_1740;
                break;
        }
        curveCalculation();
    }

    /**
     * This method calculate stigmatic thickness, if lens is astigmatic, also
     * {@link #cylinderCalculation} is called.
     * Method display results(for large and xlarge screen) or send data to
     * {@link ThicknessResultActivity} class.
     */
    private void sphereThicknessCalculation() {
        if(realBackRadiusInMM <= 0){
            if (spherePower <= 0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThickness;
            }else{
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness;
            }
        }else{
            centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness;
        }
        // %.2f format for number(make them shorter)
        formatForNumbers = getResources().getString(R.string.for_short_number_string_format);

        if(isScreenLarge){
            tv = (TextView)getActivity().findViewById(R.id.thicknessCalculateTextViewLarge);
            stringCenterThickness = getResources().getString(R.string.thkns_activ_textview_center_thickness);
            stringEdgeThickness = getResources().getString(R.string.thkns_activ_textview_edge_thickness);
            centerThicknessResult = String.format(Locale.ENGLISH, formatForNumbers, centerThickness);
            edgeThicknessResult = String.format(Locale.ENGLISH, formatForNumbers, edgeThickness);
            onlySphereStringResult = stringCenterThickness + centerThicknessResult +"\n"+
                    stringEdgeThickness + edgeThicknessResult + "\n";
            if(cylinderPower != 0) {
                cylinderCalculation();
            }else{
                tv.setText(onlySphereStringResult);
            }
        }else if(cylinderPower > 0 || cylinderPower < 0) {
            cylinderCalculation();
        }else{
            intent.putExtra(ThicknessResultActivity.CALCULATION_CENTER_THICKNESS, centerThickness);
            intent.putExtra(ThicknessResultActivity.CALCULATION_EDGE_THICKNESS, edgeThickness);
            startActivity(intent);
        }
    }

    /**
     * Method calculate maximum edge thickness for astigmatic lenses. If screen is not large
     * or xlarge, method send data to {@link ThicknessResultActivity} class, otherwise show results.
     */
    private void cylinderCalculation() {
        double maxEdgeThickness = 0;
        if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM < 0){
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        }else if (spherePower <= realFrontBaseCurveDptr && realBackRadiusInMM > 0){
            maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness;
        }else if(spherePower >= realFrontBaseCurveDptr && realBackRadiusInMM < 0){
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        }else if(spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM > 0){
            maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness;
        }else if(spherePower >= realFrontBaseCurveDptr && realBackCylinderRadiusInMM < 0) {
            maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness;
        }
        double etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness;

        if(isScreenLarge){
            if(cylinderPower != 0){
                String stringMaxET = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness);
                String stringCertainET = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness);
                String stringCertainETSecond = getResources().getString(R.string.thkns_activ_textview_certain_second_half);

                String edgeThicknessMaxET = String.format(Locale.ENGLISH,formatForNumbers,maxEdgeThickness);
                String edgeThicknessCertainAxis = String.format(Locale.ENGLISH,formatForNumbers,etOnCertainAxis);
                String finalResult = stringCenterThickness + centerThicknessResult +"\n"+
                        stringEdgeThickness + edgeThicknessResult + "\n" + stringMaxET +
                        edgeThicknessMaxET + "\n" + stringCertainET + axisView + stringCertainETSecond +
                        edgeThicknessCertainAxis;
                tv.setText(finalResult);
            }else{
                tv.setText(onlySphereStringResult);
            }
            // this check needs if user set cylinder 0 and we have ugly result view
        }else if (cylinderPower != 0) {
            ThicknessResultActivity.isCylinder = true;
            intent.putExtra(ThicknessResultActivity.CALCULATION_MAX_EDGE_THICKNESS, maxEdgeThickness);
            intent.putExtra(ThicknessResultActivity.CALCULATION_THICKNESS_ON_CERTAIN_AXIS, etOnCertainAxis);
            intent.putExtra(ThicknessResultActivity.CALCULATION_AXIS_VIEW, axisView);
        }
        // if cylinder == 0 put into intent just edge thickness and center thickness,
        // otherwise put all parameters
        if(!isScreenLarge){
            intent.putExtra(ThicknessResultActivity.CALCULATION_CENTER_THICKNESS, centerThickness);
            intent.putExtra(ThicknessResultActivity.CALCULATION_EDGE_THICKNESS, edgeThickness);
            startActivity(intent);
        }
    }
}
