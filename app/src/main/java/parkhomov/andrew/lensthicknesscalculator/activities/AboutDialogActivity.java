package parkhomov.andrew.lensthicknesscalculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import parkhomov.andrew.lensthicknesscalculator.R;

public class AboutDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dialog);
        TextView tv = (TextView)findViewById(R.id.textViewAboutDialog);
        String text = getResources().getString(R.string.version)+"\n\n"+
                getResources().getString(R.string.aboutLTCdeveloper)+"\n"+
                getResources().getString(R.string.aboutLTCemail)+"\n\n"+
                getResources().getString(R.string.copyright)+" "+
                getResources().getString(R.string.year);
        tv.setTextColor(0xDE000000);
        tv.setText(text);
        //disable outside touches in dialog activity
        this.setFinishOnTouchOutside(false);
    }
}
