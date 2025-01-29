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
    public void updateFile(FileDto fileUpdated, String linkedSlug, boolean isMainPicture) {

        String actualSlug = fileUpdated.getSlug();
        String fileSlug = slugifyFileName(fileUpdated.getFileName());

        if (actualSlug.isBlank() || actualSlug.isEmpty() || !fileRepository.existsBySlug(fileUpdated.getSlug())) {
            createFile(fileUpdated, isMainPicture, linkedSlug);
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
    public void compareAndUpdateFiles(List<FileDto> oldFiles, List<FileDto> newFiles, String linkedSlug) {
        for (FileDto oldFile : oldFiles) {
            boolean isOldFileSlugExist = false;
            for (FileDto newFile : newFiles) {
                if (oldFile.getSlug().equals(newFile.getSlug())) {
                    updateFile(newFile, linkedSlug, false);
                    isOldFileSlugExist = true;
                    break;
                }
            }
            if (!isOldFileSlugExist) {
                deleteFileBySlug(oldFile.getSlug());
            }
        }

        for (FileDto newFile : newFiles) {
            boolean isNewFileSlugExist = false;
            for (FileDto oldFile : oldFiles) {
                if (newFile.getSlug().equals(oldFile.getSlug())) {
                    isNewFileSlugExist = true;
                    break;
                }
            }
            if (!isNewFileSlugExist) {
                createFile(newFile, false, linkedSlug);
            }
        }
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
