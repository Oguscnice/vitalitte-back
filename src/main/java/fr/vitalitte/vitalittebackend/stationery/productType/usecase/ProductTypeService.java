package fr.vitalitte.vitalittebackend.stationery.productType.usecase;

import java.util.List;

public interface ProductTypeService {
	List<String> getAllProductTypesEnumIfProductAvailable();
	List<String> getAllProductTypes();
}
