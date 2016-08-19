package parkhomov.andrew.lensthicknesscalculator.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.GlossaryActivity;
import parkhomov.andrew.lensthicknesscalculator.activities.ThicknessResultActivity;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;

/**
 * This class is for lens diameter calculations.
 */

public class DiamCalculatorFragment extends Fragment implements View.OnClickListener{

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if((getResources().getConfiguration().screenLayout & UtilsDevice.getDisplaySize()) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & UtilsDevice.getDisplaySize()) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE){
            view = inflater.inflate(R.layout.activity_diam_calculator_large, container, false);

        }else{
            view = inflater.inflate(R.layout.activity_diam_calculator, container, false);
        }
        setUpButtonsAndListeners();
        return view;
    }

    /**
     * Set up all buttons in fragment activity, create listeners, and manage 'calculate'
     * button behaviour.
     */

    private void setUpButtonsAndListeners() {
        Button calculateButton = (Button)view.findViewById(R.id.diameterCalculateButton);
        ImageButton imageButtonED = (ImageButton) view.findViewById(R.id.imageButtonED);
        ImageButton imageButtonDBL = (ImageButton) view.findViewById(R.id.imageButtonDBL);
        ImageButton imageButtonPD = (ImageButton) view.findViewById(R.id.imageButtonPD);
        calculateButton.setTransformationMethod(null);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDiamCalculateButtonClicked();
            }
        });

        imageButtonED.setOnClickListener(this);
        imageButtonDBL.setOnClickListener(this);
        imageButtonPD.setOnClickListener(this);
    }

    /**
     * Method, witch calculate and display result.
     */

    public void onDiamCalculateButtonClicked() {
        EditText edEditText = (EditText) view.findViewById(R.id.editTextED);
        EditText dblEditText = (EditText) view.findViewById(R.id.editTextDBL);
        EditText pdEditText = (EditText) view.findViewById(R.id.editTextPD);
        TextView textResult = (TextView)view.findViewById(R.id.textViewDiamResult);
        try {
            double ed = Double.parseDouble(String.valueOf(edEditText.getText()));
            double dbl = Double.parseDouble(String.valueOf(dblEditText.getText()));
            double pd = Double.parseDouble(String.valueOf(pdEditText.getText()));
            double diam = Math.ceil(ed * 2 + dbl - pd);

            String result = getResources().getString(R.string.diam_activ_textView_result_formula) +
                    String.valueOf(diam).replace(",", ".") +
                    getResources().getString(R.string.diam_activ_textView_mm);

            textResult.setText(result);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getResources().getText(R.string.diam_activ_wrong_data), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manage on query button pressed behaviour.
     */

    @Override
    public void onClick(View v) {
        int content = Integer.valueOf(String.valueOf(v.getContentDescription()));
        Intent intent = new Intent(getActivity(), GlossaryActivity.class);
        intent.putExtra(GlossaryActivity.QUERY_MARK_BUTON_ID, v.getId());
        intent.putExtra(GlossaryActivity.QUERY_MARK_ID_FOR_SQL, content);
        GlossaryActivity.isGlossaryList = false;
        startActivity(intent);
    }
}
