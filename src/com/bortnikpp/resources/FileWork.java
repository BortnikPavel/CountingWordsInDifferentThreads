package com.bortnikpp.resources;

import com.bortnikpp.models.ThreadModel;

import java.io.*;

/**
 * Класс служит для работы с файлами
 * @author Bortnik Pavel
 * @version 1.0
 */
public class FileWork {

    /**
     * Метод для чтения из файла
     * @param fileName - имя файла
     */
    public void readFile(String fileName) {
        String stringRead;
        Parser parser = new Parser();
        try {
            File file = new File(fileName);
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                while ((stringRead = in.readLine()) != null && ThreadModel.flag) {
                    parser.parseLine(stringRead);
                }
            }
        } catch (IOException e) {
            System.err.println(fileName + "   " + e.getMessage());
        }
    }
}
