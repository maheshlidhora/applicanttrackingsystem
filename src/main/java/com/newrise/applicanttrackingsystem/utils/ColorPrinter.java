package com.newrise.applicanttrackingsystem.utils;

public class ColorPrinter 
{

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printlnBlack(String message) {
        System.out.println(BLACK + message + RESET);
    }
    
    public static void printlnRed(String message) {
        System.out.println(RED + message + RESET);
    }
    
    public static void printlnGreen(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void printlnYellow(String message) {
        System.out.println(YELLOW + message + RESET);
    }

    public static void printlnBlue(String message) {
        System.out.println(BLUE + message + RESET);
    }

    public static void printlnPurple(String message) {
        System.out.println(PURPLE + message + RESET);
    }

    public static void printlnCyan(String message) {
        System.out.println(CYAN + message + RESET);
    }

    public static void printlnWhite(String message) {
        System.out.println(WHITE + message + RESET);
    }

    public static void printBlack(String message) {
        System.out.print(BLACK + message + RESET);
    }

    public static void printRed(String message) {
        System.out.print(RED + message + RESET);
    }

    public static void printGreen(String message) {
        System.out.print(GREEN + message + RESET);
    }

    public static void printYellow(String message) {
        System.out.print(YELLOW + message + RESET);
    }

    public static void printBlue(String message) {
        System.out.print(BLUE + message + RESET);
    }

    public static void printPurple(String message) {
        System.out.print(PURPLE + message + RESET);
    }

    public static void printCyan(String message) {
        System.out.print(CYAN + message + RESET);
    }

    public static void printWhite(String message) {
        System.out.print(WHITE + message + RESET);
    }

    // Optional: Print with custom ANSI color code
    public static void printlnCustom(String ansiColorCode, String message) {
        System.out.println(ansiColorCode + message + RESET);
    }

    public static void printCustom(String ansiColorCode, String message) {
        System.out.print(ansiColorCode + message + RESET);
    }
}

