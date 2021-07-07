package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.service.product.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public void getAllByVendorId(ServerRequest request, ServerResponse response) {
        final List<ProductDto> products = productService.findAllByVendorId(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write(products, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT_INFO')")
    public void save(ServerRequest request, ServerResponse response) {
        final ProductDto saved = productService.create(request.read(ProductDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT_INFO')")
    public void update(ServerRequest request, ServerResponse response) {
        final ProductDto updated = productService.update(request.read(ProductDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) {
        final ProductDto product = productService.findById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write(product, ContentTypes.APPLICATION_JSON);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT_INFO')")
    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        productService.deleteById(Long.valueOf(request.getRequestAttribute("id")), request.getToken());
        response.write("Product with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }
}
