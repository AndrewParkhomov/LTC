package parkhomov.andrew.lensthicknesscalculator.activities;


import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class GetLanguageActivity extends AppCompatActivity {

    String error;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getData(int id){

        try{
            Configuration config;
            Locale locale;
            ContentValues languageValues = new ContentValues();
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(GetLanguageActivity.this);
            try{
                SQLiteDatabase db = glossaryDatabase.getWritableDatabase();
            }catch(Exception e){
                error = String.valueOf(e);
            }
            finally {
                Toast.makeText(GetLanguageActivity.this, error, Toast.LENGTH_SHORT).show();
            }


            //x = id * 2;
            // get lists items in certain language
//            switch (id) {
//                case 0:
//                    languageValues.put("CURRENT_LANGUAGE", "English");
//                    db.update("LANGUAGE", languageValues, "_id = ?",
//                            new String[]{Integer.toString(1)});
//                    locale = new Locale("en-gb");
//                    Locale.setDefault(locale);
//                    config = new Configuration();
//                    config.locale = locale;
//                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                    break;
//                case 1:
//                    languageValues.put("CURRENT_LANGUAGE", "русский");
//                    db.update("LANGUAGE", languageValues, "_id = ?",
//                            new String[]{Integer.toString(1)});
//                    locale = new Locale("ru");
//                    Locale.setDefault(locale);
//                    config = new Configuration();
//                    config.locale = locale;
//                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                    break;
//                case 2:
//                    languageValues.put("CURRENT_LANGUAGE", "українська");
//                    db.update("LANGUAGE", languageValues, "_id = ?",
//                            new String[]{Integer.toString(1)});
//                    locale = new Locale("uk");
//                    Locale.setDefault(locale);
//                    config = new Configuration();
//                    config.locale = locale;
//                    getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                    break;
//                default:
//                    languageValues.put("CURRENT_LANGUAGE", "English");
//                    db.update("LANGUAGE", languageValues, "_id = ?",
//                            new String[]{Integer.toString(1)});
//                    break;
//            }
//            db.close();
        }catch(SQLException e) {
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }
    }
}