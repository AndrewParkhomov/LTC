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

/**
 * Class for small and normal screen size. Create dialog window and show result.
 */

public class ThicknessResultActivity extends ListActivity {

    public static final String CALCULATION_CENTER_THICKNESS = "CENTER";
    public static final String CALCULATION_EDGE_THICKNESS = "EDGE";
    public static final String CALCULATION_MAX_EDGE_THICKNESS = "MAX_EDGE";
    public static final String CALCULATION_THICKNESS_ON_CERTAIN_AXIS = "ON_CERTAIN_AXIS";
    public static final String CALCULATION_AXIS_VIEW = "AXIS";

    public static boolean isCylinder;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDataInListView();
        setListViewParameters();
    }

    private void addDataInListView() {
        listView = getListView();
        String stringCenterThickness, stringEdgeThickness, formatForNumbers;
        ArrayList<String> listWithResult = new ArrayList<>();
        double centerThickness = getIntent().getExtras().getDouble(CALCULATION_CENTER_THICKNESS);
        double edgeThickness = getIntent().getExtras().getDouble(CALCULATION_EDGE_THICKNESS);
        stringCenterThickness = getResources().getString(R.string.thkns_activ_textview_center_thickness);
        stringEdgeThickness = getResources().getString(R.string.thkns_activ_textview_edge_thickness);
        formatForNumbers = getResources().getString(R.string.for_short_number_string_format);

        if(isCylinder){
            // get text "static" string fields
            String stringMaxET = getResources().getString(R.string.thkns_activ_textview_max_edge_thickness);
            String stringCertainET = getResources().getString(R.string.thkns_activ_textview_certain_edge_thickness);
            String stringCertainETSecond = getResources().getString(R.string.thkns_activ_textview_certain_second_half);

            double maxEdgeThickness = getIntent().getExtras().getDouble(CALCULATION_MAX_EDGE_THICKNESS);
            double etOnCertainAxis = getIntent().getExtras().getDouble(CALCULATION_THICKNESS_ON_CERTAIN_AXIS);
            int axisView = getIntent().getExtras().getInt(CALCULATION_AXIS_VIEW);

            String centerThicknessResult = String.format(Locale.ENGLISH,formatForNumbers, centerThickness);
            String edgeThicknessResult = String.format(Locale.ENGLISH, formatForNumbers, edgeThickness);
            String edgeThicknessMaxET = String.format(Locale.ENGLISH,formatForNumbers,maxEdgeThickness);
            String edgeThicknessCertainAxis = String.format(Locale.ENGLISH,formatForNumbers,etOnCertainAxis);

            listWithResult.add(stringCenterThickness + " " + centerThicknessResult);
            listWithResult.add(stringEdgeThickness + " " + edgeThicknessResult);
            listWithResult.add(stringMaxET + " " + edgeThicknessMaxET);
            listWithResult.add(stringCertainET + String.valueOf(axisView) + stringCertainETSecond +
                    " " + edgeThicknessCertainAxis);
            isCylinder = false;
        }else{
            String centerThicknessResult = String.format(Locale.ENGLISH,formatForNumbers, centerThickness);
            String edgeThicknessResult = String.format(Locale.ENGLISH, formatForNumbers, edgeThickness);
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
