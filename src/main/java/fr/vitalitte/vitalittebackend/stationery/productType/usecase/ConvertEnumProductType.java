package fr.vitalitte.vitalittebackend.stationery.productType.usecase;

import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.productType.exception.ProductTypeNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.productType.models.EProductType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertEnumProductType {

    static ProductRepository productRepository;

    public ConvertEnumProductType(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static String EnumToString(EProductType eProductType) {
        return switch (eProductType) {
            case CARNETS -> "CARNETS";
            case CARTES_POSTALES -> "CARTES_POSTALES";
            case MARQUE_PAGES -> "MARQUE_PAGES";
            case PRINT -> "PRINT";
            default -> throw new ProductTypeNotFoundException();
        };
    }

    public static List<String> AllEnumsToStringArrayIfProductAvailable() {

        long carnetCount = productRepository.countByEProductType(EProductType.CARNETS);
        long cartesPostalesCount = productRepository.countByEProductType(EProductType.CARTES_POSTALES);
        long marquePagesCount = productRepository.countByEProductType(EProductType.MARQUE_PAGES);
        long printCount = productRepository.countByEProductType(EProductType.PRINT);

        List<String> productTypesAvailable = new ArrayList<>();

        if (carnetCount > 0) {
            productTypesAvailable.add(EProductType.CARNETS.name());
        }
        if (cartesPostalesCount > 0) {
            productTypesAvailable.add(EProductType.CARTES_POSTALES.name());
        }
        if (marquePagesCount > 0) {
            productTypesAvailable.add(EProductType.MARQUE_PAGES.name());
        }
        if (printCount > 0) {
            productTypesAvailable.add(EProductType.PRINT.name());
        }

        return productTypesAvailable;
    }

    public static List<String> AllEnumsToStringArray() {
        return Arrays.stream(EProductType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static EProductType StringToEnum(String productType) {
        return switch (productType.toUpperCase()) {
            case "CARNETS" -> EProductType.CARNETS;
            case "CARTES_POSTALES", "CARTES-POSTALES", "CARTES POSTALES" -> EProductType.CARTES_POSTALES;
            case "MARQUE_PAGES", "MARQUE-PAGES", "MARQUE PAGES" -> EProductType.MARQUE_PAGES;
            case "PRINT" -> EProductType.PRINT;
            case "ALL" -> null;
            default -> throw new ProductTypeNotFoundException();
        };
    }

    public static String StringToSingular(String productType) {
        return switch (productType.toUpperCase()) {
            case "CARNETS" -> "Carnet";
            case "CARTES_POSTALES", "CARTES POSTALES" -> "Carte Postale";
            case "MARQUE_PAGES", "MARQUE PAGES" -> "Marque Page";
            case "PRINT" -> "Print";
            default -> throw new ProductTypeNotFoundException();
        };
    }
}
