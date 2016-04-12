package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import parkhomov.andrew.lensthicknesscalculator.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        startActivity(new Intent(this, CalculatorActivity.class));
    }
}
