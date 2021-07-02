package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.service.cart.CartService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    public void getAll(ServerRequest request, ServerResponse response) {
        final List<CartDto> carts = cartService.findAll(request.getToken());
        response.write(carts, ContentTypes.APPLICATION_JSON);
    }

    public void save(ServerRequest request, ServerResponse response) {
        final CartDto saved = cartService.create(request.read(CartDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) {
        final CartDto dto = cartService.findById(Long.valueOf(request.getRequestParameter("id")), request.getToken());
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        cartService.deleteById(Long.valueOf(request.getRequestParameter("id")), request.getToken());
        response.write("Cart with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }
}
