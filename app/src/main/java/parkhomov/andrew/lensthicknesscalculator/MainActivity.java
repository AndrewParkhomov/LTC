package parkhomov.andrew.lensthicknesscalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    String result;
    double spherePower;
    double lensDiameter;
    double lensIndex;
    double edgeThickness ;
    double centerThickness;
    double realBaseCurveinDptr;
    double realRadiusMM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonCalcClicked(View view){
        EditText getLensIndex = (EditText)findViewById(R.id.editTextLiensIndex);
        EditText getSpherePower = (EditText)findViewById(R.id.editTextSpherePower);
        EditText getBaseCurve = (EditText)findViewById(R.id.editTextBaseCurve);
        EditText getCenterThickness = (EditText)findViewById(R.id.editTextCenterThickness);
        EditText getLensDiameter = (EditText)findViewById(R.id.editTextLensDiameter);
        EditText getEdgeThickness = (EditText)findViewById(R.id.editTextEdgeThickness);

        TextView textViewResult = (TextView)findViewById(R.id.textViewResult);

        try {
            spherePower = Double.parseDouble(getSpherePower.getText().toString());
            lensDiameter = Double.parseDouble(getLensDiameter.getText().toString());
            lensIndex = Double.parseDouble(getLensIndex.getText().toString());
            realBaseCurveinDptr = Double.parseDouble(getBaseCurve.getText().toString());

            double LAB_INDEX = 1.53;
            realRadiusMM = (LAB_INDEX - 1) / (realBaseCurveinDptr / 1000);

            if (spherePower <= 0) {
                centerThickness = Double.parseDouble(getCenterThickness.getText().toString());
            } else {
                edgeThickness = Double.parseDouble(getEdgeThickness.getText().toString());
                centerThickness = 2.5;
                //centerThickness = (Math.pow(lensDiameter / 2, 2) * spherePower / (2000 * (lensIndex - 1))) + edgeThickness;
            }

            double recalcFrontCurve = (lensIndex - 1) * 1000 / realRadiusMM;
            double lensBackCurve = (spherePower - (recalcFrontCurve / (1 - centerThickness / lensIndex /
                    1000 * recalcFrontCurve))) * 1.065;

            double realBackRadiusInMM = Math.abs((LAB_INDEX - 1) / (lensBackCurve / 1000));

            double sagConvex = realRadiusMM - Math.sqrt((Math.pow(realRadiusMM, 2) -
                    Math.pow(lensDiameter / 2, 2))); // sag of convex surface;

            double sagConcave = (realBackRadiusInMM) - Math.sqrt((Math.pow(realBackRadiusInMM, 2) -
                                Math.pow(lensDiameter / 2, 2))); // sag of concave surface;
            double thickness;

            if (spherePower <= 0) {
                thickness = sagConvex - sagConcave;
                thickness = Math.abs(thickness)+centerThickness;
                result = String.format("Center thickness = %.2f\nEdge thickness = %.2f",
                        centerThickness, thickness);
            }
            if(spherePower > 0){
                if(spherePower > realBaseCurveinDptr){
                    thickness = sagConvex + sagConcave;
                    result = String.format("Center thickness = %.2f\nEdge thickness = %.2f",
                            thickness, edgeThickness);
                }else if(spherePower <= realBaseCurveinDptr){
                    thickness = sagConvex - sagConcave;
                    thickness += edgeThickness;
                    result = String.format("Center thickness = %.2f\nEdge thickness = %.2f",
                            thickness, edgeThickness);
                }
            }
            textViewResult.setText(result.replace(",", "."));
        }catch(Exception e){
            Toast.makeText(this,"Something wrong :(", Toast.LENGTH_SHORT).show();
            textViewResult.setText(null);
        }

    }
}
