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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE GLOSSARY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME_ENG TEXT, "
                    + "DESCRIPTION_ENG TEXT, "
                    + "NAME_RUS TEXT, "
                    + "DESCRIPTION_RUS TEXT, "
                    + "NAME_UKR TEXT, "
                    + "DESCRIPTION_UKR TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertItem(db, "Index of refraction", "description index of refraction",
                    "Показатель преломления","описание показателя",
                    "Показатель преломления ukr","описание показателя ukr",R.drawable.lens);
            insertItem(db, "Sphere power", "description of sphere power",
                    "Cfera","opicanie spheru",
                    "cathf ukr","описание shpere ukr",R.drawable.lens);
            insertItem(db, "Cylinder power", "description of cylinder power",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Axis", "description of axis",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Real base curve", "description of real base curve",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Center thickness", "description of center thickness",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Edge thickness", "description of edge thickness",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Diameter", "description of diameter",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Effective diameter", "description of ed",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Distance between lenses", "description of dbl",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Pupil distance", "description of pd",
                    "Cfera","opicanie spheru",
                    "Показатель преломления","описание показателя",R.drawable.lens);
        }
    }

    private void insertItem(SQLiteDatabase db,
                            String nameEng, String descriptionEng,
                            String nameRus, String descriptionRus,
                            String nameUkr, String descriptionUkr,
                            int imageId){
        ContentValues itemValues = new ContentValues();
        itemValues.put("NAME_ENG", nameEng);
        itemValues.put("DESCRIPTION_ENG", descriptionEng);
        itemValues.put("NAME_RUS", nameRus);
        itemValues.put("DESCRIPTION_RUS", descriptionRus);
        itemValues.put("NAME_UKR", nameUkr);
        itemValues.put("DESCRIPTION_UKR", descriptionUkr);
        itemValues.put("IMAGE_RESOURCE_ID", imageId);
        db.insert("GLOSSARY", null, itemValues);
    }

}
