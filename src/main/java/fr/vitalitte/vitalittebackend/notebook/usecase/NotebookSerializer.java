package fr.vitalitte.vitalittebackend.notebook.usecase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotebookSerializer extends JsonSerializer<List<Notebook>> {
    @Override
    public void serialize(List<Notebook> notebooks, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> serializedNotebooks = new ArrayList<>();
        for (Notebook notebook : notebooks) {
            Map<String, Object> serializedNotebook = new HashMap<>();
            serializedNotebook.put("name", notebook.getName());
            serializedNotebooks.add(serializedNotebook);
        }
        jsonGenerator.writeObject(serializedNotebooks);
    }
}
