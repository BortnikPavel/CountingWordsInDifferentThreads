package com.bortnikpp;

import com.bortnikpp.models.ThreadModel;

public class Main {

    public static void main(String[] args) throws Exception {
        ThreadModel[] threadModels = new ThreadModel[args.length];
        for (int i = 0; i < args.length&&ThreadModel.flag; i++){
            threadModels[i] = new ThreadModel(args[i]);
            threadModels[i].start();
            System.out.println("Я " + i + " поток");
        }
    }
}
