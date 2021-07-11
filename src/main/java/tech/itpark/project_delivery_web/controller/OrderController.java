package tech.itpark.project_delivery_web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;
import tech.itpark.project_delivery_web.dto.OrderDto;
import tech.itpark.project_delivery_web.service.order.OrderService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public void getByOwnerId(ServerRequest request, ServerResponse response) {
        List<OrderDto> orders = orderService.findAllByOwnerId(Long.valueOf(request.getRequestAttribute("id")));
        response.write(orders, ContentTypes.APPLICATION_JSON);
    }

    public void getByVendorId(ServerRequest request, ServerResponse response) {
        List<OrderDto> orders = orderService.findAllByVendorId(Long.valueOf(request.getRequestAttribute("id")));
        response.write(orders, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) {
        OrderDto order = orderService.findById(Long.valueOf(request.getRequestAttribute("id")));
        response.write(order, ContentTypes.APPLICATION_JSON);
    }

    public void update(ServerRequest request, ServerResponse response) {
        final OrderDto updated = orderService.update(request.read(OrderDto.class));
        response.write(updated, ContentTypes.APPLICATION_JSON);
    }

    public void deleteById(ServerRequest request, ServerResponse response) throws IOException {
        orderService.deleteById(Long.valueOf(request.getRequestParameter("id")));
        response.write("Order with id " + request.getRequestAttribute("id") + " has been deleted", ContentTypes.TEXT_PLAIN);
    }
}
