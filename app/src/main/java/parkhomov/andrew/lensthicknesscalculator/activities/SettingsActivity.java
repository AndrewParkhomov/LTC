package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class SettingsActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                getResources().getStringArray(R.array.settings_list)
        );
        listView.setAdapter(adapter);

    }
}
