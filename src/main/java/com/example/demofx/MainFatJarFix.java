package com.example.demofx;

/**
 * Apparently for fat jars, we need a main class
 * that does not extend from Application ....
 */
public class MainFatJarFix {

    public static void main(String[] args) {
        Main.main(args);
    }
}
