package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

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

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.languageRadioGroup);

        // set checked button with current language
        String currentLanguage = String.valueOf(Locale.getDefault().getDisplayLanguage());
        if(radioGroup != null) {
            switch (currentLanguage) {
                case "українська":
                    radioGroup.check(R.id.thirdLanguageCheckbutton);
                    break;
                case "русский":
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
                            language = "en_GB";
                            break;
                        case 1:
                            language = "ru_RU";
                            break;
                        case 2:
                            language = "uk_UA";
                            break;
                    }
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getApplicationContext().getResources().updateConfiguration(config, null);

                    TestLanguage setCurrentLanguage = new TestLanguage(SetLanguageActivity.this);
                    setCurrentLanguage.setNewLanguage(language);

                    Intent intent = new Intent(SetLanguageActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }
}
