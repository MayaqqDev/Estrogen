package dev.mayaqq.estrogen.utils;

import java.util.Random;

public class UwUfy {

    private static final Random random = new Random();

    public static String uwufyChar(Character c) {
        switch (c) {
            case 'r', 'l' -> {
                return "w";
            }
            case 'R', 'L' -> {
                return "W";
            }
            case 'o' -> {
                return "owo";
            }
            case 'O' -> {
                return "OwO";
            }
            case '.' -> {
                return ":3";
            }
        }
        return c.toString();
    }

    public static String uwufyString(String input) {
        // Replace 'r' and 'l' with 'w', and 'R' and 'L' with 'W'
        input = input.replaceAll("[rR]", "w").replaceAll("[lL]", "w");

        // Replace 'ove' with 'uv' and 'OVE' with 'UV'
        input = input.replaceAll("ove", "uv").replaceAll("OVE", "UV");

        // Replace 'o' with 'owo' and 'O' with 'OwO'
        input = input.replaceAll("o", "owo").replaceAll("O", "OwO");

        // Replace '.' with ':3'
        input = input.replaceAll("\\.", ":3");

        // Replace repeated exclamation marks and question marks
        input = input.replaceAll("!", "!!!").replaceAll("\\?", "???");

        // Convert to uppercase

        if (random.nextInt(3) == 0) {
            input = input.toUpperCase();
        }

        // Add more letters to the end of words
        input = input.replaceAll("(\\w)(\\b)", "$1$1$1$1$2");

        // 50% chance to duplicate the first letter and add '-'
        if (random.nextBoolean()) {
            input = input.replaceAll("\\b(\\w)(\\w*)\\b", "$1-$1$2");
        }

        // Add a random phrase from the array
        String[] randomPhrases = {
                "UwU",
                "owo",
                "OwO",
                "uwu",
                ">w<",
                "^w^",
                ":3",
                "^-^",
                "^_^",
                "^w^"
        };
        input += " " + randomPhrases[random.nextInt(randomPhrases.length)];

        return input;
    }
}
