package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;

import java.util.List;

public interface SecondaryPictureService {

    void createSecondaryPicture(Product product, SecondaryPictureDto secondaryPictureDto);
    List<SecondaryPictureDto> findAllSecondaryPicturesByProduct(Product product);

}
