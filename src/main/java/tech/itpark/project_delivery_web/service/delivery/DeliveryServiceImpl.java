package tech.itpark.project_delivery_web.service.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.dto.DelivererDto;
import tech.itpark.project_delivery_web.dto.user.UserDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorDto;
import tech.itpark.project_delivery_web.mappers.DelivererMapper;
import tech.itpark.project_delivery_web.model.GeoObject;
import tech.itpark.project_delivery_web.model.enums.Category;
import tech.itpark.project_delivery_web.service.DeliveryUtil;
import tech.itpark.project_delivery_web.service.YaGeocoder;
import tech.itpark.project_delivery_web.service.deliverer.DelivererService;
import tech.itpark.project_delivery_web.service.user.UserService;
import tech.itpark.project_delivery_web.service.vendor.VendorService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DeliveryServiceImpl implements DeliveryService {

    private DelivererService delivererService;
    private DelivererMapper delivererMapper;
    private DeliveryUtil deliveryUtil;
    private UserService userService;
    private VendorService vendorService;

    @Autowired
    public void setDelivererService(DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @Autowired
    public void setDeliveryUtil(DeliveryUtil deliveryUtil) {
        this.deliveryUtil = deliveryUtil;
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
    public DelivererDto getDeliverer(Long vendorId) {
        final VendorDto vendorDto = vendorService.findById(vendorId);
        final List<DelivererDto> delivererDtoList = delivererService.findAll();
        List<Double> distances = new ArrayList<>();
        for (DelivererDto delivererDto : delivererDtoList) {
            distances.add(deliveryUtil.getDistance(
                    vendorDto.getLon(), vendorDto.getLat(), delivererDto.getLon(), delivererDto.getLat()));
        }
        return delivererService.findById((long) (distances.indexOf(distances.stream().min(Double::compare).get()) + 1));
    }

    @Override
    public Long getDeliveryPrice(Long userId, Long vendorId, Category category) {
        UserDto userDto = userService.findById(userId);
        VendorDto vendorDto = vendorService.findById(vendorId);
        double distance = deliveryUtil.getDistance(userDto.getLon(), userDto.getLat(), vendorDto.getLon(), vendorDto.getLat());
        return (long) (distance * category.coefficient * 100);
    }
}
