package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

/**
 * Rate application class. Rate this app in google play.
 */
public class RateAppActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_the_app);
        setTitle(R.string.rate_dialog_title);
        TextView yesButton = (TextView) findViewById(R.id.rate_dialog_yes);
        TextView noButton = (TextView) findViewById(R.id.rate_dialog_no);
        if(yesButton != null && noButton != null){
            yesButton.setOnClickListener(this);
            noButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rate_dialog_yes:
                String appPackage = this.getPackageName();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackage));
                startActivity(intent);
                break;
            case R.id.rate_dialog_no:
                this.finish();
                break;
        }
    }
}


