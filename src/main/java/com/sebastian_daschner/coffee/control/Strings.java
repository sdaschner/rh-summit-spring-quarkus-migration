package com.sebastian_daschner.coffee.control;

public final class Strings {

    public static String capitalize(String word) {
        if (word.isBlank()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }

}
