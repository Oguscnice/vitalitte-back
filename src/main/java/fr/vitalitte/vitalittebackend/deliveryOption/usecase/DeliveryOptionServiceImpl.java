package fr.vitalitte.vitalittebackend.deliveryOption.usecase;

import fr.vitalitte.vitalittebackend.common.utils.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.deliveryOption.exception.DeliveryOptionNotFoundException;
import fr.vitalitte.vitalittebackend.deliveryOption.exception.SlugDeliveryOptionAlreadyExistsException;
import fr.vitalitte.vitalittebackend.deliveryOption.models.DeliveryOption;
import fr.vitalitte.vitalittebackend.deliveryOption.persistence.DeliveryOptionRepository;
import fr.vitalitte.vitalittebackend.deliveryOption.rest.CreateDeliveryOptionBody;
import fr.vitalitte.vitalittebackend.deliveryOption.rest.DeliveryOptionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryOptionServiceImpl implements DeliveryOptionService {

    DeliveryOptionRepository deliveryOptionRepository;
    TransformDeliveryOption transformDeliveryOption;

    public DeliveryOptionServiceImpl(DeliveryOptionRepository deliveryOptionRepository, TransformDeliveryOption transformDeliveryOption) {
        this.deliveryOptionRepository = deliveryOptionRepository;
        this.transformDeliveryOption = transformDeliveryOption;
    }

    @Override
    public void createDeliveryOption(CreateDeliveryOptionBody createDeliveryOptionBody) {

        String deliveryOptionSlug = this.slugifyDeliveryOption(createDeliveryOptionBody);
        verifyIfDeliveryOptionExistsBySlug(deliveryOptionSlug);

        final DeliveryOption deliveryOption = DeliveryOption.builder()
                .slug(deliveryOptionSlug)
                .name(CapitalizeStringUtil.firstLetter(createDeliveryOptionBody.getName()))
                .price(createDeliveryOptionBody.getPrice())
                .estimatedDeliveryTime(createDeliveryOptionBody.getEstimatedDeliveryTime())
                .isExpress(createDeliveryOptionBody.getIsExpress())
                .carrier(CapitalizeStringUtil.firstLetter(createDeliveryOptionBody.getCarrier()))
                .description(createDeliveryOptionBody.getDescription())
                .build();

        this.deliveryOptionRepository.save(deliveryOption);
    }

    @Override
    public void updateDeliveryOption(DeliveryOptionDto deliveryOptionDtoUpdated) {

        DeliveryOption deliveryOptionToUpdate = findOneDeliveryOptionBySlugOrThrow(deliveryOptionDtoUpdated.getSlug());

        String newDeliveryOptionSlug = slugifyDeliveryOption(deliveryOptionDtoUpdated);
        if (!newDeliveryOptionSlug.equals(deliveryOptionToUpdate.getSlug())) {
            verifyIfDeliveryOptionExistsBySlug(newDeliveryOptionSlug);
        }

        deliveryOptionToUpdate.setSlug(newDeliveryOptionSlug);
        deliveryOptionToUpdate.setName(CapitalizeStringUtil.firstLetter(deliveryOptionDtoUpdated.getName()));
        deliveryOptionToUpdate.setPrice(deliveryOptionDtoUpdated.getPrice());
        deliveryOptionToUpdate.setEstimatedDeliveryTime(deliveryOptionDtoUpdated.getEstimatedDeliveryTime());
        deliveryOptionToUpdate.setCarrier(CapitalizeStringUtil.firstLetter(deliveryOptionDtoUpdated.getCarrier()));
        deliveryOptionToUpdate.setDescription(deliveryOptionDtoUpdated.getDescription());

        this.deliveryOptionRepository.save(deliveryOptionToUpdate);
    }

    @Override
    public void changeDeliveryOptionAvailability(DeliveryOptionDto deliveryOptionDto) {
        System.out.println(deliveryOptionDto.getSlug());
        DeliveryOption deliveryOptionToUpdate = findOneDeliveryOptionBySlugOrThrow(deliveryOptionDto.getSlug());
        deliveryOptionToUpdate.setAvailable(!deliveryOptionToUpdate.isAvailable());
        this.deliveryOptionRepository.save(deliveryOptionToUpdate);
    }

    @Override
    public void changeDeliveryOptionExpress(DeliveryOptionDto deliveryOptionDto) {
        System.out.println(deliveryOptionDto.getSlug());
        DeliveryOption deliveryOptionToUpdate = findOneDeliveryOptionBySlugOrThrow(deliveryOptionDto.getSlug());
        deliveryOptionToUpdate.setExpress(!deliveryOptionToUpdate.isExpress());
        this.deliveryOptionRepository.save(deliveryOptionToUpdate);
    }

    @Override
    public List<DeliveryOptionDto> getAllDeliveryOptions() {
        List<DeliveryOption> deliveryOptions = this.deliveryOptionRepository.findAll();
        return this.transformDeliveryOption.deliveryOptionsToDto(deliveryOptions);
    }

    @Override
    public List<DeliveryOptionDto> getDeliveryOptionsIsAvailable(boolean isAvailable) {
        List<DeliveryOption> deliveryOptions = this.deliveryOptionRepository.findAllDeliveryOptionByIsAvailable(isAvailable);
        return this.transformDeliveryOption.deliveryOptionsToDto(deliveryOptions);
    }

    @Override
    public void deleteDeliveryOption(String slug) {
        DeliveryOption deliveryOptionToDelete = findOneDeliveryOptionBySlugOrThrow(slug);
        this.deliveryOptionRepository.delete(deliveryOptionToDelete);
    }

    private String slugifyDeliveryOption(CreateDeliveryOptionBody createDeliveryOptionBody) {
        return SlugifyUtil.stringToSlug(createDeliveryOptionBody.getName()) + '-' + SlugifyUtil.stringToSlug(createDeliveryOptionBody.getCarrier());
    }

    private String slugifyDeliveryOption(DeliveryOptionDto deliveryOptionDto) {
        return SlugifyUtil.stringToSlug(deliveryOptionDto.getName()) + '-' + SlugifyUtil.stringToSlug(deliveryOptionDto.getCarrier());
    }

    private void verifyIfDeliveryOptionExistsBySlug(String slug) {
        if (this.deliveryOptionRepository.existsBySlug(slug)) {
            throw new SlugDeliveryOptionAlreadyExistsException();
        }
    }

    private DeliveryOption findOneDeliveryOptionBySlugOrThrow(String slug) {
        return this.deliveryOptionRepository.findBySlug(slug).orElseThrow(DeliveryOptionNotFoundException::new);
    }
}
