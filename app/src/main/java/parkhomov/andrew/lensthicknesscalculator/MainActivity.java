package parkhomov.andrew.lensthicknesscalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private EditText getCylinderPower;
    private String result;
    String s;
    private double lensIndex;
    private double indexX;
    private double spherePower;
    private double cylinderPower;
    private int axis;
    private double edgeThickness;
    private double maxEdgeThickness;
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

    public void onButtonCalcClicked(View view){
        selectIndex();
        curveCalculation();
        sphereThicknessCalculation();
    }

    private void curveCalculation() {
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
            edgeThickness = Double.parseDouble(String.valueOf(getEdgeThickness.getText()));

            // Real radius of front curve in mm
            realRadiusMM = (LAB_INDEX - 1) / (realFrontBaseCurveDptr / 1000);


            if(spherePower <= 0){
                // if lens is 0 or minus
                centerThickness = Double.parseDouble(String.valueOf(getCenterThickness.getText()));
            }else{
                // ROUGH Formula for calc CT with plano - concave lens, without pay attention
                // on front curve
                centerThickness = (Math.pow(lensDiameter / 2, 2) * spherePower /
                                  (2000 * (lensIndex - 1))) + edgeThickness;
            }

            // Find D1
            double recalculatedFrontCurve = (lensIndex - 1) * 1000 / realRadiusMM;
            // Corrected back curve
            double recalculatedSphereCurve = (spherePower - (recalculatedFrontCurve /
                    (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve))) * indexX;
            if(!getCylinderPower.getText().toString().equals("")){
                cylinderPower = Double.parseDouble(String.valueOf(getCylinderPower.getText()));
                //axis = Integer.getInteger(getAxis.toString());

                double recalculatedCylinderCurve = ((cylinderPower - (recalculatedFrontCurve /
                        (1 - centerThickness / lensIndex / 1000 * recalculatedFrontCurve)-spherePower)) * indexX);
                double realBackCylinderRadiusInMM = Math.abs((LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000));

                sag2Cylinder = realBackCylinderRadiusInMM -
                               Math.sqrt((Math.pow(realBackCylinderRadiusInMM, 2)
                                       - Math.pow(lensDiameter / 2, 2))); // sag of convex surface;
            }else{
                sag2Sphere = realRadiusMM - Math.sqrt((Math.pow(realRadiusMM, 2)
                        - Math.pow(lensDiameter / 2, 2)));    // sag of convex surface;
            }
            // Real radius of back curve in mm(we need exactly in mm for sag formula)
            double realBackRadiusInMM = Math.abs((LAB_INDEX - 1) / (recalculatedSphereCurve / 1000));
            sag1Sphere = realBackRadiusInMM - Math.sqrt((Math.pow(realBackRadiusInMM, 2)
                            - Math.pow(lensDiameter / 2, 2)));    // sag of concave surface;
        }catch(Exception e){
            Toast.makeText(this,"Something wrong in curveCalculation", Toast.LENGTH_SHORT).show();
            textViewResult.setText(null);
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
                    lensIndex = 1.555;
                    indexX = 0.97;
                    break;
                case 3:
                    lensIndex = 1.58;
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
                    lensIndex = 1.73;
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
        try{
            if (spherePower <= 0) {
                edgeThickness = Math.abs(sag1Sphere - sag2Sphere)+ centerThickness;
                result = String.format("Center thickness = %.2f mm\n" +
                                "Edge thickness = %.2f mm",
                        centerThickness, edgeThickness);
            }
            else{
                if(spherePower > realFrontBaseCurveDptr){
                    centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness;
                    result = String.format("Center thickness = %.2f mm\n" +
                                    "Edge thickness = %.2f mm",
                            centerThickness, edgeThickness);
                }else if(spherePower <= realFrontBaseCurveDptr){
                    centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness;
                    result = String.format("Center thickness = %.2f mm\n" +
                                    "Edge thickness = %.2f mm",
                            centerThickness, edgeThickness);
                }
            }
            if(!getCylinderPower.getText().toString().equals("")) {
                cylinderCalculation();
            }else{
                textViewResult.setText(result.replace(",", "."));
            }
        }catch (Exception e){
            Toast.makeText(this, "Something wrong in sphereThicknessCalculation", Toast.LENGTH_SHORT).show();
        }
    }

    private void cylinderCalculation(){
        try{
            if (spherePower <= 0) {
                maxEdgeThickness = Math.abs(sag1Sphere - sag2Cylinder) + centerThickness;
                result = String.format("Center thickness = %.2f mm\n" +
                                "Edge thickness = %.2f mm\n" +
                                "Max edge thickness = %.2f mm",
                        centerThickness, edgeThickness, maxEdgeThickness);
            }
            else{
                if (spherePower > realFrontBaseCurveDptr) {
                    maxEdgeThickness = Math.abs(sag1Sphere + sag2Cylinder) + edgeThickness; //???????
                    result = String.format("Center thickness = %.2f mm\n" +
                                    "Edge thickness = %.2f mm\n" +
                                    "Max edge thickness = %.2f mm",
                            centerThickness, edgeThickness, maxEdgeThickness);
                }else if (spherePower <= realFrontBaseCurveDptr) {
                    maxEdgeThickness = Math.abs(sag1Sphere - sag2Cylinder) + edgeThickness;
                    result = String.format("Center thickness = %.2f mm\n" +
                                    "Edge thickness = %.2f mm\n" +
                                    "Max edge thickness = %.2f mm",
                            centerThickness, edgeThickness, maxEdgeThickness);
                }
            }
            textViewResult.setText(result.replace(",", "."));
        }catch(Exception e){
            Toast.makeText(this, "Something wrong in cylinderThicknessCalculation", Toast.LENGTH_SHORT).show();
        }
    }
}
