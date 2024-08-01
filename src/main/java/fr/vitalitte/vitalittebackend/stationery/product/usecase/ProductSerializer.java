package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSerializer extends JsonSerializer<List<Product>> {
    @Override
    public void serialize(List<Product> products, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> serializedProducts = new ArrayList<>();
        for (Product product : products) {
            Map<String, Object> serializedProduct = new HashMap<>();
            serializedProduct.put("slug", product.getSlug());
            serializedProducts.add(serializedProduct);
        }
        jsonGenerator.writeObject(serializedProducts);
    }
}
