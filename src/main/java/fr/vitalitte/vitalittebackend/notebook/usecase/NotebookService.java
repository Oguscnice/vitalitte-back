package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;

import java.util.List;

public interface NotebookService {
    void createNotebook(CreateNotebookBody createNotebookBody);
//    List<NotebookDto> findAllNotebooks();
//    void changeNotebookAvailabilityBySlug(String slug, boolean booleanValue);
//    void updateNotebookBySlug(String slug, NotebookDto notebookDtoUpdated);
//    void deleteNotebookBySlug(String slug);

}
