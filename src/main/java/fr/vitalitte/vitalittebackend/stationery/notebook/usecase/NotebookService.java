package fr.vitalitte.vitalittebackend.stationery.notebook.usecase;

import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryAndCollection;
import fr.vitalitte.vitalittebackend.stationery.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.stationery.notebook.rest.NotebookDto;

import java.util.List;

public interface NotebookService {
    void createNotebook(CreateNotebookBody createNotebookBody);
    NotebookDto getNotebookBySlug(String slug);
    List<NotebookDto> findAllNotebooks();
    List<NotebookDto> findAllNotebooksByCategorySlug(String categorySlug);
    List<NotebookDto> findAllNotebooksByCollectionSlug(String collectionSlug);
    List<NotebookDto> findAllNotebooksFilteredByCategoryAndCollection(CategoryAndCollection categoryAndCollection);
    NotebookDto changeNotebookAvailability(NotebookDto notebookDto);
    void updateNotebookBySlug(String slug, NotebookDto notebookDtoUpdated);
    void deleteNotebookBySlug(String slug);
}
