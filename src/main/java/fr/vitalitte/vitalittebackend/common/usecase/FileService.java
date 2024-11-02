package fr.vitalitte.vitalittebackend.common.usecase;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;

import java.util.List;

public interface FileService {
    void createFile(FileDto fileDto, boolean isMainPicture, String linkedSlug);
    FileDto findFilePictureByLinkedSlug(String linkedSlug);
    List<FileDto> findFilesSecondaryPicturesByLinkedSlug(String linkedSlug);
    void updateFile(FileDto file, String linkedSlug);
    void deleteFileBySlug(String slug);
    void deleteAllFilesByLinkedSlug(String linkedSlug);
}
