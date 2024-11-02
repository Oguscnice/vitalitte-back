package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

@Service
public class TransformSecondaryPicture {

    SecondaryPictureRepository secondaryPictureRepository;
    TransformUrl transformUrl;

    public TransformSecondaryPicture(SecondaryPictureRepository secondaryPictureRepository, TransformUrl transformUrl) {
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.transformUrl = transformUrl;
    }

    public SecondaryPictureDto pictureToDto(SecondaryPicture secondaryPicture){
        return SecondaryPictureDto.builder()
                .picture(this.transformUrl.urlToString(secondaryPicture.getPicture()))
                .pictureThumbnail(this.transformUrl.urlToString(secondaryPicture.getPictureThumbnail()))
                .build();
    }

    public List<SecondaryPictureDto> picturesToDtos(List<SecondaryPicture> secondaryPictures) {
        return mapList(this::pictureToDto, secondaryPictures);
    }
}
