package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class SecondaryPictureServiceImpl implements SecondaryPictureService {

    SecondaryPictureRepository secondaryPictureRepository;
    ProductRepository productRepository;
    TransformUrl transformUrl;
    TransformSecondaryPicture transformSecondaryPicture;

    public SecondaryPictureServiceImpl(SecondaryPictureRepository secondaryPictureRepository, ProductRepository productRepository, TransformUrl transformUrl, TransformSecondaryPicture transformSecondaryPicture) {
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.productRepository = productRepository;
        this.transformUrl = transformUrl;
        this.transformSecondaryPicture = transformSecondaryPicture;
    }

    @Override
    public void createSecondaryPicture(Product product, SecondaryPictureDto secondaryPictureDto) {

        URL picture = this.transformUrl.stringToUrl(secondaryPictureDto.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(secondaryPictureDto.getPictureThumbnail());

        SecondaryPicture newPicture = SecondaryPicture.builder()
                                                      .picture(picture)
                                                      .pictureThumbnail(pictureThumbnail)
                                                      .product(product)
                                                      .build();

        this.secondaryPictureRepository.save(newPicture);
    }

    public List<SecondaryPictureDto> findAllSecondaryPicturesByProduct(Product product) {
        List<SecondaryPicture> pictures = this.secondaryPictureRepository.findAllByProduct(product);
        return this.transformSecondaryPicture.picturesToDtos(pictures);
    }
}
