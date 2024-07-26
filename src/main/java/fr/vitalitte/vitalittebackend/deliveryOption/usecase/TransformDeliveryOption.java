package fr.vitalitte.vitalittebackend.deliveryOption.usecase;

import fr.vitalitte.vitalittebackend.deliveryOption.exception.DeliveryOptionNotFoundException;
import fr.vitalitte.vitalittebackend.deliveryOption.models.DeliveryOption;
import fr.vitalitte.vitalittebackend.deliveryOption.persistence.DeliveryOptionRepository;
import fr.vitalitte.vitalittebackend.deliveryOption.rest.DeliveryOptionDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformDeliveryOption {

    DeliveryOptionRepository deliveryOptionRepository;

    public TransformDeliveryOption(DeliveryOptionRepository deliveryOptionRepository) {
        this.deliveryOptionRepository = deliveryOptionRepository;
    }

    public DeliveryOptionDto deliveryOptionToDto (DeliveryOption deliveryOption) {
        return DeliveryOptionDto.builer()
                .slug(deliveryOption.getSlug())
                .name(deliveryOption.getName())
                .price(deliveryOption.getPrice())
                .estimatedDeliveryTime(deliveryOption.getEstimatedDeliveryTime())
                .isExpress(deliveryOption.isExpress())
                .carrier(deliveryOption.getCarrier())
                .isAvailable(deliveryOption.isAvailable())
                .description(deliveryOption.getDescription())
                .build();
    }

    public List<DeliveryOptionDto> deliveryOptionsToDto (List<DeliveryOption> deliveryOptions) {
        return mapList(this::deliveryOptionToDto, deliveryOptions);
    }

    public DeliveryOption dtoToDeliveryOption(DeliveryOptionDto deliveryOptionDto) {
        return this.deliveryOptionRepository.findBySlug(deliveryOptionDto.getSlug())
                .orElseThrow(DeliveryOptionNotFoundException::new);
    }
}
