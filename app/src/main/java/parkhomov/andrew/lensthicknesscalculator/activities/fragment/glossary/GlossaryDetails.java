package parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;

/**
 * Created by MyPC on 29.07.2017.
 */

public class GlossaryDetails extends Parent {

    @BindView(R.id.glossaryImageView)
    ImageView imageHolder;
    @BindView(R.id.glossaryTitleTextView)
    TextView titleHolder;
    @BindView(R.id.glossaryDescriptionTextView)
    TextView descriptionHolder;
    @BindView(R.id.header)
    TextView header;

    private String title, description;
    private int imageId;

    public static GlossaryDetails getInstance(String title, String desrciption, Integer imageId) {
        Bundle bundle = new Bundle();
        GlossaryDetails glossaryDetails = new GlossaryDetails();
        glossaryDetails.setArguments(bundle);
        glossaryDetails.setTitle(title);
        glossaryDetails.setDescription(desrciption);
        glossaryDetails.setImageId(imageId);
        return glossaryDetails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.glossary_detail, container, false);
        ButterKnife.bind(this, view);

        header.setText(Utils.spacing(title.toUpperCase(), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_8));
        //set data in holder
        imageHolder.setImageDrawable(ContextCompat.getDrawable(getActivity(), imageId));
        titleHolder.setText(title);
        descriptionHolder.setText(description);

        return view;
    }

    @OnClick(R.id.turnBackImgB)
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

}
