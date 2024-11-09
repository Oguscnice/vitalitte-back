package fr.vitalitte.vitalittebackend.common.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    FileRepository fileRepository;
    FileService fileService;

    public FileUploadController(FileRepository fileRepository, FileService fileService) {
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @PutMapping("/{linkedSlug}")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> updateFileBySlug(@PathVariable String linkedSlug, @RequestBody FileDto fileDto) {
        fileService.updateFile(fileDto, linkedSlug);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Fichier modifié avec succès."));
    }

    @DeleteMapping("/{fileSlug}")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> deleteFileBySlug(@PathVariable String fileSlug) {
        fileService.deleteFileBySlug(fileSlug);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Fichier supprimé avec succès."));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
//        Optional<FileEntity> fileEntityOptional = fileRepository.findById(id);
//
//        if (fileEntityOptional.isPresent()) {
//            FileEntity fileEntity = fileEntityOptional.get();
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(fileEntity.getFileData());
//        }
//
//        return ResponseEntity.notFound().build();
//    }
}
