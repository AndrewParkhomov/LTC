package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

/**
 * Display detailed information about selected parameter.
 */

public class GlossaryActivity extends AppCompatActivity {

    public static boolean isGlossaryList;
    int itemId, contentDescr, imageId;
    String name, description;
    TextView setName, setDescription, addTV;
    ImageView setPicture;

    public static final String QUERY_MARK_BUTON_ID = "listId";
    public static final String QUERY_MARK_ID_FOR_SQL = "listContentDescr";

    // this final variable need for display add Text View, when user pressed into !GLOSSARYLIST!,
    // coz in GlossaryList we don't have ID of image buttons
    private final int INDEX_FROM_LISTVIEW_3 = 3, INDEX_FROM_LISTVIEW_7 = 7, INDEX_FROM_LISTVIEW_8 = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        createDetailDescription();
    }

    /**
     * From SQLite database get title, description and picture(in current language) and
     * set them into views.
     */

    private void createDetailDescription(){
        addTV = (TextView)findViewById(R.id.glossaryAddTextView);
        itemId = getIntent().getExtras().getInt(QUERY_MARK_BUTON_ID);
        contentDescr = getIntent().getExtras().getInt(QUERY_MARK_ID_FOR_SQL);
        getDataFromDatabase(contentDescr);

        setName = (TextView)findViewById(R.id.glossaryTitleTextView);
        setDescription = (TextView)findViewById(R.id.glossaryDescriptionTextView);
        setPicture = (ImageView)findViewById(R.id.glossaryImageView);

        setName.setText(name);
        setDescription.setText(description);
        setPicture.setImageResource(imageId);

        if(itemId == R.id.imageButtonCylinderPower || itemId == R.id.imageButtonEdgeThickness ||
                itemId == INDEX_FROM_LISTVIEW_3 || itemId == INDEX_FROM_LISTVIEW_7){
            addTV.setText(getResources().getText(R.string.link_to_transposition));
        }
        if(itemId == R.id.imageButtonLensDiameter || itemId == INDEX_FROM_LISTVIEW_8){
            addTV.setText(getResources().getText(R.string.link_to_diamCalcActivity));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.string_glossary));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onGlossaryTextViewClicked(View view) {
        if (itemId == R.id.imageButtonLensDiameter || itemId == INDEX_FROM_LISTVIEW_8){
            addTV.setText(getResources().getText(R.string.link_to_diamCalcActivity));
            MainActivity.isTextLinkInDatabaseClicked = true;
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (itemId == R.id.imageButtonCylinderPower || itemId == R.id.imageButtonEdgeThickness ||
                itemId == INDEX_FROM_LISTVIEW_3 || itemId == INDEX_FROM_LISTVIEW_7){
            // 12 coz number 12 in SQL is exactly transposition
            final int TRANSPOSITION_INDEX = 12;
            getDataFromDatabase(TRANSPOSITION_INDEX);
            setName.setText(name);
            setDescription.setText(description);
            setPicture.setImageResource(imageId);
            addTV.setText(null);
        }
    }

    private void getDataFromDatabase(int id) {
        LanguageManagerActivity languageManager = new LanguageManagerActivity(this);
        languageManager.setDataForDetailDescription(id);
        name = languageManager.getName();
        description = languageManager.getDescription();
        imageId = languageManager.getImageId();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressedBehaviour();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        onBackPressedBehaviour();
    }

    private void onBackPressedBehaviour(){
        if(isGlossaryList){
            isGlossaryList = false;
            startActivity(new Intent(GlossaryActivity.this, GlossaryListActivity.class));
        }else{
            this.finish();
        }
    }
}
