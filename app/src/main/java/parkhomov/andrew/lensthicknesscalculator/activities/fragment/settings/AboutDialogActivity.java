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
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent;


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

        String text = getResources().getString(R.string.version) + "\n\n" +
                getResources().getString(R.string.aboutLTCdeveloper) + "\n" +
                getResources().getString(R.string.aboutLTCemail) + "\n\n" +
                getResources().getString(R.string.copyright) + " " +
                getResources().getString(R.string.year);
        if (tv != null) {
            tv.setTextColor(0xDE000000);
            tv.setText(text);
        }
        return view;
    }

}
