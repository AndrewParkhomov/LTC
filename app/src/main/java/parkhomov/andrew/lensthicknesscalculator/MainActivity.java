package parkhomov.andrew.lensthicknesscalculator;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private EditText getCylinderPower;
    private String result;
    private int axis;
    private int axisView;
    private double lensIndex;
    private double indexX;
    private double spherePower;
    private double edgeThickness;
    private double centerThickness;
    private double realFrontBaseCurveDptr;
    private double sag1Sphere;
    private double sag2Sphere;
    private double sag2Cylinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = (TextView)findViewById(R.id.textViewResult);
        getCylinderPower = (EditText)findViewById(R.id.editTextCylinderPower);
    }

    public void onButtonCalcClicked(View view) throws IllegalArgumentException{
        resetValues();
        selectIndex();
        curveCalculation();
        sphereThicknessCalculation();
    }

    private void resetValues() {
        lensIndex = 0;
        indexX = 0;
        spherePower = 0;
        axis = 0;
        axisView = 0;
        edgeThickness = 0;
        centerThickness = 0;
        realFrontBaseCurveDptr = 0;
        sag1Sphere = 0;
        sag2Sphere = 0;
        sag2Cylinder = 0;
    }

    private void curveCalculation() throws IllegalArgumentException{
        EditText getSpherePower = (EditText)findViewById(R.id.editTextSpherePower);
        EditText getAxis = (EditText)findViewById(R.id.editTextAxis);
        EditText getBaseCurve = (EditText)findViewById(R.id.editTextBaseCurve);
        EditText getCenterThickness = (EditText)findViewById(R.id.editTextCenterThickness);
        EditText getLensDiameter = (EditText)findViewById(R.id.editTextLensDiameter);
        EditText getEdgeThickness = (EditText)findViewById(R.id.editTextEdgeThickness);

        double lensDiameter;
        double realRadiusMM;
        final double LAB_INDEX = 1.53;  // Constant index 1.53

        try {
            spherePower = Double.parseDouble(String.valueOf(getSpherePower.getText()));
            realFrontBaseCurveDptr = Double.parseDouble(String.valueOf(getBaseCurve.getText()));
            lensDiameter = Double.parseDouble(String.valueOf(getLensDiameter.getText()));

            // Real radius of front curve in mm
            realRadiusMM = (LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000);
            // Find D1
            double recalculatedFrontCurve = (lensIndex - 1) * 1000 / realRadiusMM;

            if(!getCylinderPower.getText().toString().equals("")){
                String getStringAxis = getAxis.getText().toString();
                if(getStringAxis.equals("")){
                   axis = 0;
                }else{
                    axis = Integer.valueOf(getStringAxis);
                    if(axis < 0 || axis > 180) {
                        try {
                            throw new IllegalArgumentException();
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(this, getResources().getText(R.string.wrong_axis),
                                                                  Toast.LENGTH_SHORT).show();
                            getAxis.setText("");
                            axis = 0;
                        }
                    }
                    axisView = axis;
                }
                double cylinderPower = Double.parseDouble(String.valueOf(getCylinderPower.getText()));
                if(cylinderPower > 0) {
                    spherePower += cylinderPower;
                    cylinderPower = cylinderPower * -1;
                    if (axis + 90 > 180) {
                        axis = Math.abs(180 - (axis + 90));
                    } else if (axis > 90) {
                        axis = 180 - axis;
                    }
                }else if(cylinderPower < 0){
                    if(axis > 90) axis = 180 - axis;
                }
                double recalculatedCylinderCurve = ((cylinderPower - (recalculatedFrontCurve /
                        (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve)-spherePower)) * indexX);
                double realBackCylinderRadiusInMM = Math.abs((LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000));
                sag2Cylinder = realBackCylinderRadiusInMM -
                        Math.sqrt((Math.pow(realBackCylinderRadiusInMM, 2)
                                - Math.pow(lensDiameter / 2, 2))); // sag of convex surface;
            }

            sag2Sphere = realRadiusMM - Math.sqrt((Math.pow(realRadiusMM, 2)
                    - Math.pow(lensDiameter / 2, 2)));    // sag of convex surface;

            // if edge thickness field is empty, et = 0
            if(!getEdgeThickness.getText().toString().equals("")) {
                edgeThickness = Double.parseDouble(String.valueOf(getEdgeThickness.getText()));
            }else{
                edgeThickness = 0;
            }

            if(spherePower <= 0){
                // if lens is 0 or minus
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            }else{
                // ROUGH Formula for calc CT with plano - concave lens, without pay attention
                // on front curve
                centerThickness = (Math.pow(lensDiameter / 2, 2) * spherePower /
                                  (2000 * (lensIndex - 1))) + edgeThickness;
            }


            // Corrected back curve
            double recalculatedSphereCurve = (spherePower - (recalculatedFrontCurve /
                    (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve))) * indexX;

            // Real radius of back curve in mm(we need exactly in mm for sag formula)
            double realBackRadiusInMM = Math.abs((LAB_INDEX - 1) / (recalculatedSphereCurve / 1000));
            sag1Sphere = realBackRadiusInMM - Math.sqrt((Math.pow(realBackRadiusInMM, 2)
                            - Math.pow(lensDiameter / 2, 2)));    // sag of concave surface;
        }catch(Exception e){
            textViewResult.setText(null);
            Toast.makeText(this, getResources().getText(R.string.wrong_base_curve),
                                                        Toast.LENGTH_LONG).show();
        }
    }

    private void selectIndex() {
        try{
            Spinner getLensIndex = (Spinner)findViewById(R.id.spinner);
            int getSpinnerNumber = getLensIndex.getSelectedItemPosition();
            switch(getSpinnerNumber) {
                case 0:
                    lensIndex = 1.498;
                    indexX = 1.065;
                    break;
                case 1:
                    lensIndex = 1.535;
                    indexX = 0.99;
                    break;
                case 2:
                    lensIndex = 1.532;
                    indexX = 0.97;
                    break;
                case 3:
                    lensIndex = 1.586;
                    indexX = 0.92;
                    break;
                case 4:
                    lensIndex = 1.59;
                    indexX = 0.898;
                    break;
                case 5:
                    lensIndex = 1.66;
                    indexX = 0.803;
                    break;
                case 6:
                    lensIndex = 1.727;
                    indexX = 0.7235;
                    break;
                default:
                    break;
            }
        }catch(Exception e){
            Toast.makeText(this,"Something wrong in selectIndex ", Toast.LENGTH_SHORT).show();
            textViewResult.setText(null);
        }
    }

    private void sphereThicknessCalculation() {
        String stringCenterThickness = getResources().getString(R.string.view_center_thickness);
        String stringEdgeThickness = getResources().getString(R.string.view_edge_thickness);

        if (spherePower <= 0) {
            edgeThickness = Math.abs(sag1Sphere - sag2Sphere)+ centerThickness;
            result = String.format(stringCenterThickness + stringEdgeThickness,
                    centerThickness, edgeThickness);
        }
        else{
            if(spherePower > realFrontBaseCurveDptr){
                centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness;
                result = String.format(stringCenterThickness + stringEdgeThickness,
                        centerThickness, edgeThickness);
            }else if(spherePower <= realFrontBaseCurveDptr){
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness;
                result = String.format(stringCenterThickness + stringEdgeThickness,
                        centerThickness, edgeThickness);
            }
        }
        if(!getCylinderPower.getText().toString().equals("")) {
            cylinderCalculation();
        }else{
            textViewResult.setText(result.replace(",", "."));
        }
    }

    private void cylinderCalculation(){
        double maxEdgeThickness;
        double etOnCertainAxis;
        String stringCenterThickness = getResources().getString(R.string.view_center_thickness);
        String stringEdgeThickness = getResources().getString(R.string.view_edge_thickness);
        String stringMaxET = getResources().getString(R.string.view_max_edge_thickness);
        String stringCertainET = getResources().getString(R.string.view_certain_edge_thickness);
        String stringCertainETSecond = getResources().getString(R.string.view_certain_second_half);

        if (spherePower <= 0) {
            maxEdgeThickness = Math.abs(sag1Sphere - sag2Cylinder) + centerThickness;
            etOnCertainAxis = (maxEdgeThickness - edgeThickness)/90*axis+edgeThickness;
            result = String.format(stringCenterThickness+stringEdgeThickness+stringMaxET+
                    stringCertainET+axisView+stringCertainETSecond,
                    centerThickness, edgeThickness, maxEdgeThickness,etOnCertainAxis);
        }
        else{
            if (spherePower > realFrontBaseCurveDptr) {
                maxEdgeThickness = Math.abs(sag1Sphere + sag2Cylinder) + edgeThickness;
                etOnCertainAxis = (maxEdgeThickness - edgeThickness)/90*axis+edgeThickness;
                result = String.format(stringCenterThickness+stringEdgeThickness+stringMaxET+
                                stringCertainET+axisView+stringCertainETSecond,
                        centerThickness, edgeThickness, maxEdgeThickness,etOnCertainAxis);
            }else if (spherePower <= realFrontBaseCurveDptr) {
                maxEdgeThickness = Math.abs(sag1Sphere - sag2Cylinder) + edgeThickness;
                etOnCertainAxis = (maxEdgeThickness - edgeThickness)/90*axis+edgeThickness;
                result = String.format(stringCenterThickness+stringEdgeThickness+stringMaxET+
                                stringCertainET+axisView+stringCertainETSecond,
                        centerThickness, edgeThickness, maxEdgeThickness,etOnCertainAxis);
            }
        }
        textViewResult.setText(result.replace(",", "."));
    }
}
