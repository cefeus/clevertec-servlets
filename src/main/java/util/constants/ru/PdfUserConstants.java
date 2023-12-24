package util.constants.ru;

import lombok.experimental.UtilityClass;
import util.ReadProperties;

import java.nio.file.Path;

@UtilityClass
public class PdfUserConstants {
    public static String NAME = "Имя пользователя: ";
    public static String SURNAME = "Фамилия пользователя: ";
    public static String EMAIL = "Почта: ";
    public static String AGE = "Возраст: ";

    public static String NEW_LINE = "\n";

    public static String FONT_PATH =  "font/arial.ttf";
    public static String BACKGROUND_PATH = "Clevertec_Template.pdf";
    public static String SAVE_PATH = "";

    public static String ENCODING = "cp1251";
}
