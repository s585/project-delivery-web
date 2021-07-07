package tech.itpark.project_delivery_web.dto.vendor;

import tech.itpark.project_delivery_web.model.Product;

import java.util.List;

public class VendorDto {

    private Long id;
    private String name;
    private String address;
    private List<Product> products;
}
