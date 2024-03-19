package fr.vitalitte.vitalittebackend.common.utils;

public class CapitalizeStringUtil {

    public static String capitalizeFirstLetter(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return inputString;
        }
        return inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
    }

    public static String capitalizeAllLetters(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return inputString;
        }
        return inputString.toUpperCase();
    }
}
