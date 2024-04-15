package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;

import java.util.List;

public interface NotebookService {
    void createNotebook(CreateNotebookBody createNotebookBody);
    NotebookDto getNotebookBySlug(String slug);
    List<NotebookDto> findAllNotebooks();
    List<NotebookDto> findNotebooksByCategory(String categorySlug);
    List<NotebookDto> findNotebooksByCollection(String collectionSlug);
    NotebookDto changeNotebookAvailability(NotebookDto notebookDto);
    void updateNotebookBySlug(String slug, NotebookDto notebookDtoUpdated);
    void deleteNotebookBySlug(String slug);
}
