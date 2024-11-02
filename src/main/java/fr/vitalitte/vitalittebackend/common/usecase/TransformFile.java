package fr.vitalitte.vitalittebackend.common.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

@Service
public class TransformFile {

    FileRepository fileRepository;

    public TransformFile(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileDto fileToDto(FileEntity file) {
        if (file == null) {
            return null;
        }
        return FileDto.builder()
                .slug(file.getSlug())
                .fileName(file.getFileName())
                .fileData(file.getFileData())
                .build();
    }

    public List<FileDto> filesToDtos(List<FileEntity> files) {
        return mapList(this::fileToDto, files);
    }
}
