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

public class GlossaryListActivity extends ListActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        Cursor cursor;
        // get info from database
        try{
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
            SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
            String currentLanguage = String.valueOf(Locale.getDefault().getDisplayLanguage());
            // get lists items in certain language
             switch(currentLanguage){
                case "English":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_ENG"},
                            null,
                            null,
                            null, null, null);
                    break;
                case "русский":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_RUS"},
                            null,
                            null,
                            null, null, null);
                    break;
                case "українська":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_UKR"},
                            null,
                            null,
                            null, null, null);
                    break;
                default:
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_ENG"},
                            null,
                            null,
                            null, null, null);
                    break;
            }
            if(cursor.moveToFirst()){
                ArrayList<String> glossaryList = new ArrayList<>();
                // get first position from database, and add it to list
                glossaryList.add(cursor.getString(0));
                // get all others position from database and add them to list
                while(cursor.moveToNext()){
                    glossaryList.add(cursor.getString(0));
                }
                // list which display items(titles) in glossary
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        glossaryList){

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
                cursor.close();
                db.close();
            }
        }catch(SQLException e){
            Toast.makeText(this, getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }
        setDividerColorAndBackground();
        android.app.ActionBar ab = getActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
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
        intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, (int)id+1);
        GlossaryActivity.isGlossaryList = true;
        startActivity(intent);
    }
}
