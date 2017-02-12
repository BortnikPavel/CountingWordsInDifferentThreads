package com.bortnikpp.resources;


import com.bortnikpp.models.ThreadModel;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс служит для считывания текста с URL ресурса, и записи в файл
 * @author Bortnik Pavel
 * @version 1.0
 */
public class URLWork {
    /**Счетчик для формирования имен файлов*/
    private volatile static int count = 1;
    /**Имя файла для записи с URL ресурса*/
    private String fileName;

    /**
     * Метод открывает потоки ввода и вывода и вызывает метод для копирования
     * @param str - имя ресурса для ввода
     * @return имя записаного файла
     */
    public String readURL(String str){
            fileName = "Net" + (count++) + ".txt";
            try (BufferedReader src = new BufferedReader(new InputStreamReader(new URL(str).openStream()));
                 BufferedWriter dst = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
                URL url = new URL(str);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getContentType().equals("text/plain") || str.endsWith(".txt")) {
                    copy(src, dst);
                }else{
                    ThreadModel.flag = false;
                    connection.disconnect();
                    throw new Exception();
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                ThreadModel.flag = false;
                e.printStackTrace();
                return fileName;
            } catch (IOException e) {
                ThreadModel.flag = false;
                System.err.println("Is there a problem with the stream opening");
                return fileName;
            } catch (Exception e) {
                System.err.println("This is " + str + " invalid URL path!");
                return fileName;
            }
        return fileName;
    }

    /**
     * Метод для копирования из потока ввода в в поток вывода
     * @param src - поток ввода
     * @param dst - поток вывода
     */
    public void copy(BufferedReader src, BufferedWriter dst )  {
        while (ThreadModel.flag) {
            String data = null;
            try {
                data = src.readLine();
            } catch (IOException e) {
                ThreadModel.flag = false;
                System.err.println("Failed to read string");
            }
            if (data != null && ThreadModel.flag) {
                try {
                    dst.write(data);
                } catch (IOException e) {
                    ThreadModel.flag = false;
                    System.err.println("Failed to write string");
                }
            } else {
                return;
            }
        }
    }
}
