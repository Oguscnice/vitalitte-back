package fr.vitalitte.vitalittebackend.stationery.category.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.TransformFile;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformCategory {

    CategoryRepository categoryRepository;
    FileRepository fileRepository;
    TransformFile transformFile;

    public TransformCategory(CategoryRepository categoryRepository, FileRepository fileRepository, TransformFile transformFile) {
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
        this.transformFile = transformFile;
    }

    public CategoryDto categoryToDto(Category category) {
        FileEntity file = fileRepository.findByLinkedSlugAndIsMainPictureTrue(category.getSlug());
        return CategoryDto.builder()
                .slug(category.getSlug())
                .name(category.getName())
                .description(category.getDescription())
                .pictureDto(transformFile.fileToDto(file))
                .build();
    }

    public List<CategoryDto> categoriesToDtos(List<Category> categories) {
        return mapList(this::categoryToDto, categories);
    }
}
