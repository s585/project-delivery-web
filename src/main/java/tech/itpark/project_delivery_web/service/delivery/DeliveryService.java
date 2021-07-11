package tech.itpark.project_delivery_web.service.delivery;

import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.model.enums.Category;

public interface DeliveryService {

    double[] getCoordinates(String address);

    DelivererDto getDeliverer(Long vendorId);

    Long getDeliveryPrice(Long userId, Long vendorId, Category category);

}
