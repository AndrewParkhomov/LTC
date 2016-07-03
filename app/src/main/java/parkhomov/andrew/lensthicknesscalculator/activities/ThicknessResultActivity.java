package parkhomov.andrew.lensthicknesscalculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class ThicknessResultActivity extends AppCompatActivity {

    public static final String CALCULATION_CENTER_THICKNESS = "CENTER";
    public static final String CALCULATION_EDGE_THICKNESS = "EDGE";
    public static final String CALCULATION_MAX_EDGE_THICKNESS = "MAX_EDGE";
    public static final String CALCULATION_THICKNESS_ON_CERTAIN_AXIS= "ON_CERTAIN_AXIS";
    public static final String CALCULATION_AXIS_VIEW= "AXIS";

    public static boolean isCylinder = false;

    double centerThickness, edgeThickness, maxEdgeThickness, etOnCertainAxis;
    int axisView;
    String resultTextField, resultNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thickness_result);
        TextView textViewText = (TextView)findViewById(R.id.textViewText);
        TextView textViewNumber = (TextView)findViewById(R.id.textViewResult);

        // get text "static" string fields
        String stringCenterThickness = getResources().getString(R.string.thkns_activ_textview_center_thickness);
        String stringEdgeThickness = getResources().getString(R.string.thkns_activ_textview_edge_thickness);
        String stringMaxET = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness);
        String stringCertainET = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness);
        String stringCertainETSecond = getResources().getString(R.string.thkns_activ_textview_certain_second_half);

        // get strings for numbers format with 2 symbols before dot
        String stringCenterThicknessNumbers = getResources().getString(R.string.thkns_activ_textview_center_thickness_numbers_format);
        String stringEdgeThicknessNumbers = getResources().getString(R.string.thkns_activ_textview_edge_thickness_numbers_format);
        String stringMaxETNumbers = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness_numbers_format);
        String stringCertainETNumbers = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness_numbers_format);

        // if lens is astigmatic - get all parameters, if don't - get center and edge thickness
        if(isCylinder){
            centerThickness = getIntent().getExtras().getDouble(CALCULATION_CENTER_THICKNESS);
            edgeThickness = getIntent().getExtras().getDouble(CALCULATION_EDGE_THICKNESS);
            maxEdgeThickness = getIntent().getExtras().getDouble(CALCULATION_MAX_EDGE_THICKNESS);
            etOnCertainAxis = getIntent().getExtras().getDouble(CALCULATION_THICKNESS_ON_CERTAIN_AXIS);
            axisView = getIntent().getExtras().getInt(CALCULATION_AXIS_VIEW);
            resultTextField = stringCenterThickness + stringEdgeThickness + stringMaxET +
                    stringCertainET + axisView + stringCertainETSecond;
            resultNumberField = String.format(stringCenterThicknessNumbers + stringEdgeThicknessNumbers +
                            stringMaxETNumbers + stringCertainETNumbers,
                    centerThickness, edgeThickness, maxEdgeThickness, etOnCertainAxis);
            isCylinder = false;
        }else{
            centerThickness = getIntent().getExtras().getDouble(CALCULATION_CENTER_THICKNESS);
            edgeThickness = getIntent().getExtras().getDouble(CALCULATION_EDGE_THICKNESS);
            resultTextField = stringCenterThickness + stringEdgeThickness;
            resultNumberField = String.format(stringCenterThicknessNumbers + stringEdgeThicknessNumbers,
                    centerThickness, edgeThickness);
        }

        // set separately text and numbers in textView, and replace comma
        if(textViewText != null && textViewNumber != null){
            textViewText.setText(resultTextField);
            textViewNumber.setText(resultNumberField.replace(",", "."));
        }
    }
}
