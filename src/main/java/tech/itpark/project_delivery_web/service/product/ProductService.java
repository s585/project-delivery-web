package tech.itpark.project_delivery_web.service.product;

import tech.itpark.project_delivery_web.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAllByVendorId(Long id, String token);

    ProductDto findById(Long id, String token);

    ProductDto create(ProductDto dto);

    ProductDto update(ProductDto dto);

    void deleteById(Long id, String token);
}
