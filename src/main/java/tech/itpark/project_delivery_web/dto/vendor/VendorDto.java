package tech.itpark.project_delivery_web.dto.vendor;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.Product;

import java.util.List;

@Getter
@Setter
public class VendorDto {

    private Long id;
    private String name;
    private String address;
    private Double lon;
    private Double lat;
    private List<Product> products;
}
