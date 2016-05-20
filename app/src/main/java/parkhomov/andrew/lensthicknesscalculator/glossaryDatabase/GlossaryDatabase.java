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
            insertItem(db,"name eng","descr eng","name rus", "descr rus","name urk","descr ukr",R.drawable.lens);
            insertItem(db, "Index of refraction", "description index of refraction",
                    "Показатель преломления","Показатель преломленя - это величина, характеризующий" +
                            " преломляющую силу прозрачной среды(обозначается n)." +
                            " Чем больше показатель преломления, тем тоньше будет линза.",
                    "Показник заломлення","Показник заломлення - це коефіцієнт, що характеризує" +
                            "заломлюючу силу прозорого середовища. Чим більше показник заломлення," +
                            "тим тоньшою буде лінза.",R.drawable.lens);
            insertItem(db, "Sphere power", "description of sphere power",
                    "Сфера","Сфера — оптическая сила линзы, выражаемая в диоптриях " +
                            "(обозначается D или Дптр.), необходимая для коррекции аномалии рефракции.\n" +
                            "Для близорукости (миопии) используют рассеивающие линзы — " +
                            "перед числовым значением стоит знак «-».\n" +
                            "Для дальнозоркости (гиперметропии) используют собирающие линзы — " +
                            "перед числовым значением стоит знак «+».",
                    "cathf ukr","описание shpere ukr",R.drawable.lens);
            insertItem(db, "Cylinder power", "description of cylinder power",
                    "Цилиндр","Цилиндр — оптическая сила линз, применяемых для коррекции астигматизма.\n" +
                            "Такая аномалия исправляется цилиндрическими линзами. " +
                            "При этом обязательно указывается положение оси цилиндра в градусах " +
                            "от 0 до 180. Это связано с особенностями преломления света, " +
                            "проходящего через цилиндрическую линзу:\n" +
                            "лучи, идущие перпендикулярно оси цилиндра, преломляются;\n" +
                            "лучи, идущие параллельно оси, не изменяют своего направления.\n" +
                            "Такие свойства позволяют «исправить» преломление света в нужном конкретном меридиане.\n" +
                            "Значение цилиндра бывает минусовым и плюсовым.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Axis", "description of axis",
                    "Угол оси цилиндра","Указывает под каким углом расположен цилиндр."+
                    " Положение измеряется в градусах от 0 до 180",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Real base curve", "description of real base curve",
                    "Реальная кривизна передней поверхности","Действительная кривизна передней " +
                            "поверхности(база линзы),измерятеся в диоприях," + "Меряется СФЕРОМЕТРОМ??"+
                    "значения в пределах от 0.001(плоская передняя " +
                            "поверхность для изголовления линз с большим отрицательным значением)" +
                            "до 10.5 (примерное значение) для изготовления линз с большим положительным " +
                            "значением.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Center thickness", "description of center thickness",
                    "Толщина линзы по центру","Толщина линзы по ее оптическому " +
                            "центру. Измеряется толщинометром.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Edge thickness", "description of edge thickness",
                    "Толщина линзы по краю","Толщина линзы по краю. При наличии" +
                            " цилиндра со знаком «-», максимальная толщина будет под углом 90°," +
                            "а для цилиндра со знаком «+», под углом 0°, он же 180°. СМ ТРАНСПОЗИЦИЯ",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Diameter", "description of diameter",
                    "Диаметр линзы","Диаметр линзы в миллиметрах. Просчитать нужный диаметр можно во " +
                            "вкладке «расчет диаметра линзы».",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Effective diameter", "description of ed",
                    "Эффективный диаметр линзы","Эффективный диаметр линзы(Effective diameter)" +
                            " - расстояние в миллиметрах от оптического центра до " +
                            "самой дальней точки линзы.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Distance between lenses", "description of dbl",
                    "Расстояние между линзами","Расстояние между линзами(Distance between lenses)" +
                            " - расстояние в миллиметрах между линзами(переносица).",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Pupil distance", "",
                    "Расстояние между зрачками","Расстояние между зрачками, межцентровое расстояние" +
                            "(Pupil distance) - расстояние между зрачками в миллиметрах.",
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
