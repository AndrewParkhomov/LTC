package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class GlossaryActivity extends AppCompatActivity {

    Cursor cursor;
    int itemId;
    int imageId;
    // temp number need to switch off or on correct information in addTExtView
    int tempNumber;
    String name;
    String description;
    TextView setName;
    TextView setDescription;
    TextView addTV;
    ImageView setPicture;

    public static final String QUERY_MARK_LISTNUMBER_ID = "listId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getExtras().getInt(QUERY_MARK_LISTNUMBER_ID);
        Cursor cursor;
        // get info from database
        try{
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
            SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
            String currentLanguage = String.valueOf(Locale.getDefault().getDisplayLanguage());
            switch(currentLanguage){
                case "English":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_ENG", "DESCRIPTION_ENG", "IMAGE_RESOURCE_ID"},
                            "_id = ?",
                            new String[]{Integer.toString(itemId)},
                            null, null, null);
                    break;
                case "русский":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_RUS", "DESCRIPTION_RUS", "IMAGE_RESOURCE_ID"},
                            "_id = ?",
                            new String[]{Integer.toString(itemId)},
                            null, null, null);
                    break;
                case "українська":
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_UKR", "DESCRIPTION_UKR", "IMAGE_RESOURCE_ID"},
                            "_id = ?",
                            new String[]{Integer.toString(itemId)},
                            null, null, null);
                    break;
                default:
                    cursor = db.query("GLOSSARY",
                            new String[]{"NAME_ENG", "DESCRIPTION_ENG", "IMAGE_RESOURCE_ID"},
                            "_id = ?",
                            new String[]{Integer.toString(itemId)},
                            null, null, null);
            }

            if(cursor.moveToFirst()){
                name = cursor.getString(0);
                description = cursor.getString(1);
                imageId = cursor.getInt(2);
            }
            cursor.close();
            db.close();
        }catch(SQLException e){
            Toast.makeText(this, getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_glossary);
        // set title, text and picture in glossary
        setName = (TextView)findViewById(R.id.glossaryTitleTextView);
        setDescription = (TextView)findViewById(R.id.glossaryDescriptionTextView);
        setPicture = (ImageView)findViewById(R.id.glossaryImageView);
        setName.setText(name);
        setDescription.setText(description);
        setPicture.setImageResource(imageId);
        if(itemId == 7){
            tempNumber = 12;
            addTV = (TextView)findViewById(R.id.glossaryAddTextView);
            addTV.setText(getResources().getText(R.string.link_to_transposition));
        }
        if(itemId == 8){
            addTV = (TextView)findViewById(R.id.glossaryAddTextView);
            addTV.setText(getResources().getText(R.string.link_to_diamCalcActivity));
        }
    }


    public void onGlossaryTextViewClicked(View view) {
        if(itemId == 8) {
            startActivity(new Intent(this, DiamCalculatorActivity.class));
        }
        if(itemId == 7){
            try{
                SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
                SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
                String currentLanguage = String.valueOf(Locale.getDefault().getDisplayLanguage());
                switch(currentLanguage) {
                    case "English":
                        cursor = db.query("GLOSSARY",
                                new String[]{"NAME_ENG", "DESCRIPTION_ENG", "IMAGE_RESOURCE_ID"},
                                "_id = ?",
                                new String[]{Integer.toString(tempNumber)},
                                null, null, null);
                        break;
                    case "русский":
                        cursor = db.query("GLOSSARY",
                                new String[]{"NAME_RUS", "DESCRIPTION_RUS", "IMAGE_RESOURCE_ID"},
                                "_id = ?",
                                new String[]{Integer.toString(tempNumber)},
                                null, null, null);
                        break;
                    case "українська":
                        cursor = db.query("GLOSSARY",
                                new String[]{"NAME_UKR", "DESCRIPTION_UKR", "IMAGE_RESOURCE_ID"},
                                "_id = ?",
                                new String[]{Integer.toString(tempNumber)},
                                null, null, null);
                        break;
                    default:
                        cursor = db.query("GLOSSARY",
                                new String[]{"NAME_ENG", "DESCRIPTION_ENG", "IMAGE_RESOURCE_ID"},
                                "_id = ?",
                                new String[]{Integer.toString(tempNumber)},
                                null, null, null);
                }

                if(cursor.moveToFirst()) {
                    String name = cursor.getString(0);
                    String description = cursor.getString(1);
                    int glossaryImageResourceId = cursor.getInt(2);
                    setName.setText(name);
                    setDescription.setText(description);
                    setPicture.setImageResource(glossaryImageResourceId);
                    addTV.setText(null);
                }
            }catch (SQLException e){
                Toast.makeText(this,  getResources().getText(R.string.database_unavailable), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
