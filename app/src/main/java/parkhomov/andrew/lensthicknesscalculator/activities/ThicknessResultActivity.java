package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;

public class ThicknessResultActivity extends ListActivity {

    public static final String CALCULATION_CENTER_THICKNESS = "CENTER";
    public static final String CALCULATION_EDGE_THICKNESS = "EDGE";
    public static final String CALCULATION_MAX_EDGE_THICKNESS = "MAX_EDGE";
    public static final String CALCULATION_THICKNESS_ON_CERTAIN_AXIS= "ON_CERTAIN_AXIS";
    public static final String CALCULATION_AXIS_VIEW= "AXIS";

    public static boolean isCylinder;

    double centerThickness, edgeThickness, maxEdgeThickness, etOnCertainAxis;
    int axisView;
    String stringCenterThickness, stringEdgeThickness, stringCenterThicknessNumbers,
            stringEdgeThicknessNumbers;
    ListView listView;
    ArrayList<String> listWithResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        listWithResult = new ArrayList<>();
        centerThickness = getIntent().getExtras().getDouble(CALCULATION_CENTER_THICKNESS);
        edgeThickness = getIntent().getExtras().getDouble(CALCULATION_EDGE_THICKNESS);
        stringCenterThickness = getResources().getString(R.string.thkns_activ_textview_center_thickness);
        stringEdgeThickness = getResources().getString(R.string.thkns_activ_textview_edge_thickness);
        stringCenterThicknessNumbers = getResources().getString(R.string.thkns_activ_textview_center_thickness_numbers_format);
        stringEdgeThicknessNumbers = getResources().getString(R.string.thkns_activ_textview_edge_thickness_numbers_format);
        setListViewParameters();
        addDataInListView();
    }

    private void addDataInListView() {

        if(isCylinder){
            // get text "static" string fields
            String stringMaxET = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness);
            String stringCertainET = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness);
            String stringCertainETSecond = getResources().getString(R.string.thkns_activ_textview_certain_second_half);

            // get strings for numbers format with 2 symbols before dot
            String stringMaxETNumbers = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness_numbers_format);
            String stringCertainETNumbers = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness_numbers_format);

            maxEdgeThickness = getIntent().getExtras().getDouble(CALCULATION_MAX_EDGE_THICKNESS);
            etOnCertainAxis = getIntent().getExtras().getDouble(CALCULATION_THICKNESS_ON_CERTAIN_AXIS);
            axisView = getIntent().getExtras().getInt(CALCULATION_AXIS_VIEW);

            String centerThicknessResult = String.format(Locale.ENGLISH,stringCenterThicknessNumbers, centerThickness);
            String edgeThicknessResult = String.format(Locale.ENGLISH, stringEdgeThicknessNumbers, edgeThickness);
            String edgeThicknessMaxET = String.format(Locale.ENGLISH,stringMaxETNumbers,maxEdgeThickness);
            String edgeThicknessCertainAxis = String.format(Locale.ENGLISH,stringCertainETNumbers,etOnCertainAxis);

            listWithResult.add(stringCenterThickness + " " + centerThicknessResult);
            listWithResult.add(stringEdgeThickness + " " + edgeThicknessResult);
            listWithResult.add(stringMaxET + " " + edgeThicknessMaxET);
            listWithResult.add(stringCertainET + String.valueOf(axisView) + stringCertainETSecond +
                    " " + edgeThicknessCertainAxis);
            isCylinder = false;
        }else{
            String centerThicknessResult = String.format(Locale.ENGLISH,stringCenterThicknessNumbers, centerThickness);
            String edgeThicknessResult = String.format(Locale.ENGLISH, stringEdgeThicknessNumbers, edgeThickness);
            listWithResult.add(stringCenterThickness + " " + centerThicknessResult);
            listWithResult.add(stringEdgeThickness + " " + edgeThicknessResult);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listWithResult){

            //  change text color in list items
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(0xDE000000);

                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    private void setListViewParameters() {
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.horizontal_divider));
        listView.setDivider(sage);
        listView.setEnabled(false);
        listView.setBackgroundResource(R.drawable.result_activity_background_selector);
        listView.setDividerHeight(1);
        listView.setPadding(20,20,20,20);
    }

}
