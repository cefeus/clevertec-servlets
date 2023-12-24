package util.constants.ru;

import lombok.experimental.UtilityClass;
import util.ReadProperties;

@UtilityClass
public class PdfUserConstants {
    public static String NAME = "Имя пользователя: ";
    public static String SURNAME = "Фамилия пользователя: ";
    public static String EMAIL = "Почта: ";
    public static String AGE = "Возраст: ";

    public static String NEW_LINE = "\n";

    public static String FONT_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\font\\DejaVuSans.ttf";
    public static String BACKGROUND_PATH = "src/main/resources/Clevertec_Template.pdf";
    public static String SAVE_PATH = "";

    public static String ENCODING = "cp1251";
}
