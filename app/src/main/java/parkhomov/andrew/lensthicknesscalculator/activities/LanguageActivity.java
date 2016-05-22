package parkhomov.andrew.lensthicknesscalculator.activities;


import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class LanguageActivity extends ListActivity {

    ListView listView;
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.language_list)
        );
        listView.setAdapter(adapter);
        customizeListiew();
    }

    private void customizeListiew() {
        listView.setDivider(new ColorDrawable(0xDE000000));
        listView.setDividerHeight(1);
        listView.setPadding(16, 16, 16, 16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Configuration config;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try{
            ContentValues languageValues = new ContentValues();
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
            SQLiteDatabase db = glossaryDatabase.getWritableDatabase();
            // get lists items in certain language
            switch(position){
                case 0:
                    languageValues.put("CURRENT_LANGUAGE", "English");
                    db.update("LANGUAGE", languageValues,"_id = ?",
                            new String[] {Integer.toString(1)});
                    locale = new Locale("en-gb");
                    Locale.setDefault(locale);
                    config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                    startActivity(intent);
                    break;
                case 1:
                    languageValues.put("CURRENT_LANGUAGE", "русский");
                    db.update("LANGUAGE", languageValues,"_id = ?",
                            new String[] {Integer.toString(1)});
                    locale = new Locale("ru");
                    Locale.setDefault(locale);
                    config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                    startActivity(intent);
                    break;
                case 2:
                    languageValues.put("CURRENT_LANGUAGE", "українська");
                    db.update("LANGUAGE", languageValues,"_id = ?",
                            new String[] {Integer.toString(1)});
                    locale = new Locale("uk");
                    Locale.setDefault(locale);
                    config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                    startActivity(intent);

                    break;
                default:
                    languageValues.put("CURRENT_LANGUAGE", "English");
                    db.update("LANGUAGE", languageValues,"_id = ?",
                            new String[] {Integer.toString(1)});
                    break;
            }
            db.close();
        }catch(SQLException e) {
            Toast.makeText(this, getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }
    }
}
