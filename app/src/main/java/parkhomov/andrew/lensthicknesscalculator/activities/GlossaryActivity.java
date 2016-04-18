package parkhomov.andrew.lensthicknesscalculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class GlossaryActivity extends AppCompatActivity {

    public static final String QUERY_MARK_NAME = "name";
    public static final String QUERY_MARK_DESCR = "descr";
    public static final String QUERY_MARK_IMG_ID = "imgId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        TextView setName = (TextView)findViewById(R.id.glossaryTitleTextView);
        TextView setDescription = (TextView)findViewById(R.id.glossaryDescriptionTextView);
        ImageView setPicture = (ImageView)findViewById(R.id.glossaryImageView);
        String name = getIntent().getStringExtra(QUERY_MARK_NAME);
        String description = getIntent().getStringExtra(QUERY_MARK_DESCR);
        int imageId = getIntent().getExtras().getInt(QUERY_MARK_IMG_ID);
        setName.setText(name);
        setDescription.setText(description);
        setPicture.setImageResource(imageId);
    }


}
