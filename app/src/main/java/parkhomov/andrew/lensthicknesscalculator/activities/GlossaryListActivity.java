package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

public class GlossaryListActivity extends ListActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createList();
    }

    /**
     *According to current language, method get titles from SQLite database, and create list
     */
    private void createList(){
        listView = getListView();
        LanguageManagerActivity languageManager = new LanguageManagerActivity(this);
        // create list with titles
        languageManager.setTitlesForGlossaryList();

        // list which display items(titles) in glossary
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                //get list, which was created
                languageManager.getListWithTitles()){

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

    private void setDividerColorAndBackground() {
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.blue_500));
        listView.setDivider(sage);
        listView.setBackgroundResource(R.drawable.fragment_background);
        listView.setDividerHeight(1);
        listView.setPadding(16,16,16,16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, GlossaryActivity.class);
        intent.putExtra(GlossaryActivity.QUERY_MARK_BUTON_ID, position+1);
        intent.putExtra(GlossaryActivity.QUERY_MARK_ID_FOR_SQL, position+1);
        GlossaryActivity.isGlossaryList = true;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
