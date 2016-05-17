package parkhomov.andrew.lensthicknesscalculator.activities;


import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;

public class LanguageActivity extends ListActivity {

    ListView listView;
    public static Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.language_list)
        );
        listView.setAdapter(adapter);
        customizeListiew();
    }

    private void customizeListiew() {
        listView.setDivider(new ColorDrawable(0xDE000000));
        listView.setDividerHeight(1);
        listView.setPadding(16, 16, 16, 16);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Configuration config;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        switch (position) {
            case 0:
                locale = new Locale("en-gb");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                startActivity(intent);
                break;
            case 1:
                locale = new Locale("ru");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                startActivity(intent);
                break;
            case 2:
                locale = new Locale("uk");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                startActivity(intent);
                break;
        }
    }
}
