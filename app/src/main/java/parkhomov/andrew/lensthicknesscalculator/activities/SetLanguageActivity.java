package parkhomov.andrew.lensthicknesscalculator.activities;


import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class SetLanguageActivity extends AppCompatActivity {

    Locale locale;
    RadioGroup radioGroup;
    int checkedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        //disable outside touches in dialog activity
        this.setFinishOnTouchOutside(false);

        radioGroup = (RadioGroup)findViewById(R.id.languageRadioGroup);

        // set checked button with current language
        String currentLanguage = String.valueOf(Locale.getDefault().getDisplayLanguage());
        switch(currentLanguage) {
            case "English":
                radioGroup.check(R.id.firstLanguageCheckbutton);
                break;
            case "русский":
                radioGroup.check(R.id.secondLanguageCheckbutton);
                break;
            case "українська":
                radioGroup.check(R.id.thirdLanguageCheckbutton);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            checkedPosition = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            GetLanguageActivity gl = new GetLanguageActivity();
            gl.getData(checkedPosition);

            //Toast.makeText(SetLanguageActivity.this, String.valueOf(gl.x), Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //Toast.makeText(SetLanguageActivity.this,String.valueOf(x), Toast.LENGTH_LONG).show();
            //startActivity(intent);
            //Toast.makeText(SetLanguageActivity.this, String.valueOf(checkedPosition), Toast.LENGTH_SHORT).show();
//            Configuration config;
//            try{
//                ContentValues languageValues = new ContentValues();
//                SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(SetLanguageActivity.this);
//                SQLiteDatabase db = glossaryDatabase.getWritableDatabase();
//                // get lists items in certain language
//                switch(checkedPosition){
//                    case 0:
//                        languageValues.put("CURRENT_LANGUAGE", "English");
//                        db.update("LANGUAGE", languageValues,"_id = ?",
//                                new String[] {Integer.toString(1)});
//                        locale = new Locale("en-gb");
//                        Locale.setDefault(locale);
//                        config = new Configuration();
//                        config.locale = locale;
//                        getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                        break;
//                    case 1:
//                        languageValues.put("CURRENT_LANGUAGE", "русский");
//                        db.update("LANGUAGE", languageValues,"_id = ?",
//                                new String[] {Integer.toString(1)});
//                        locale = new Locale("ru");
//                        Locale.setDefault(locale);
//                        config = new Configuration();
//                        config.locale = locale;
//                        getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                        break;
//                    case 2:
//                        languageValues.put("CURRENT_LANGUAGE", "українська");
//                        db.update("LANGUAGE", languageValues,"_id = ?",
//                                new String[] {Integer.toString(1)});
//                        locale = new Locale("uk");
//                        Locale.setDefault(locale);
//                        config = new Configuration();
//                        config.locale = locale;
//                        getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                        break;
//                    default:
//                        languageValues.put("CURRENT_LANGUAGE", "English");
//                        db.update("LANGUAGE", languageValues,"_id = ?",
//                                new String[] {Integer.toString(1)});
//                        break;
//                }
//                db.close();
//                startActivity(intent);
//            }catch(SQLException e) {
//                Toast.makeText(getApplicationContext(), getResources().getText(R.string.database_unavailable), Toast.LENGTH_LONG).show();
//            }
//
        }
    });
    }
}
