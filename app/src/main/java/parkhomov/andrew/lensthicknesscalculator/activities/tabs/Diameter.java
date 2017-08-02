package parkhomov.andrew.lensthicknesscalculator.activities.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails;
import parkhomov.andrew.lensthicknesscalculator.activities.main.MyApp;

/**
 * Created by MyPC on 29.07.2017.
 */


public class Diameter extends AbstractTabFragment {

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
    EditText ed;
    @BindView(R.id.dblET)
    EditText dbl;
    @BindView(R.id.pdET)
    EditText pd;


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

        return view;
    }

    @OnClick(R.id.diameterCalculateButton)
    public void onButtonClicked() {
        setUpViewsBehaviourBefore();
        double edValue = -1, dblValue = -1, pdValue = -1;
        try {
            edValue = Double.valueOf(ed.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(ed, edWrapper);
        }
        try {
            dblValue = Double.valueOf(dbl.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(dbl, dblWrapper);
        }
        try {
            pdValue = Double.valueOf(pd.getText().toString());
        } catch (NumberFormatException e) {
            Utils.highlightEditText(pd, pdWrapper);
        }

        if (edValue != -1 && dblValue != -1 && pdValue != -1) {
            double diam = Math.ceil(edValue * 2 + dblValue - pdValue);
            String result = getResources().getString(R.string.diam_activ_textView_result_formula) +
                    String.valueOf(diam).replace(",", ".") +
                    getResources().getString(R.string.diam_activ_textView_mm);

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
        Utils.makeNormalEditText(ed, edWrapper);
        Utils.makeNormalEditText(dbl, dblWrapper);
        Utils.makeNormalEditText(pd, pdWrapper);
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
        }
    }
}