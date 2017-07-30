package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import parkhomov.andrew.lensthicknesscalculator.R;

/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
public class Language extends DialogFragment {

    @BindView(R.id.languageRadioGroup)
    RadioGroup radioGroup;

    public static Language getInstance() {
        Bundle bundle = new Bundle();
        Language language = new Language();
        language.setArguments(bundle);
        return language;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_language, container, false);
        ButterKnife.bind(this, view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        setUpRadiobuttons();
        return view;
    }


    private void setUpRadiobuttons() {
        String currentLanguage = getCurrentLanguage();
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
                int checkedPosition = getRadiobuttonId();

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
                setCurrentLanguage(language);
            }
        });


    }

    private void setCurrentLanguage(String language){
        Configuration config = getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }
        if (language != null && !language.equals("") && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);

            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                Log.d(Tag.AUTHORIZATION, " createConfigurationContext");
//                createConfigurationContext(config);
//            } else {
//                Log.d(Tag.AUTHORIZATION, " updateConfiguration");
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
//            }
        }
    }

    private int getRadiobuttonId() {
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())));
        return radioButton != null ? radioButton.getId() : -1;
    }

    private String getCurrentLanguage() {
        return Locale.getDefault().getISO3Language().substring(0, 2);
    }
}
