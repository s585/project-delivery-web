package tech.itpark.project_delivery_web.service.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.model.GeoObject;
import tech.itpark.project_delivery_web.model.enums.Category;
import tech.itpark.project_delivery_web.service.YaGeocoder;
import tech.itpark.project_delivery_web.service.deliverer.DelivererService;
import tech.itpark.project_delivery_web.service.user.UserService;
import tech.itpark.project_delivery_web.service.vendor.VendorService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DeliveryServiceImpl implements DeliveryService {

    private DelivererService delivererService;
    private UserService userService;
    private VendorService vendorService;

    @Autowired
    public void setDelivererService(DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Override
    public double[] getCoordinates(String address) {
        YaGeocoder geocoder = new YaGeocoder();
        List<GeoObject> geoObjects = geocoder.directGeocode(address).collect(Collectors.toList());
        GeoObject geoObject = geoObjects.get(0);
        return Stream.of(geoObject.getPoint().split(" "))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    @Override
    public Long getDeliverer(Long vendorId, String token) {
        final VendorDto vendorDto = vendorService.findById(vendorId, token);
        final List<DelivererDto> delivererDtoList = delivererService.findAll(token);
        return null;
    }

    @Override
    public Long getDeliveryPrice(Long userId, Long vendorId, Category category) {
        return null;
    }
}
