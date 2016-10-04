package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;

/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
public class SetLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        //disable outside touches in dialog activity
        this.setFinishOnTouchOutside(false);
        setTitle(R.string.language);

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.languageRadioGroup);

        // set checked button with current language
        String currentLanguage = Locale.getDefault().getLanguage();
        if(radioGroup != null) {
            switch (currentLanguage) {
                case "uk_uk":
                case "uk":
                    radioGroup.check(R.id.thirdLanguageCheckbutton);
                    break;
                case "ru_ru":
                case "ru":
                    radioGroup.check(R.id.secondLanguageCheckbutton);
                    break;
                default:
                    radioGroup.check(R.id.firstLanguageCheckbutton);
                    break;
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int checkedPosition = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));

                    String language = null;
                    switch (checkedPosition) {
                        case 0:
                            language = "en_gb";
                            break;
                        case 1:
                            language = "ru_ru";
                            break;
                        case 2:
                            language = "uk_uk";
                            break;
                    }
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getApplicationContext().getResources().updateConfiguration(config, null);

                    LanguageManagerActivity setCurrentLanguage = new LanguageManagerActivity(SetLanguageActivity.this);
                    setCurrentLanguage.setNewLanguage(language);

                    Intent intent = new Intent(SetLanguageActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}
