package fr.vitalitte.vitalittebackend.stationery.common.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.common.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import fr.vitalitte.vitalittebackend.stationery.common.persistence.ProductCommonValuesRepository;
import fr.vitalitte.vitalittebackend.stationery.common.rest.ProductCommonValuesDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

@Service
public class TransformProductCommonValues {

    ProductCommonValuesRepository productRepository;
    TransformUrl transformUrl;

    public TransformProductCommonValues(ProductCommonValuesRepository productRepository, TransformUrl transformUrl) {
        this.productRepository = productRepository;
        this.transformUrl = transformUrl;
    }

    public ProductCommonValuesDto productCVToProductCVDto(ProductCommonValues productCV) {

        ProductCommonValuesDto productDto = new ProductCommonValuesDto() {
            @Override
            public void setName(String name) {
                super.setName(name);
            }

            @Override
            public void setSlug(String slug) {
                super.setSlug(slug);
            }

            @Override
            public void setPicture(String picture) {
                super.setPicture(picture);
            }

            @Override
            public void setPictureThumbnail(String pictureThumbnail) {
                super.setPictureThumbnail(pictureThumbnail);
            }

            @Override
            public void setPrice(BigDecimal price) {
                super.setPrice(price);
            }

            @Override
            public void setDescription(String description) {
                super.setDescription(description);
            }

            @Override
            public void setAvailable(boolean available) {
                super.setAvailable(available);
            }
        };

        String picture = this.transformUrl.urlToString(productCV.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(productCV.getPictureThumbnail());

        productDto.setSlug(productCV.getSlug());
        productDto.setName(productCV.getName());
        productDto.setDescription(productCV.getDescription());
        productDto.setPrice(productCV.getPrice());
        productDto.setPicture(picture);
        productDto.setPictureThumbnail(pictureThumbnail);
        productDto.setAvailable(productCV.isAvailable());

        return productDto;
    }

    public List<ProductCommonValuesDto> productsCVToDtos(List<ProductCommonValues> productCVList) {
        return mapList(this::productCVToProductCVDto, productCVList);
    }

    public ProductCommonValues dtoToProductCV(ProductCommonValuesDto productCVDto) {
        return this.productRepository.findBySlug(productCVDto.getSlug()).orElseThrow(ProductNotFoundException::new);
    }
}
