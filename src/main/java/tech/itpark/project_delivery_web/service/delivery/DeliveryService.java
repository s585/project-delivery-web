package tech.itpark.project_delivery_web.service.delivery;

import tech.itpark.project_delivery_web.model.enums.Category;

public interface DeliveryService {

    double[] getCoordinates(String address);

    Long getDeliverer(Long vendorId, String token);

    Long getDeliveryPrice(Long userId, Long vendorId, Category category);

}
