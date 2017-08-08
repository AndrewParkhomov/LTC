package parkhomov.andrew.lensthicknesscalculator.activities.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MainActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MyApp;

/**
 * Created by MyPC on 29.07.2017.
 */


public class Diameter extends AbstractTabFragment implements
        MainActivity.HideKeyboardI {

    @BindView(R.id.calculationContainerConstr)
    ConstraintLayout mainHolder;
    @BindView(R.id.textTest)
    HTextView textTest;
    @BindView(R.id.diameterCalculateButton)
    Button button;

    @BindView(R.id.edTxtInptL)
    TextInputLayout edWrapper;
    @BindView(R.id.dblTxtInptL)
    TextInputLayout dblWrapper;
    @BindView(R.id.pdTxtInptL)
    TextInputLayout pdWrapper;

    @BindView(R.id.edET)
    EditText edET;
    @BindView(R.id.dblET)
    EditText dblET;
    @BindView(R.id.pdET)
    EditText pdET;


    public static Diameter getInstance(List<String> headers, List<String> description, List<Integer> images) {
        Bundle bundle = new Bundle();
        Diameter diameter = new Diameter();
        diameter.setArguments(bundle);
        diameter.setTitle(MyApp.getAppContext().getString(R.string.tab_diameter));
        diameter.setHeaders(headers);
        diameter.setDescription(description);
        diameter.setImages(images);
        return diameter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diameter_fragment, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();

        button.setText(Utils.spacing(getString(R.string.button_text_calculate), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8));
        setUpListeners();
        return view;
    }

    private void setUpListeners() {
        ViewPager viewPager = ButterKnife.findById(activity, R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideSoftKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void hideKeyboard() {
        hideSoftKeyboard();
    }

    @OnClick(R.id.diameterCalculateButton)
    public void onButtonClicked() {
        setUpViewsBehaviourBefore();
        double edValue = -1, dblValue = -1, pdValue = -1;
        try {
            edValue = Double.valueOf(edET.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(edET, edWrapper);
        }
        try {
            dblValue = Double.valueOf(dblET.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(dblET, dblWrapper);
        }
        try {
            pdValue = Double.valueOf(pdET.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(pdET, pdWrapper);
        }

        if (edValue != -1 && dblValue != -1 && pdValue != -1) {
            double diam = Math.ceil(edValue * 2 + dblValue - pdValue);
            DecimalFormat df = new DecimalFormat("#");
            String result = String.format("%s%s %s",
                    getResources().getString(R.string.diam_activ_textView_result_formula),
                    String.valueOf(df.format(diam)),
                    getResources().getString(R.string.result_mm));

            // get random true or false and show animation according to type
            HTextViewType animationType = Math.random() < 0.5 ? HTextViewType.ANVIL : HTextViewType.LINE;
            textTest.setAnimateType(animationType);
            // need to repeat animation
            textTest.animateText("");
            textTest.animateText(result);
        }
        setUpViewsBehaviourAfter();
    }

    private void setUpViewsBehaviourAfter() {
        Utils.enableWrapper(edWrapper);
        Utils.enableWrapper(dblWrapper);
        Utils.enableWrapper(pdWrapper);
    }

    private void setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(edET, edWrapper);
        Utils.makeNormalEditText(dblET, dblWrapper);
        Utils.makeNormalEditText(pdET, pdWrapper);
        Utils.disableWrapper(edWrapper);
        Utils.disableWrapper(dblWrapper);
        Utils.disableWrapper(pdWrapper);
    }


    @OnClick({R.id.edImgB,
            R.id.dblImgB,
            R.id.pdImgB})
    public void onQueryClicked(View v) {
        int position = -1;
        switch (v.getId()) {
            case R.id.edImgB:
                position = 8;
                break;
            case R.id.dblImgB:
                position = 9;
                break;
            case R.id.pdImgB:
                position = 10;
                break;
        }
        if (position != -1) {
            try {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainContainerConstr,
                                GlossaryDetails.getInstance(
                                        headers.get(position),
                                        description.get(position),
                                        images.get(position)),
                                CONSTANT.GLOSSARY_DETAILS)
                        .commit();
            } catch (IllegalStateException e) {
                Log.d(CONSTANT.MY_EXCEPTION, e.toString() + "");
            }
            hideSoftKeyboard();
        }
    }
    private void hideSoftKeyboard() {
        Utils.getInputManager().hideSoftInputFromWindow(edET.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(dblET.getWindowToken(), 0);
        Utils.getInputManager().hideSoftInputFromWindow(pdET.getWindowToken(), 0);
    }
}