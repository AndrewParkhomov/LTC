package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;

public class DiamCalculatorActivity extends AppCompatActivity {

    private double ed, dbl, pd, diam;
    String result;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diam_calculator);
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
        initialiseToolbar();
    }

    private void initialiseToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(R.string.toolbar_title_diam_calc);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onButtonClicked(View view) {
        EditText edEditText = (EditText)findViewById(R.id.editTextED);
        EditText dblEditText = (EditText)findViewById(R.id.editTextDBL);
        EditText pdEditText = (EditText)findViewById(R.id.editTextPD);
        textResult = (TextView)findViewById(R.id.textViewDiamResult);
        try{
            ed = Double.parseDouble(String.valueOf(edEditText.getText()));
            dbl = Double.parseDouble(String.valueOf(dblEditText.getText()));
            pd = Double.parseDouble(String.valueOf(pdEditText.getText()));
            diam = ed * 2 + dbl - pd;
            result = getResources().getString(R.string.diam_activ_textView_result_formula)
                    + String.valueOf(diam).replace(",", ".") +
                    getResources().getString(R.string.diam_activ_textView_mm);
            textResult.setText(result);
        }catch (IllegalArgumentException e){
            Toast.makeText(this, "Something wrong with ed, dbl, pd", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("result", result);
        outState.putDouble("ed", ed);
        outState.putDouble("dbl", dbl);
        outState.putDouble("pd", pd);
    }


    public void onQueryMarkImageButtonClicked(View view) {
        //get id of button, witch was pressed by user
        int id = Integer.valueOf(String.valueOf(view.getContentDescription()));

        // get info from database
        try{
            SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
            SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
            Cursor cursor = db.query("GLOSSARY",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(id)},
                    null, null, null);
            if(cursor.moveToFirst()){
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int glossaryImageResourceId = cursor.getInt(2);
                Intent intent = new Intent(this, GlossaryActivity.class);
                intent.putExtra(GlossaryActivity.QUERY_MARK_NAME, name);
                intent.putExtra(GlossaryActivity.QUERY_MARK_DESCR, description);
                intent.putExtra(GlossaryActivity.QUERY_MARK_IMG_ID, glossaryImageResourceId);
                startActivity(intent);
            }
            cursor.close();
            db.close();
        }catch(SQLException e){
            Toast.makeText(this, getResources().getText(R.string.database_unavailable), Toast.LENGTH_SHORT).show();
        }
    }
}
