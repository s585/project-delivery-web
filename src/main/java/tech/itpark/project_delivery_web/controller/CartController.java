package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.CartDto;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.service.cart.CartService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    public void getByOwnerId(ServerRequest request, ServerResponse response) {
        final CartDto cart = cartService.findByUserId(Long.valueOf(request.getRequestAttribute("id")));
        response.write(cart, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) {
        final CartDto dto = cartService.findById(Long.valueOf(request.getRequestAttribute("id")));
        response.write(dto, ContentTypes.APPLICATION_JSON);
    }

    public void save(ServerRequest request, ServerResponse response) {
        final CartDto saved = cartService.create(request.read(CartDto.class), request.getToken());
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    public void checkout(ServerRequest request, ServerResponse response) {
        final OrderDto saved = cartService.checkout(request.read(CartDto.class));
        response.write(saved, ContentTypes.APPLICATION_JSON);
    }

    public void update(ServerRequest request, ServerResponse response) {
        final CartDto updated = cartService.update(request.read(CartDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        cartService.deleteById(Long.valueOf(request.getRequestParameter("id")));
        response.write("Cart with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }
}
