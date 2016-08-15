package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

/**
 * Class with two fields - settings and rate this app.
 */

public class SettingsActivity extends ListActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.settings_list)){

                //  change text color in list items
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view =super.getView(position, convertView, parent);

                    TextView textView=(TextView) view.findViewById(android.R.id.text1);

                    textView.setTextColor(0xDE000000);

                    return view;
                }
        };
        listView.setAdapter(adapter);
        setDividerColorAndBackground();

    }
    private void setDividerColorAndBackground(){
        // set divider color and background
        listView = getListView();
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.blue_500));
        listView.setDivider(sage);
        listView.setBackgroundResource(R.drawable.fragment_background);
        listView.setDividerHeight(1);
        listView.setPadding(16,16,16,16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class targetClass = null;
        switch (position) {
            case 0:
                targetClass = SetLanguageActivity.class;
                break;
            case 1:
                targetClass =  RateAppActivity.class;
                break;
        }
        startActivity(new Intent(this, targetClass));
    }
}
