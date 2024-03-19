package fr.vitalitte.vitalittebackend.materials.usecase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.vitalitte.vitalittebackend.materials.models.Material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialSerializer extends JsonSerializer<List<Material>> {
    @Override
    public void serialize(List<Material> materials, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> serializedTags = new ArrayList<>();
        for (Material material : materials) {
            Map<String, Object> serializedTag = new HashMap<>();
            serializedTag.put("name", material.getName());
            serializedTags.add(serializedTag);
        }
        jsonGenerator.writeObject(serializedTags);
    }
}
