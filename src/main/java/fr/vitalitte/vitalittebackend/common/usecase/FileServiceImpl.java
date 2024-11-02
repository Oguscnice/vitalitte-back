package fr.vitalitte.vitalittebackend.common.usecase;

import fr.vitalitte.vitalittebackend.common.exception.FileNotFoundException;
import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;
    TransformFile transformFile;
    SlugifyUtil slugifyUtil;

    public FileServiceImpl(FileRepository fileRepository, TransformFile transformFile, SlugifyUtil slugifyUtil) {
        this.fileRepository = fileRepository;
        this.transformFile = transformFile;
        this.slugifyUtil = slugifyUtil;
    }

    @Override
    public void createFile(FileDto fileDto, boolean isMainPicture, String linkedSlug) {
        String fileSlug = slugifyFileName(fileDto.getFileName());
        FileEntity fileEntity = FileEntity.builder()
                                        .slug(fileSlug)
                                        .fileName(fileDto.getFileName())
                                        .fileData(fileDto.getFileData())
                                        .linkedSlug(linkedSlug)
                                        .isMainPicture(isMainPicture)
                                        .build();
        fileRepository.save(fileEntity);
    }

    @Override
    public FileDto findFilePictureByLinkedSlug(String linkedSlug) {
        FileEntity file = fileRepository.findByLinkedSlugAndIsMainPictureTrue(linkedSlug);
        return transformFile.fileToDto(file);
    }

    @Override
    public List<FileDto> findFilesSecondaryPicturesByLinkedSlug(String linkedSlug) {
        List<FileEntity> files = fileRepository.findAllByLinkedSlugAndIsMainPictureFalse(linkedSlug);
        return transformFile.filesToDtos(files);
    }

    @Override
    public void updateFile(FileDto fileUpdated, String linkedSlug) {

        String actualSlug = fileUpdated.getSlug();
        String fileSlug = slugifyFileName(fileUpdated.getFileName());

        if (actualSlug.isBlank() || actualSlug.isEmpty() || !fileRepository.existsBySlug(fileUpdated.getSlug())) {
            createFile(fileUpdated, true, linkedSlug);
            return;
        }

        FileEntity actualFile = findOneFileByFileDataOrThrow(fileUpdated.getSlug());
        actualFile.setSlug(fileSlug);
        actualFile.setFileName(fileUpdated.getFileName());
        actualFile.setFileData(fileUpdated.getFileData());
        actualFile.setLinkedSlug(linkedSlug);

        fileRepository.save(actualFile);
    }

    @Override
    public void deleteFileBySlug(String slug) {
        FileEntity file = findOneFileByFileDataOrThrow(slug);
        fileRepository.delete(file);
    }

    @Override
    public void deleteAllFilesByLinkedSlug(String linkedSlug) {
        List<FileEntity> files = fileRepository.findAllByLinkedSlug(linkedSlug);
        fileRepository.deleteAll(files);
    }

    private FileEntity findOneFileByFileDataOrThrow(String slug) {
        return fileRepository.findFileEntityBySlug(slug).orElseThrow(FileNotFoundException::new);
    }

    private String slugifyFileName(String fileName) {
        String fileSlug = slugifyUtil.stringToSlug(fileName);
        while (fileRepository.existsBySlug(fileSlug)) {
            fileSlug = fileSlug + "-bis";
        }
        return fileSlug;
    }
}
