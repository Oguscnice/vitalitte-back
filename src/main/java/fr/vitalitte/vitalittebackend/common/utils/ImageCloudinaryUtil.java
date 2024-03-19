package fr.vitalitte.vitalittebackend.common.utils;

import org.springframework.stereotype.Service;

@Service
public class ImageCloudinaryUtil {

    public String generateFormatedImages(String originalUrl, String imageFormat) {

        // Thumbnail => "/c_thumb,w_250,h_250,g_auto/"
        // Spotlighted => "/c_thumb,w_700,h_500,g_auto/"
        // Article => "/c_thumb,w_1200,h_800,g_auto/"

        StringBuilder modifiedUrl = new StringBuilder(originalUrl);

        // Trouver l'index où insérer la transformation (juste après "/upload/")
        int insertIndex = modifiedUrl.indexOf("/upload/") + 8;

        // Insérer la transformation à l'index trouvé
        modifiedUrl.insert(insertIndex, imageFormat);


        return modifiedUrl.toString();
    }
}
