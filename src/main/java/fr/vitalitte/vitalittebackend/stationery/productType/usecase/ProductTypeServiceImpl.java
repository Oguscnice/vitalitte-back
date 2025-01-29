package fr.vitalitte.vitalittebackend.stationery.productType.usecase;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements  ProductTypeService {

	@Override
	public List<String> getAllProductTypesEnumIfProductAvailable() {
		return ConvertEnumProductType.AllEnumsToStringArrayIfProductAvailable();
	}

	@Override
	public List<String> getAllProductTypes() {
		return ConvertEnumProductType.AllEnumsToStringArray();
	}
}
