package parkhomov.andrew.lensthicknesscalculator.activities.fragment.dialog;

import android.app.Activity;
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

/**
 * Created by MyPC on 29.07.2017.
 */

public class Result extends DialogFragment {

    @BindView(R.id.centerThicknessTxtV)
    TextView centerThicknessHolder;
    @BindView(R.id.minimumEdgeThicknessTxtV)
    TextView minimumThicknessHolder;
    @BindView(R.id.maximumEdgeThicknessTxtV)
    TextView maximumThicknessHolder;
    @BindView(R.id.dymanicAxisEdgeThicknessTxtV)
    TextView onAxisThicknessHolder;
    @BindView(R.id.include_3)
    View divider_3;
    @BindView(R.id.include_4)
    View divider_4;

    private String center, minimum, maximum, onAxis, axis;
    private Activity activity;

    public Result() {
    }

    public static Result getInstance(String center, String minimum, String maximum, String thkOnAxis, String axis) {
        Bundle bundle = new Bundle();
        Result alertDialog = new Result();
        alertDialog.setArguments(bundle);
        alertDialog.setCenter(center);
        alertDialog.setMinimum(minimum);
        alertDialog.setMaximum(maximum);
        alertDialog.setOnAxis(thkOnAxis);
        alertDialog.setAxis(axis);
        return alertDialog;
    }

    public static Result getInstance(String center, String minimum) {
        Bundle bundle = new Bundle();
        Result alertDialog = new Result();
        alertDialog.setArguments(bundle);
        alertDialog.setCenter(center);
        alertDialog.setMinimum(minimum);
        return alertDialog;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public void setOnAxis(String onAxis) {
        this.onAxis = onAxis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_result, container);
        ButterKnife.bind(this, view);
        activity = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white);

        setUpViewsAndListeners();

        return view;
    }

    private void setUpViewsAndListeners() {
        centerThicknessHolder.setText(String.format("%s %s %s", activity.getString(R.string.result_center_thickness), center,activity.getString(R.string.result_mm)));
        centerThicknessHolder.setText(String.format("%s %s %s", activity.getString(R.string.result_center_thickness), center, activity.getString(R.string.result_mm)));
        minimumThicknessHolder.setText(String.format("%s %s %s", activity.getString(R.string.result_min_edge_thickness), minimum,activity.getString(R.string.result_mm)));
        if (maximum != null && !maximum.equals("")) {
            maximumThicknessHolder.setText(String.format("%s %s %s", activity.getString(R.string.result_max_edge_thickness), maximum,activity.getString(R.string.result_mm)));
        } else {
            maximumThicknessHolder.setVisibility(View.GONE);
            divider_3.setVisibility(View.GONE);
        }
        if (onAxis != null && !onAxis.equals("")) {
            onAxisThicknessHolder.setText(String.format("%s %s°: %s %s", activity.getString(R.string.result_on_axis_thickness), axis, onAxis,activity.getString(R.string.result_mm)));
        } else {
            onAxisThicknessHolder.setVisibility(View.GONE);
            divider_4.setVisibility(View.GONE);
        }
    }
}