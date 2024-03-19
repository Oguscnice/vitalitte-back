package fr.vitalitte.vitalittebackend.common.utils;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SlugifyUtil {
    public static String stringToSlug(String title) {
        return Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-zA-Z0-9]+", "-")
                .toLowerCase();
    }

    public static String dateToFormatDDmmYY(Date dateToConvert) {

        LocalDateTime instantActual = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        return instantActual.format(formatter);
    }

}
