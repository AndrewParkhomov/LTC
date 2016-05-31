package parkhomov.andrew.lensthicknesscalculator.activities;


import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class HelpTheProjectActivity extends ListActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.help_and_feedback_array_list)
        );
        listView.setAdapter(adapter);
        customizeListView();
    }

    private void customizeListView(){
        // set divider color
        //int[] colors = {0, 0xFF388E3C, 0}; // green divider
        //listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listView.setDivider(new ColorDrawable(0xDE000000));
        listView.setDividerHeight(1);
        listView.setPadding(16,16,16,16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, RateAppActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, FinanceHelpActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, HelpFeedbackActivity.class);
                startActivity(intent);
                break;
        }
    }
}
