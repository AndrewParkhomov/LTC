package parkhomov.andrew.lensthicknesscalculator.glossaryDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import parkhomov.andrew.lensthicknesscalculator.R;

public class GlossaryDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "glossary";
    private static final String SECOND_DB_NAME = "language";
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
                    " Положение измеряется в градусах от 0° до 180°",
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
                            "а для цилиндра со знаком «+», под углом 0°, он же 180°.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Diameter", "description of diameter",
                    "Диаметр линзы","Диаметр линзы в миллиметрах. Просчитать нужный диаметр можно во " +
                            "вкладке «расчет диаметра линзы».",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Effective diameter", "description of ed",
                    "Эффективный диаметр линзы","Эффективный диаметр линзы(Effective diameter)" +
                            " - расстояние в миллиметрах между двумя самыми отдаленными точками окуляра" +
                            ", для корригирующих оправ в основном это значение в пределах 2 - 3 мм.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Distance between lenses", "description of dbl",
                    "Расстояние между линзами","Расстояние между линзами(Distance between lenses)" +
                            " - расстояние в миллиметрах между линзами(переносица).",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Pupil distance", "",
                    "Расстояние между зрачками","Расстояние между зрачками, межцентровое расстояние" +
                            "(Pupil distance) - расстояние между зрачками в миллиметрах.",
                    "Показатель преломления","описание показателя",R.drawable.lens);
            insertItem(db, "Transposition", "Ophthalmologists and a few older optometrists " +
                    "(different types of eye doctors), write astigmatism prescriptions with " +
                    "positive (+) cylinders. This is because many years ago the instruments used" +
                    " to measure and cut lenses were only able to do so in positive increments.\n" +
                    "Nowadays the majority of labs, including ours, cut lenses in the negative (-)," +
                    " and require that any positive (+) cylinder prescriptions be converted into" +
                    " the negative (-) equivalent.",
                    "Транспозиция","Оптометристы могут ставить значение цилинда как + так и -." +
                            " Это обусловлено тем, что инструменты много лет назад измеряли " +
                            "анномалию рефракции глаза только в плюсовом цилиндре, и изготавливались" +
                            "астигматические линзы тоже только в цилиндре со знаком (+), " +
                            "Сейчас в основном рецепты выписывают в минусовом цилиндре. " +
                            "Пересчитывается плюсовой цилиндр в минусовый следующим образом:" +
                            "значение цилиндра добавляется к значению сферы, и к текущему значению угла " +
                            "добавляется 90°.\n" +
                            "Примеры:\n" +
                            "sph -7.0 cyl -3.0 axis 22° можно записать как:\n" +
                            "sph -10.0 cyl +3.0 axis 112°.\n" +
                            "sph +5.75 cyl -1.25 axis 165° можно записать как:\n" +
                            "sph +4.5 cyl +1.25 axis 75°",
                    "Транспозицыя","описание транспозиция укр",R.drawable.lens);
            db.execSQL("CREATE TABLE LANGUAGE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CURRENT_LANGUAGE TEXT);");
            currentLanguage(db, "English");
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

    private void currentLanguage(SQLiteDatabase db, String language){
        ContentValues itemValues = new ContentValues();
        itemValues.put("CURRENT_LANGUAGE", language);
        db.insert("LANGUAGE", null, itemValues);
    }

}
