package dev.mayaqq.estrogen.utils;

public class UwUfy {

    public static String uwufyString(String input) {
        int stringLength = input.length();
        // Replace 'r' and 'l' with 'w', and 'R' and 'L' with 'W'
        input = input.replaceAll("[rl]", "w").replaceAll("[RL]", "W");

        // Replace 'ove' with 'uv' and 'OVE' with 'UV'
        input = input.replaceAll("ove", "uv").replaceAll("OVE", "UV");

        // Replace 'o' with 'owo' and 'O' with 'OwO'
        input = input.replaceAll("o", "owo").replaceAll("O", "OwO");

        // Replace '.' with ':3'
        //input = input.replaceAll("\\.", ":3");

        // Replace repeated exclamation marks and question marks
        input = input.replaceAll("!", "!!!").replaceAll("\\?", "???");

        // Convert to uppercase
        if (stringLength % 3 == 0) {
            input = input.toUpperCase();
        }

        // Add more letters to the end of words
        if (stringLength % 2 == 0) {
            input = input.replaceAll("(\\w)(\\b)", "$1$1$1$1$2");
        }
        // 50% chance to duplicate the first letter and add '-'
        if (!(stringLength % 2 == 0)) {
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
                "^w^",
                ":3"
        };
        input += " " + randomPhrases[stringLength % randomPhrases.length];

        return input;
    }
}
