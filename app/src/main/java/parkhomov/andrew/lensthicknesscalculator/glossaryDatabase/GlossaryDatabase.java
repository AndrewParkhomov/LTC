package parkhomov.andrew.lensthicknesscalculator.glossaryDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
            insertItem(db, "Index of refraction", "In optics, the refractive index or index of " +
                    "refraction of a material is a dimensionless number that describes how light " +
                    "propagates through that medium. It is defined as n = c / v, where c is the speed " +
                    "of light in vacuum and v is the phase velocity of light in the medium. " +
                    "For example, the refractive index of water is 1.333, meaning that light " +
                    "travels 1.333 times faster in a vacuum than it does in water. © Wikipedia",
                    "Показатель преломления","Показатель преломления - это величина, характеризующая" +
                            " преломляющую силу прозрачной среды (обозначается n).\n" +
                            "Чем больше показатель преломления, тем тоньше линза.",
                    "Показник заломлення","Показник заломлення - це величина, яка характеризує " +
                            "заломлюючу силу прозорого середовища (позначається n).\n" +
                            "Чим вищий показник заломлення, тим тоншою є лінза.",R.drawable.index_of_refraction_img);
            insertItem(db, "Sphere power", "Spherical correction corrects refractive error of the " +
                    "eye with a single convergent or divergent refractive power in all meridians.",
                    "Сфера","Сфера — оптическая сила линзы, измеряется в диоптриях, " +
                            "обозначается D или Дптр., применяется для коррекции аномалии рефракции.\n" +
                            "Для близорукости (миопии) используют рассеивающие линзы — " +
                            "перед числовым значением стоит знак «-».\n" +
                            "Для дальнозоркости (гиперметропии) используют собирающие линзы — " +
                            "перед числовым значением стоит знак «+».",
                    "Сфера","Сфера - це оптична сила лінзи, вимірюється в діоптріях, " +
                            "позначається D чи Дптр., застосовується для корекції аномалії " +
                            "рефракції.\n Для далекозорості (гіперметропії) використовують розсіювальні " +
                            "лінзи - перед числовим значенням стоїть знак «-».\n" +
                            "Для далекозорості (гіперметропії) використовують збираючі лінзи " +
                            "— перед числовим значенням стоїть знак «+».",R.drawable.sphere_img);
            insertItem(db, "Cylinder power", "Cylindrical correction corrects astigmatic refractive " +
                    "error of the eye by adding or subtracting power cylindrically in a meridian " +
                    "specified by the prescribed axis.",
                    "Цилиндр","Цилиндр — оптическая сила линзы, применяемая для коррекции астигматизма.\n" +
                            "В здоровом виде роговица и хрусталик имеют сферическую форму. " +
                            "Если эта форма меняется, то при прохождении световых лучей через " +
                            "роговицу создается искаженное изображение предмета. Одни фрагменты " +
                            "изображения фокусируются на сетчатке, другие — перед ней или " +
                            "позади нее. В результате этого человек видит картинку, в которой " +
                            "присутствуют как четкие, так и размытые участки и линии.\n" +
                            "Астигматизм может быть двух видов:\n" +
                            "   — роговичный\n" +
                            "   — хрусталиковый\n" +
                            "Также астигматизм может быть как врожденым, так и приобретенным." +
                            "При астигматизме, в рецепте обязательно указывается положение оси цилиндра в градусах — " +
                            "от 1° до 180°(он же 0°). Это связано с особенностями преломления света, " +
                            "проходящего через цилиндрическую линзу:\n" +
                            "   — лучи, идущие перпендикулярно оси цилиндра, преломляются;\n" +
                            "   — лучи, идущие параллельно оси, не изменяют своего направления.\n" +
                            "Такие свойства позволяют «исправить» преломление света в нужном меридиане. " +
                            "Значение цилиндра бывает минусовым и плюсовым(см. Транспозиция).",
                    "Циліндр","Циліндр - оптична сила лінзи, яка застосовується для корекції астигматизму.\n" +
                            "У здоровому вигляді рогівка і кришталик мають сферичну форму. Якщо ця форма " +
                            "змінюється, то при проходженні світлових променів через рогівку створюється " +
                            "викривлене зображення предмета. Одні фрагменти зображення фокусуються на сітківці, " +
                            "інші - перед нею, або поза нею. У результаті цього людина бачить картинку, " +
                            "в якій присутні як чіткі, так і розмиті ділянки і лінії.\n" +
                            "Астигматизм може бути двох видів:\n" +
                            "   — рогівковий\n" +
                            "   — кришталиковий\n" +
                            "Також астигматизм може бути як вродженим, так і набутим. " +
                            "При астигматизмі, у рецепті обов'язково вказується положення осі циліндра у градусах — " +
                            "від 1° до 180°(він же 0°). Це пов'язано з відмінностями заломлення світла, яке " +
                            "проходить крізь циліндричну лінзу:\n" +
                            "   — промені, які проходять перпендикулярно осі циліндра переломлюються\n" +
                            "   — промені, які проходять паралельно осі, не змінюють напрямок руху\n" +
                            "Такі властивості дозволяють «виправити» переломлення світла у потрібному" +
                            " меридіані. Значення циліндра бувають мінусовими і плюсовими(див. Транспозиція).",
                    R.drawable.cylinder_img);
            insertItem(db, "Axis", "Axis is present only if there is a value for cylinder. This " +
                    "indicates the angle in degrees of one of two major meridians the prescribed " +
                    "cylindrical power is in. Which major meridian is referenced is indicated by " +
                    "the cylindrical correction being in plus or minus notation.",
                    "Угол оси цилиндра","Указывает, под каким углом расположена ось цилиндра. "+
                            "Положение оси измеряется в градусах от 1° до 180°(он же 0°).",
                    "Кут осі циліндра","Вказує, під яким кутом розташована вісь циліндра. " +
                            "Положення осі вимірюється у градусах від 1° до 180°(він же 0°).",R.drawable.axis_img);
            insertItem(db, "Real base curve", "The base curve (usually determined from the profile " +
                    "of the front surface of an ophthalmic lens) can be changed to result in the " +
                    "best optic and cosmetic characteristics across the entire surface of the lens. " +
                    "Optometrists may choose to specify a particular base curve when prescribing a " +
                    "corrective lens for either of these reasons. A multitude of mathematical formulas " +
                    "and professional clinical experience has allowed optometrists and lens designers " +
                    "to determine standard base curves that are ideal for most people. As a result," +
                    " the front surface curve is more standardized and the characteristics that " +
                    "generate a person's unique prescription are typically derived from the geometry " +
                    "of the back surface of the lens.",
                    "Кривизна передней поверхности","Действительная кривизна передней " +
                            "поверхности(база линзы), измеряется в диоптриях(в этом приложении), или  в " +
                            "миллиметрах, измеряется сферометром. Значения в пределах от 0.001(плоская передняя " +
                            "поверхность) для изготовления линз с большим отрицательным значением" +
                            "('минусовых' линз), до 10.5(примерное значение), для изготовления линз " +
                            "с большим положительным значением('плюсовых' линз).",
                    "Кривизна передньої поверхні","Дійсна кривизна передньої поверхні(база лінзи)" +
                            ", вимірюється в діоптріях(у цьому додатку), або в міліметрах, вимірюється " +
                            "сферометром. Значення у межах від 0.001(плоска передня поверхня) для " +
                            "виготовлення лінз з великим негативним значенням('мінусових' лінз), " +
                            "до 10.5(приблизне значення), для виготовлення лінз з великим позитивним " +
                            "значенням('плюсових' лінз).",R.drawable.front_curve_img);
            insertItem(db, "Center thickness", "Thickness in optical center of the lens in millimeters.",
                    "Толщина линзы по центру","Толщина линзы в ее оптическом центре. Для 'плюсовых' линз наибольшая " +
                            "толщина будет в оптическом центре, для 'минусовых' наоборот: наименьшая " +
                            "толщина будет в оптическом центре линзы. Толщина измеряется толщинометром.",
                    "Товщина лінзи по центру","Товщина лінзи в її оптичному центрі. Для 'плюсових'" +
                            "лінз найбільша товщина буде в оптичному центрі, а для 'мінусових' " +
                            "навпаки: найменша товщина буде у оптичному центрі лінзи. Товщина " +
                            "вимірюється товщинометром.",R.drawable.thickness_gauge_img);
            insertItem(db, "Edge thickness", "Thickness of the lens in edge, expressed in millimeters.",
                    "Толщина линзы по краю","Для 'плюсовых' линз минимальная толщина будет по краю," +
                            " а для 'минусовых', соответственно, минимальная толщина будет по центру, " +
                            "а максимальная - по краю. " +
                            "Следует отметить, что для оправ 'на леске' и 'на винтах' (для 'плюсовых'" +
                            " линз), толщина по краю будет в среднем на 1.5 мм больше чем для " +
                            "ободковых(рамочных) оправ. Это связано с тем, что для нарезания " +
                            "канавки для лески необходимо около 2 мм(для металлической лески 2.5 - 3.0 мм), " +
                            "для оправ 'на винтах' необходимы те же 2 мм 'тела' линзы для того, " +
                            "чтобы линза не ломалась при малейшей нагрузке. Для оправ 'на винтах'" +
                            " лучше всего использовать поликарбонат, или же высокоиндексные материалы" +
                            "(1.61, 1.67, 1.74), так как они более прочные в сравнении с низкоиндексными." +
                            "Так же необходимо учитывать, что у астигматических линз со знаком " +
                            "цилиндра «-» максимальная толщина будет под углом 90°, " +
                            "а для линз со знаком цилиндра «+» - под углом 0°, он же 180°" +
                            "(см. Транспозиция).\n" +
                            "P.S. Если одним из основных критериев при подборе очков является толщина линз, " +
                            "то следует выбирать небольшую ободковую оправу и высокий показатель" +
                            " преломления линзы.",
                    "Товщина лінзи по краю","Для 'плюсових' лінз мінімальна товщина буде по краю, " +
                            "а для 'миінусових', відповідно, мінімальна товщина буде по центру, " +
                            "а максимальна - по краю. Слід зауважити, що для оправ 'на жилці' та" +
                            " 'на гвинтах'(для 'плюсових' лінз), товщина по краю буде в середньому" +
                            "на 1.5 мм більша ніж в ободкових(рамочних) оправах. Це пов'язано з тим, " +
                            "що для нарізання канавки для жилки необхідно близько 2 мм(для металевої " +
                            "жилки 2.5 - 3.0 мм), для оправ 'на гвинтах' необхідні ті ж 2 мм 'тіла'" +
                            " лінзи для того, щоб лінза не ламалася при найменших навантаженнях. " +
                            "Для оправ 'на гвинтах' найкраще всього застосувати полікарбонат," +
                            "або ж високоіндексні матеріали(1.61, 1.67, 1.74), оскільки вони міцніші" +
                            " порівняно з низькоіндексними. Також необхідно враховувати, що у " +
                            "астигматичних лінз із знаком циліндра «-» максимальна товщина буде " +
                            "під кутом 90°, а для лінз із знаком циліндра «+» - під кутом 0°, він же 180°" +
                            "(див. Транспозиція).\n" +
                            "P.S. Якщо одним із головних критеріїв при підборі окулярів є товщина " +
                            "лінз, то слід вибирати невелику ободкову оправу та високий показник" +
                            " заломлення лінзи.",R.drawable.edge_thickness_img);
            insertItem(db, "Diameter", "Diameter of the lens in millimeters. You can calculate lens " +
                    "diameter in menu 'Diameter calculator'.",
                    "Диаметр линзы","Диаметр линзы в миллиметрах. Чем больше диаметр - тем больше " +
                            "толщина линзы. Рассчитать нужный диаметр можно в меню «расчет диаметра линзы».",
                    "Діаметр лінзи","Діаметр лінзи в міліметрах. Чим більший діаметр - тим більша " +
                            "товщина лінзи. Розрахувати необхідний діаметр можна в меню " +
                            "«розрахунок діаметра лінзи».",
                    R.drawable.diam_img);
            insertItem(db, "Effective diameter", "Effective diameter is a longest diagonal across the lens.",
                    "Эффективный диаметр","Эффективный диаметр окуляра(Effective diameter)" +
                            " - это расстояние в миллиметрах между двумя самыми отдаленными точками окуляра" +
                            "(наибольшая диагональ). В основном, для корригирующих оправ, это значение превышает " +
                            "горизонтальный размер(А) на 2 - 3 мм.",
                    "Ефективний діаметр ","Ефективний діаметр окуляра(Effective diameter)" +
                            " - це відстань в міліметрах між двома найвіддаленішими точками окуляра " +
                            "(найбільша діагональ). Здебільшого, для коригуючих оправ, це значення " +
                            "перевищує горизонтальний розмір(А) на 2 - 3 мм.",R.drawable.ed_img);
            insertItem(db, "Distance between lenses", "The shortest distance in millimeters between " +
                    "the nasal edges of each lens or the distance between boxes. DBL is also commonly " +
                    "referred to as bridge size.",
                    "Расстояние между линзами","Расстояние между линзами(Distance between lenses)" +
                            " - это расстояние в миллиметрах между линзами(переносица).",
                    "Відстань між лінзами","Відстань між лінзами(Distance between lenses)" +
                            " - це відстань в міліметрах між лінзами(перенісся).",R.drawable.dbl_img);
            insertItem(db, "Pupil distance", "Pupil Distance is the distance (expressed in millimeters)" +
                    " between the centers of the pupils.",
                    "Расстояние между зрачками","Расстояние между зрачками, межцентровое расстояние" +
                            "(Pupil distance) - это расстояние между зрачками в миллиметрах.",
                    "Відстань між зіницями","Відстань між зіницями, міжцентрова відстань(Pupil distance)" +
                            " - це відстань між зіницями в міліметрах.",R.drawable.pd_img);
            insertItem(db, "Transposition", "Ophthalmologists and a few older optometrists " +
                            "(different types of eye doctors), write astigmatism prescriptions with " +
                            "positive (+) cylinders. This is because many years ago the instruments used" +
                            " to measure and cut lenses were only able to do so in positive increments.\n" +
                            "Nowadays the majority of labs, including ours, cut lenses in the negative (-)," +
                            " and require that any positive (+) cylinder prescriptions be converted into" +
                            " the negative (-) equivalent.",
                    "Транспозиция","Оптометристы могут ставить значение цилиндра как «+», так и «-»." +
                            " Это обусловлено тем, что инструменты много лет назад измеряли " +
                            "аномалию рефракции глаза только в 'плюсовом' цилиндре, и изготавливались " +
                            "астигматические линзы тоже только в цилиндре со знаком «+». " +
                            "Сейчас, в основном, рецепты выписывают в минусовом цилиндре. " +
                            "Пересчитывается 'плюсовой' цилиндр в 'минусовый'(и наоборот) следующим образом: " +
                            "значение цилиндра добавляется к значению сферы, и к текущему значению угла " +
                            "добавляется 90°.\n" +
                            "Примеры:\n" +
                            "   sph -7.0 cyl -3.0 axis 22° можно записать как:\n" +
                            "   sph -10.0 cyl +3.0 axis 112°.\n" +
                            "   sph +5.75 cyl -1.25 axis 165° можно записать как:\n" +
                            "   sph +4.5 cyl +1.25 axis 75°",
                    "Транспозиція","Оптометристи можуть ставити значення циліндра як «+», так і «-»." +
                            " Це обумовлено тим, що інструменти багато років тому вимірювали аномалію" +
                            " рефракції ока тільки у 'плюсовому' циліндрі, та виготовлялись астигматичні " +
                            "лінзи теж тільки у циліндрі із знаком «+». Зараз рецепти виписують, головним" +
                            " чином, у мінусовому циліндрі. Перераховується 'плюсовий' циліндр у " +
                            "'мінусовий'(і навпаки) наступним чином: значення циліндра добавляється " +
                            "до значення сфери, та до поточного значення кута додається 90°.\n" +
                            "Приклади:\n" +
                            "   sph -7.0 cyl -3.0 axis 22° можна записати як:\n" +
                            "   sph -10.0 cyl +3.0 axis 112°.\n" +
                            "   sph +5.75 cyl -1.25 axis 165° можна записати як:\n" +
                            "   sph +4.5 cyl +1.25 axis 75°",R.drawable.transposition_img);
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
