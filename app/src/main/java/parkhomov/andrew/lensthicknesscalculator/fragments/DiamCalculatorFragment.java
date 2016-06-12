package parkhomov.andrew.lensthicknesscalculator.fragments;

import android.content.Intent;
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

public class DiamCalculatorFragment extends Fragment implements View.OnClickListener{

    private double ed, dbl, pd, diam;
    String result;
    TextView textResult;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_diam_calculator, container, false);
        textResult = (TextView)view.findViewById(R.id.textViewDiamResult);
        if(savedInstanceState != null){
            result = savedInstanceState.getString("result");
            ed = savedInstanceState.getDouble("ed");
            ed = savedInstanceState.getDouble("dbl");
            ed = savedInstanceState.getDouble("pd");
            result = getResources().getString(R.string.diam_activ_textView_result_formula)
                    + String.valueOf(diam).replace(",", ".") +
                    getResources().getString(R.string.diam_activ_textView_mm);
            textResult.setText(result);
        }
        setUpButtonsAndListeners();
        return view;
    }

    private void setUpButtonsAndListeners() {
        Button calculateButton = (Button)view.findViewById(R.id.diameterCalculateButton);
        ImageButton imageButtonED = (ImageButton) view.findViewById(R.id.imageButtonED);
        ImageButton imageButtonDBL = (ImageButton) view.findViewById(R.id.imageButtonDBL);
        ImageButton imageButtonPD = (ImageButton) view.findViewById(R.id.imageButtonPD);
        calculateButton.setOnClickListener(this);
        imageButtonED.setOnClickListener(this);
        imageButtonDBL.setOnClickListener(this);
        imageButtonPD.setOnClickListener(this);
    }

    public void onDiamCalculateButtonClicked() {
        EditText edEditText = (EditText) view.findViewById(R.id.editTextED);
        EditText dblEditText = (EditText) view.findViewById(R.id.editTextDBL);
        EditText pdEditText = (EditText) view.findViewById(R.id.editTextPD);
        try {
            ed = Double.parseDouble(String.valueOf(edEditText.getText()));
            dbl = Double.parseDouble(String.valueOf(dblEditText.getText()));
            pd = Double.parseDouble(String.valueOf(pdEditText.getText()));
            diam = ed * 2 + dbl - pd;
            result = getResources().getString(R.string.diam_activ_textView_result_formula)
                    + String.valueOf(diam).replace(",", ".") +
                    getResources().getString(R.string.diam_activ_textView_mm);
            textResult.setText(result);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getResources().getText(R.string.diam_activ_wrong_EdDblPd), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        boolean isCalculationPressed = false;
        Intent intent = new Intent(getActivity(), GlossaryActivity.class);
        switch(v.getId()){
            case R.id.diameterCalculateButton:
                isCalculationPressed = true;
                onDiamCalculateButtonClicked();
                break;
            case R.id.imageButtonED:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 9);
                break;
            case R.id.imageButtonDBL:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 10);
                break;
            case R.id.imageButtonPD:
                intent.putExtra(GlossaryActivity.QUERY_MARK_LISTNUMBER_ID, 11);
                break;
        }
        if(!isCalculationPressed){
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("result", result);
        outState.putDouble("ed", ed);
        outState.putDouble("dbl", dbl);
        outState.putDouble("pd", pd);
    }
}
