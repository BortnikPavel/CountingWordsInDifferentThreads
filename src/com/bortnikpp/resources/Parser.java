package com.bortnikpp.resources;

import com.bortnikpp.models.ThreadModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс служит для обработки строк и подсчета слов
 * @author Bortnik Pavel
 * @version 1.0
 */
public class Parser {
    /**Объект для синхронизации*/
    public static final Object lock = new Object();
    /**Карта для подсчета слов*/
    private volatile static Map<String, Integer> map = new HashMap<>(1000);
    /**Массив слов получаемых из строки*/
    private volatile String[] currentString;
    /**Регулярное выражение для проверки строки на валидность*/
    private static final String VALIDATOR = "^[а-яА-ЯёЁ0-9«\\t\\n»_.?,\\s!-:;\"]{1,}$";
    /**Регулярное выражение для разбиения строки на слова*/
    private static final String SPLIT = "[,.!?«»:;\\s\\n\\t0-9]{1,}";

    /**
     * Метод для проверки валидности строки, разбиения строки на слова и подсчета слов
     * @param str - строка для обработки
     * @throws IOException
     */
    public void parseLine(String str) throws IOException {
        Pattern p = Pattern.compile(VALIDATOR);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            currentString = str.split(SPLIT);
        }else {
            ThreadModel.flag = false;
            throw new IOException("file contains invalid characters!");
        }
        synchronized(lock) {
            for (int i = 0; i < currentString.length && ThreadModel.flag; i++){
                if (!map.isEmpty() && map.containsKey(currentString[i])) {
                    Integer tmp = map.get(currentString[i]);
                    map.put(currentString[i], tmp + 1);
                    System.out.println("Слово: " + currentString[i] + "\nКоличество: " + map.get(currentString[i]));
                } else {
                    map.put(currentString[i], 1);
                    System.out.println("Слово: " + currentString[i] + "\nКоличество: " + map.get(currentString[i]));
                }
            }
        }
    }
}
