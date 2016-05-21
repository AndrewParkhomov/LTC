package parkhomov.andrew.lensthicknesscalculator.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
                            null, null, "NAME_ENG ASC");
                    break;
                case "русский":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_RUS"},
                            null,
                            null,
                            null, null, "NAME_RUS ASC");
                    break;
                case "українська":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_UKR"},
                            null,
                            null,
                            null, null, "NAME_UKR ASC");
                    break;
                default:
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_ENG"},
                            null,
                            null,
                            null, null, "NAME_ENG ASC");
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
                listView = getListView();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        glossaryList
                );
                listView.setAdapter(adapter);
                cursor.close();
                db.close();
            }
        }catch(SQLException e){
            Toast.makeText(this, getResources().getText(R.string.database_unavailable), Toast.LENGTH_SHORT).show();
        }
    }
}
