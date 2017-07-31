package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;

/**
 * This class is for switch language. Radiobutton checked changed, and locale is update.
 */
public class Language extends DialogFragment {

    @BindView(R.id.radioButtonContainer)
    RadioGroup radioGroup;

    private Activity activity;
    private View view;

    public static Language getInstance() {
        Bundle bundle = new Bundle();
        Language language = new Language();
        language.setArguments(bundle);
        return language;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_language, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white);
        }
        addRadioButtons();
        getCheckedPosition();
        return view;
    }

    private void addRadioButtons() {

        List<String> languages = new ArrayList<>(3);
        languages.add(0, getString(R.string.english));
        languages.add(1, getString(R.string.russian));
        languages.add(2, getString(R.string.ukrainian));

        radioGroup = new RadioGroup(activity);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        int checkedPosition = getCheckedPosition();
        for (int i = 0; i < languages.size(); i++) {
            RadioButton button = new RadioButton(activity, null, R.attr.radioButtonStyle);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setId(i);
            if (i == checkedPosition)
                button.setChecked(true);
            button.setTextSize(20);
            button.setText(languages.get(i));
            button.setPadding(
                    Utils.convertDpToPixel(40),
                    Utils.convertDpToPixel(20),
                    Utils.convertDpToPixel(20),
                    Utils.convertDpToPixel(20));
            radioGroup.addView(button);
        }
        ((ViewGroup) view.findViewById(R.id.radioButtonContainer)).addView(radioGroup);

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
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
            }
        });
    }

    private int getCheckedPosition() {
        int checkedButtonId;
        switch (getCurrentLanguage()) {
            case "ru_ru":
            case "ru":
                checkedButtonId = 1;
                break;
            case "uk_uk":
            case "uk":
                checkedButtonId = 2;
                break;
            default:
                checkedButtonId = 0;
                break;
        }
        return checkedButtonId;
    }

    private void setCurrentLanguage(String language) {
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
            // button do not enough time to show in checked position(this for better view)
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.removeCallbacksAndMessages(null);
                    dismiss();
                }
            }, 200);
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
