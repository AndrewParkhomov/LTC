package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class TestLanguage {

    private Context context;

    public TestLanguage(Context c){
        this.context = c;
    }

    public void setNewLanguage(String language){
        try{
            ContentValues languageValues = new ContentValues();
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(context);
            SQLiteDatabase db = glossaryDatabase.getWritableDatabase();
            languageValues.put("CURRENT_LANGUAGE", language);
            db.update("LANGUAGE", languageValues,"_id = ?",
                    new String[] {Integer.toString(1)});
            db.close();
        }catch(SQLException e){
            Toast.makeText(context, context.getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }
    }

    public void setCurrentLanguage(){
        String language = null;
        try {
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(context);
            SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
            Cursor cursor = db.query("LANGUAGE",
                    new String[]{"CURRENT_LANGUAGE"},
                    null,
                    null,
                    null, null, null);
            if (cursor.moveToFirst()) {
                if (cursor.getString(0).equals("none")){
                    language = String.valueOf(Locale.getDefault().getLanguage());
                }else{
                    language = cursor.getString(0);
                }
            }
            cursor.close();
            db.close();
        }catch (SQLException e){
            //Toast.makeText(TestLanguage.this, context.getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, null);

        //Toast.makeText(context, language, Toast.LENGTH_SHORT).show();
        String language2 = String.valueOf(Locale.getDefault().getLanguage());
        Toast.makeText(context, language2, Toast.LENGTH_SHORT).show();
    }
}
