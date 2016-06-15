package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class SettingsActivity extends ListActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.settings_list)
        );
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
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, LanguageActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, RateAppActivity.class);
                startActivity(intent);
                break;
        }
    }
}
