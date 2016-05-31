package parkhomov.andrew.lensthicknesscalculator.glossaryDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import parkhomov.andrew.lensthicknesscalculator.R;

public class GlossaryDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "glossary";
    private static final int DB_VERSION = 10;

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
                    "Показник заломлення","Показник заломлення - це величина, що характеризує" +
                            "заломлюючу силу прозорого середовища. Чим більше показник заломлення," +
                            "тим тоньшою буде лінза.",R.drawable.index_of_refraction_img);
            insertItem(db, "Sphere power", "description sphere",
                    "Сфера","Сфера — оптическая сила линзы, измеряется в диоптриях " +
                            "(обозначается D или Дптр.), применяется для коррекции аномалии рефракции.\n" +
                            "Для близорукости (миопии) используют рассеивающие линзы — " +
                            "перед числовым значением стоит знак «-».\n" +
                            "Для дальнозоркости (гиперметропии) используют собирающие линзы — " +
                            "перед числовым значением стоит знак «+».",
                    "Сфера","Сфера - це оптична сила лінзи, вимірюється в діопріях," +
                            "(позначається D чи Дптр.) застосовується для коррекції аномалії " +
                            "рефракції. Для далекозорості (гіперметропії) використовують розсіювальні " +
                            "лінзи - перед числовим значенням стоїть знак «-».\n" +
                            "Для далекозорості (гіперметропії) використовують збираючі лінзи, " +
                            "- перед числовим значенням стоїть знак «+».",R.drawable.sphere_img);
            insertItem(db, "Cylinder power", "description of cylinder power",
                    "Цилиндр","Цилиндр — оптическая сила линзы, применяемых для коррекции астигматизма.\n" +
                            "В здоровом виде роговица и хрусталик имеют сферическую форму. " +
                            "Если эта форма меняется, то при прохождении световых лучей через " +
                            "роговицу создается искаженное изображение предмета. Одни фрагменты " +
                            "изображения фокусируются на сетчатке, другие – перед ней или " +
                            "позади нее. В результате этого человек видит картинку, в которой " +
                            "присутствуют как четкие, так и размытые участки и линии." +
                            "Астигматизм может быть двух видов:\n" +
                            "   - роговичный\n" +
                            "   - хрусталиковый\n" +
                            "Также астигматизм может быть как врожденым, так и приобретенным." +
                            "Обязательно указывается положение оси цилиндра в градусах " +
                            "от 0° до 180°. Это связано с особенностями преломления света, " +
                            "проходящего через цилиндрическую линзу:\n" +
                            "   - лучи, идущие перпендикулярно оси цилиндра, преломляются;\n" +
                            "   - лучи, идущие параллельно оси, не изменяют своего направления.\n" +
                            "Такие свойства позволяют «исправить» преломление света в нужном меридиане.\n" +
                            "Значение цилиндра бывает минусовым и плюсовым.",
                    "Циліндр","Циліндр - оптична сила лінзи, що застосовується для корекції астигматизму" +
                            "При цьому обов'язково вказується положення осі циліндра у градусах" +
                            " від 0° до 180°. Це пов'язано з особливостями заломлення світла, що " +
                            "проходить через циліндричну лінзу:\n" +
                            "   - промені, що проходять перпендикулярно до осі циліндра переломлюються;\n" +
                            "   - промені, що проходять паралельно осі, не змінюються напрямок руху;\n" +
                            "Такі властивості дозволяються «виправити» переломлення світла у потрібному" +
                            " мередиані. Значення циліндра бувають мінусовими і плюсовими.",R.drawable.cylinder_img);
            insertItem(db, "Axis", "description of axis",
                    "Угол оси цилиндра(Axis)","Указывает под каким углом расположен цилиндр."+
                    " Положение измеряется в градусах от 0° до 180°",
                    "Кут осі циліндра(Axis)","Вказує, під яким кутом розташований циліндр.",R.drawable.axis_img);
            insertItem(db, "Real base curve", "description of real base curve",
                    "Реальная кривизна передней поверхности","Действительная кривизна передней " +
                            "поверхности(база линзы),измерятеся в диоприях," + "Меряется СФЕРОМЕТРОМ??"+
                    "значения в пределах от 0.001(плоская передняя " +
                            "поверхность для изголовления линз с большим отрицательным значением)" +
                            "до 10.5 (примерное значение) для изготовления линз с большим положительным " +
                            "значением.",
                    "Показатель преломления","описание показателя",R.drawable.front_curve_img);
            insertItem(db, "Center thickness", "description of center thickness",
                    "Толщина линзы по центру","Толщина линзы по ее оптическому " +
                            "центру. Измеряется толщинометром.",
                    "Показатель преломления","описание показателя",R.drawable.thickness_gauge_img);
            insertItem(db, "Edge thickness", "description of edge thickness",
                    "Толщина линзы по краю","Толщина линзы по краю. При наличии" +
                            " цилиндра со знаком «-», максимальная толщина будет под углом 90°," +
                            "а для цилиндра со знаком «+», под углом 0°, он же 180°.",
                    "Показатель преломления","описание показателя",R.drawable.edge_thickness_img);
            insertItem(db, "Diameter", "description of diameter",
                    "Диаметр линзы","Диаметр линзы в миллиметрах. Просчитать нужный диаметр можно во " +
                            "вкладке «расчет диаметра линзы».",
                    "Показатель преломления","описание показателя",R.drawable.diam_img);
            insertItem(db, "Effective diameter", "description of ed",
                    "Эффективный диаметр линзы","Эффективный диаметр линзы(Effective diameter)" +
                            " - расстояние в миллиметрах между двумя самыми отдаленными точками окуляра" +
                            ", для корригирующих оправ в основном это значение в пределах 2 - 3 мм.",
                    "Показатель преломления","описание показателя",R.drawable.ed_img);
            insertItem(db, "Distance between lenses", "description of dbl",
                    "Расстояние между линзами","Расстояние между линзами(Distance between lenses)" +
                            " - расстояние в миллиметрах между линзами(переносица).",
                    "Показатель преломления","описание показателя",R.drawable.dbl_img);
            insertItem(db, "Pupil distance", "",
                    "Расстояние между зрачками","Расстояние между зрачками, межцентровое расстояние" +
                            "(Pupil distance) - расстояние между зрачками в миллиметрах.",
                    "Показатель преломления","описание показателя",R.drawable.pd_img);
            insertItem(db, "Transposition", "Ophthalmologists and a few older optometrists " +
                    "(different types of eye doctors), write astigmatism prescriptions with " +
                    "positive (+) cylinders. This is because many years ago the instruments used" +
                    " to measure and cut lenses were only able to do so in positive increments.\n" +
                    "Nowadays the majority of labs, including ours, cut lenses in the negative (-)," +
                    " and require that any positive (+) cylinder prescriptions be converted into" +
                    " the negative (-) equivalent.",
                    "Транспозиция","Оптометристы могут ставить значение цилинда как + так и -." +
                            " Это обусловлено тем, что инструменты много лет назад измеряли " +
                            "анномалию рефракции глаза только в плюсовом цилиндре, и изготавливались " +
                            "астигматические линзы тоже только в цилиндре со знаком +, " +
                            "Сейчас в основном рецепты выписывают в минусовом цилиндре. " +
                            "Пересчитывается плюсовой цилиндр в минусовый следующим образом:" +
                            "значение цилиндра добавляется к значению сферы, и к текущему значению угла " +
                            "добавляется 90°.\n" +
                            "Примеры:\n" +
                            "sph -7.0 cyl -3.0 axis 22° можно записать как:\n" +
                            "sph -10.0 cyl +3.0 axis 112°.\n" +
                            "sph +5.75 cyl -1.25 axis 165° можно записать как:\n" +
                            "sph +4.5 cyl +1.25 axis 75°",
                    "Транспозицыя","описание транспозиция укр",R.drawable.transposition_img);
            db.execSQL("CREATE TABLE LANGUAGE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CURRENT_LANGUAGE TEXT);");
            currentLanguage(db, "English");
        }
//        if(oldVersion < DB_VERSION){
//            ContentValues cv = new ContentValues();
//            cv.put("IMAGE_RESOURCE_ID", R.drawable.diagram);
//            db.update("GLOSSARY",cv,"_id=2",null);
//        }
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
