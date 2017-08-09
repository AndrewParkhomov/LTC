package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import parkhomov.andrew.lensthicknesscalculator.BuildConfig;
import parkhomov.andrew.lensthicknesscalculator.R;


public class AboutDialogActivity extends DialogFragment {

    @BindView(R.id.textViewAboutDialog)
    TextView tv;

    public static AboutDialogActivity getInstance() {
        Bundle bundle = new Bundle();
        AboutDialogActivity aboutDialogActivity = new AboutDialogActivity();
        aboutDialogActivity.setArguments(bundle);
        return aboutDialogActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about_dialog, container, false);
        ButterKnife.bind(this, view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white);
        }

        String text = String.format("%s\n\n%s\n\n%s\n\n%s %s",
                getResources().getString(R.string.version) + BuildConfig.VERSION_NAME,
                getResources().getString(R.string.author_name),
                getResources().getString(R.string.author_email),
                getResources().getString(R.string.copyright),
                getResources().getString(R.string.year));
        tv.setText(text);
        return view;
    }

}
