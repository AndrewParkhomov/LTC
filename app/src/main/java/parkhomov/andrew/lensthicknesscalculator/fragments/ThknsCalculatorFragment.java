package parkhomov.andrew.lensthicknesscalculator.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.GlossaryActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.ThicknessResultActivity;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;


public class ThknsCalculatorFragment extends Fragment implements View.OnClickListener{

    View view;
    Intent intent;

    private EditText getCylinderPower;

    private int axis, axisView;

    private double lensIndex, indexX, spherePower, edgeThickness,realBackRadiusInMM,cylinderPower,
            centerThickness, sag1Sphere, sag2Sphere, sag2Cylinder, realBackCylinderRadiusInMM, realFrontBaseCurveDptr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_calculator, container, false);
        getCylinderPower = (EditText)view.findViewById(R.id.editTextCylinderPower);
        intent = new Intent(getActivity(), ThicknessResultActivity.class);
        setUpButtonsAndListeners();
        return view;
    }

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
        calculateButton.setOnClickListener(this);
        imageButtonLensIndex.setOnClickListener(this);
        imageButtonSpherePower.setOnClickListener(this);
        imageButtonCylinderPower.setOnClickListener(this);
        imageButtonAxis.setOnClickListener(this);
        imageButtonRealBaseCurve.setOnClickListener(this);
        imageButtonCenterThickness.setOnClickListener(this);
        imageButtonEdgeThickness.setOnClickListener(this);
        imageButtonLensDiameter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), GlossaryActivity.class);
        boolean isCalculationPressed = false;
        switch(v.getId()){
            case R.id.thicknessCalculateButton:
                isCalculationPressed = true;
                selectIndex();
                break;
            case R.id.imageButtonLensIndex:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 1);
                break;
            case R.id.imageButtonSpherePower:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 2);
                break;
            case R.id.imageButtonCylinderPower:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 3);
                break;
            case R.id.imageButtonAxis:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 4);
                break;
            case R.id.imageButtonRealBaseCurve:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 5);
                break;
            case R.id.imageButtonCenterThickness:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 6);
                break;
            case R.id.imageButtonEdgeThickness:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 7);
                break;
            case R.id.imageButtonLensDiameter:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 8);
                break;
        }
        if(!isCalculationPressed){
            startActivity(intent);
        }
    }

    private void curveCalculation() {
        EditText getSpherePower = (EditText)view.findViewById(R.id.editTextSpherePower);
        EditText getAxis = (EditText)view.findViewById(R.id.editTextAxis);
        EditText getBaseCurve = (EditText)view.findViewById(R.id.editTextBaseCurve);
        EditText getCenterThickness = (EditText)view.findViewById(R.id.editTextCenterThickness);
        EditText getLensDiameter = (EditText)view.findViewById(R.id.editTextLensDiameter);
        EditText getEdgeThickness = (EditText)view.findViewById(R.id.editTextEdgeThickness);

        double lensDiameter, realRadiusMM, tempDoubleForThickness, recalculatedCylinderCurve,
                recalculatedSphereCurve ;

        final double LAB_INDEX = 1.53;  // Constant index 1.53

        try {
            spherePower = Double.parseDouble(String.valueOf(getSpherePower.getText()));
            realFrontBaseCurveDptr = Double.parseDouble(String.valueOf(getBaseCurve.getText()));
            lensDiameter = Double.parseDouble(String.valueOf(getLensDiameter.getText()));

            // Real radius of front curve in mm
            realRadiusMM = (LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000);

            // if edge thickness field is empty, et = 0
            if(!getEdgeThickness.getText().toString().equals("")) {
                edgeThickness = Double.parseDouble(String.valueOf(getEdgeThickness.getText()));
            }else{
                edgeThickness = 0;
            }

            // get cylinder power
            try{
                cylinderPower = Double.parseDouble(String.valueOf(getCylinderPower.getText()));
            }catch(NumberFormatException e){}

            // set center thickness
            if(spherePower <= 0 && cylinderPower == 0) {
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            }else if(spherePower <= 0 && cylinderPower > 0){
                if(spherePower == 0){
                    // if sphere power == 0 we change formula, use cylinder power instead sphere power
                    // for thickness calculation
                    centerThickness = (Math.pow(lensDiameter / 2, 2) * cylinderPower /
                            (2000 * (lensIndex - 1))) + edgeThickness;
                }
                try{
                    if(spherePower + cylinderPower < 0){
                        centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
                    }else{
                        // fix thickness bug, when lens is plus, and center thickness field is not empty,
                        // when you need press calculate button twice, before you got correct CT value
                        centerThickness = (Math.pow(lensDiameter / 2, 2) * (spherePower + cylinderPower)/
                                (2000 * (lensIndex - 1))) + edgeThickness;
                    }
                }catch(NumberFormatException e){
                    if(Math.abs(spherePower) > Math.abs(cylinderPower)){
                        throw new Exception();
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
                throw new Exception();
            }

            // Find D1
            double recalculatedFrontCurve = (lensIndex - 1) * 1000 / realRadiusMM;

            if(!getCylinderPower.getText().toString().equals("")) {
                String getStringAxis = getAxis.getText().toString();
                if (getStringAxis.equals("")) {
                    axis = 0;
                } else {
                    try{
                        axis = Integer.valueOf(getStringAxis);
                        if (axis < 0 || axis > 180) {
                            throw new NumberFormatException();
                        }
                    }catch(NumberFormatException e){
                        Toast.makeText(getActivity(), getResources().getText(R.string.thkns_activ_wrong_axis),Toast.LENGTH_LONG).show();
                        getAxis.setText("");
                        axis = 0;
                    }
                    axisView = axis;
                }

                if (cylinderPower > 0) {
                    spherePower += cylinderPower;
                    cylinderPower = cylinderPower * -1;
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
        }catch(Exception e){
            Toast.makeText(getActivity(), getResources().getText(R.string.thkns_activ_wrong_data),Toast.LENGTH_LONG).show();
        }
    }

    private void selectIndex() {
        try{
            Spinner getLensIndex = (Spinner)view.findViewById(R.id.spinner);
            int getSpinnerNumber = getLensIndex.getSelectedItemPosition();
            switch(getSpinnerNumber) {
                case 0:
                    lensIndex = 1.498;
                    indexX = 1.0645;
                    break;
                case 1:
                    lensIndex = 1.535;
                    indexX = 0.99;
                    break;
                case 2:
                    lensIndex = 1.53;
                    indexX = 0.97;
                    break;
                case 3:
                    lensIndex = 1.586;
                    indexX = 0.92;
                    break;
                case 4:
                    lensIndex = 1.59;
                    indexX = 0.899;
                    break;
                case 5:
                    lensIndex = 1.66;
                    indexX = 0.803;
                    break;
                case 6:
                    lensIndex = 1.727;
                    indexX = 0.73;
                    break;
            }
            curveCalculation();
        }catch(Exception e){}
    }

    private void sphereThicknessCalculation() {
        //Toast.makeText(getActivity(), String.valueOf(realBackRadiusInMM), Toast.LENGTH_SHORT).show();
        if(realBackRadiusInMM <= 0){
            if (spherePower < 0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThickness;
            }else{
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness;
            }
        }else{
            centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness;
        }
        if(!getCylinderPower.getText().toString().equals("")) {
            cylinderCalculation();
        }else{
            intent.putExtra(ThicknessResultActivity.CALCULATION_CENTER_THICKNESS, centerThickness);
            intent.putExtra(ThicknessResultActivity.CALCULATION_EDGE_THICKNESS, edgeThickness);
            startActivity(intent);
        }
    }

    private void cylinderCalculation() {
        double maxEdgeThickness = 0;
        //Toast.makeText(getActivity(), "here 5", Toast.LENGTH_SHORT).show();
        if (spherePower < realFrontBaseCurveDptr && realBackRadiusInMM < 0){
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        }else if (spherePower < realFrontBaseCurveDptr && realBackRadiusInMM > 0){
            maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness;
        }else if(spherePower >= realFrontBaseCurveDptr && realBackRadiusInMM < 0){
            maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness;
        }else if(spherePower > realFrontBaseCurveDptr && realBackCylinderRadiusInMM > 0){
            maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness;
        }else if(spherePower > realFrontBaseCurveDptr && realBackCylinderRadiusInMM < 0) {
            maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness;
        }
        double etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness;

        if(cylinderPower != 0){
            ThicknessResultActivity.isCylinder = true;
            intent.putExtra(ThicknessResultActivity.CALCULATION_MAX_EDGE_THICKNESS, maxEdgeThickness);
            intent.putExtra(ThicknessResultActivity.CALCULATION_THICKNESS_ON_CERTAIN_AXIS, etOnCertainAxis);
            intent.putExtra(ThicknessResultActivity.CALCULATION_AXIS_VIEW, axisView);
        }
        intent.putExtra(ThicknessResultActivity.CALCULATION_CENTER_THICKNESS, centerThickness);
        intent.putExtra(ThicknessResultActivity.CALCULATION_EDGE_THICKNESS, edgeThickness);
        startActivity(intent);

    }
}
