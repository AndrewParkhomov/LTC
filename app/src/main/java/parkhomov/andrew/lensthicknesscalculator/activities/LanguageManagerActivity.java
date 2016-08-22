package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class LanguageManagerActivity {

    private Context context;
    private String name, description, currentLanguage, errorMessage;
    private int imageId;
    private ArrayList<String> listWithTitles;
    private SQLiteOpenHelper glossaryDatabase;
    private SQLiteDatabase db;

    public LanguageManagerActivity(Context c){
        this.context = c;
        try{
            glossaryDatabase = new GlossaryDatabase(context);
            db = glossaryDatabase.getReadableDatabase();
            currentLanguage = Locale.getDefault().getLanguage();
            errorMessage = String.valueOf(context.getResources().getText(R.string.database_unavailable));
        }catch(SQLException e){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    public void setNewLanguage(String language){
        try{
            ContentValues languageValues = new ContentValues();
            SQLiteDatabase db = glossaryDatabase.getWritableDatabase();
            languageValues.put("CURRENT_LANGUAGE", language);
            db.update("LANGUAGE", languageValues,"_id = ?",
                    new String[] {Integer.toString(1)});
            db.close();
        }catch(SQLException e){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void setCurrentLanguage(){
        String language = null;
        try {
            Cursor cursor = db.query("LANGUAGE",
                    new String[]{"CURRENT_LANGUAGE"},
                    null,
                    null,
                    null, null, null);
            if (cursor.moveToFirst()) {
                // if user start app in the first time, and we don't know language, that user choose.
                if (cursor.getString(0).equals("none")){
                    language = Locale.getDefault().getLanguage();
                }else{
                    language = cursor.getString(0);
                }
            }
            cursor.close();
            db.close();
        }catch (SQLException e){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, null);
    }

    public void setDataForDetailDescription(int id){
        String getName, getDescription;
        try{
            switch(currentLanguage){
                case "ru_ru":
                case "ru":
                    getName = "NAME_RUS";
                    getDescription = "DESCRIPTION_RUS";
                    break;
                case "uk_uk":
                case "uk":
                    getName = "NAME_UKR";
                    getDescription = "DESCRIPTION_UKR";
                    break;
                default:
                    getName = "NAME_ENG";
                    getDescription = "DESCRIPTION_ENG";
                    break;
            }
            Cursor cursor = db.query("GLOSSARY",
                    new String[]{getName, getDescription, "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(id)},
                    null, null, null);
            if(cursor.moveToFirst()){
                name = cursor.getString(0);
                description = cursor.getString(1);
                imageId = cursor.getInt(2);
            }
            cursor.close();
            db.close();
        }catch(SQLException e){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void setTitlesForGlossaryList(){
        String title;
        try{
            // get lists items in certain language
            switch(currentLanguage){
                case "ru_ru":
                case "ru":
                    title = "NAME_RUS";
                    break;
                case "uk_uk":
                case "uk":
                    title = "NAME_UKR";
                    break;
                default:
                    title = "NAME_ENG";
                    break;
            }
            Cursor cursor = db.query("GLOSSARY",
                    new String[]{title},
                    null,
                    null,
                    null, null, null);
            if(cursor.moveToFirst()){
                listWithTitles = new ArrayList<>();
                // get first position from database, and add it to list
                listWithTitles.add(cursor.getString(0));
                // get all others position from database and add them to list
                while(cursor.moveToNext()){
                    listWithTitles.add(cursor.getString(0));
                }
            }
            cursor.close();
            db.close();
        }catch(SQLException e){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getImageId(){
        return imageId;
    }

    public ArrayList<String> getListWithTitles(){
        return listWithTitles;
    }
}
