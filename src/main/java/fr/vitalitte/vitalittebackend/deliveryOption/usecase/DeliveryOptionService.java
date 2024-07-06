package fr.vitalitte.vitalittebackend.deliveryOption.usecase;

import fr.vitalitte.vitalittebackend.deliveryOption.rest.CreateDeliveryOptionBody;
import fr.vitalitte.vitalittebackend.deliveryOption.rest.DeliveryOptionDto;

import java.util.List;

public interface DeliveryOptionService {

    void createDeliveryOption(CreateDeliveryOptionBody createDeliveryOptionBody);
    void updateDeliveryOption(DeliveryOptionDto deliveryOptionDto);
    void changeDeliveryOptionAvailability(DeliveryOptionDto deliveryOptionDto);
    void changeDeliveryOptionExpress(DeliveryOptionDto deliveryOptionDto);
    List<DeliveryOptionDto> getAllDeliveryOptions();
    List<DeliveryOptionDto> getDeliveryOptionsIsAvailable(boolean isAvailable);
    void deleteDeliveryOption(String slug);

}
