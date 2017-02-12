package com.bortnikpp.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.bortnikpp.models.ThreadModel;
import org.apache.commons.validator.routines.*;
/**
 * Класс служит для проверки валидности аргументов полученых на вход программе
 * @author Bortnik Pavel
 * @version 1.0
 */
public class ArgumentValidator {

    /**
     * Метод проверяет валидность URL
     * @param str - строка содержащая URL
     * @return true - если URL валиден, false - если URL невалиден
     */
    public static boolean isValidURL (String str){
        try {
            URL url = new URL(str);
            String[] schemes = {"http", "https"};
            UrlValidator urlValidator = new UrlValidator(schemes);
            if (urlValidator.isValid(str)) {
                return true;
            }
            }catch(MalformedURLException e) {
            ThreadModel.flag = false;
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод проверяет существование файла и допустимый ли формат
     * @param str - строка содержащая имя Файла
     * @return true - если файл существует и он в нужном формате,
     * false - файл не существует или недопустимый формат
     */
    public static boolean isValidFileName(String str) {
        File file = new File(str);
        if(file.exists()&&str.endsWith(".txt")) {
            return true;
        }
        return false;
    }
}
