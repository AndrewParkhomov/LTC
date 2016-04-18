package parkhomov.andrew.lensthicknesscalculator.glossaryDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import parkhomov.andrew.lensthicknesscalculator.R;

public class GlossaryDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "glossary";
    private static final int DB_VERSION = 1;

    public GlossaryDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE GLOSSARY ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT "
                    + "DESCRIPTION TEXT "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertItem(db, "index of refraction", "description index of refraction", R.drawable.lens);
            insertItem(db, "Sphere power", "description of sphere power", R.drawable.lens);
            insertItem(db, "Cylinder power", "description of cylinder power", R.drawable.lens);
            insertItem(db, "Axis", "description of axis", R.drawable.lens);
            insertItem(db, "real base curve", "description of real base curve", R.drawable.lens);
            insertItem(db, "center thickness", "description of center thickness", R.drawable.lens);
            insertItem(db, "edge thickness", "description of edge thickness", R.drawable.lens);
            insertItem(db, "diameter", "description of diameter", R.drawable.lens);
            insertItem(db, "effective diameter", "description of ed", R.drawable.lens);
            insertItem(db, "distance between lenses", "description of dbl", R.drawable.lens);
            insertItem(db, "pupil distance", "description of pd", R.drawable.lens);
        }
    }

    private void insertItem(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues itemValues = new ContentValues();
        itemValues.put("NAME", name);
        itemValues.put("DESCRIPTION", description);
        itemValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("GLOSSARY", null, itemValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
