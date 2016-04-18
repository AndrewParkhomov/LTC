package parkhomov.andrew.lensthicknesscalculator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;


public class GlossaryFragment extends Fragment {

//    private String name, description;
//    private int imageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = getView();
//        if(view != null){
//            TextView setName = (TextView) view.findViewById(R.id.glossaryTitleTextView);
//            TextView setDescription = (TextView) view.findViewById(R.id.glossaryDescriptionTextView);
//            ImageView setPicture = (ImageView) view.findViewById(R.id.glossaryImageView);
//////            setName.setText(name);
//////            setDescription.setText(description);
//////            setPicture.setImageResource(imageId);
////        }

        return inflater.inflate(R.layout.fragment_glossary, container, false);
    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }
}
