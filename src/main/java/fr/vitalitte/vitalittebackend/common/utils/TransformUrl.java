package fr.vitalitte.vitalittebackend.common.utils;

import fr.vitalitte.vitalittebackend.common.exception.UrlImageInvalidException;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.net.URL;
import java.util.List;

@Service
public class TransformUrl {
    public String urlToString(URL url){
        return url.toString();
    }
    public List<String> urlsToString(List<URL> urls){
        return mapList(this::urlToString, urls);
    }
    public URL stringToUrl(String value){
        URL newUrl;
        try {
            newUrl = new URL(value);
        } catch (Exception e) {
            throw new UrlImageInvalidException();
        }
        return newUrl;
    }
    public List<URL> stringsToUrl(List<String> values){
        return mapList(this::stringToUrl, values);
    }
}
