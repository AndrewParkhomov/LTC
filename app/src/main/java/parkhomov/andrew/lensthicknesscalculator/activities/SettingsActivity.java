package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
        customizeListiew();
    }
    private void customizeListiew(){
        // set divider color
        //int[] colors = {0, 0xFF388E3C, 0}; // green divider
        //listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listView.setDivider(new ColorDrawable(0xDE000000));
        listView.setDividerHeight(1);
        listView.setPadding(16,16,16,16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch(position){
            case 0:
                Intent intent = new Intent(SettingsActivity.this, LanguageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
    }
}
