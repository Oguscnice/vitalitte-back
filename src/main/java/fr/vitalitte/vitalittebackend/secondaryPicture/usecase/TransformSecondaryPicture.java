package fr.vitalitte.vitalittebackend.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.secondaryPicture.rest.SecondaryPictureDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

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
                .url(this.transformUrl.urlToString(secondaryPicture.getUrl()))
                .build();
    }
    public List<SecondaryPictureDto> picturesToDtos(List<SecondaryPicture> secondaryPictures) {
        return mapList(this::pictureToDto, secondaryPictures);
    }

}
