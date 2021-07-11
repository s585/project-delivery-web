package tech.itpark.project_delivery_web.dto;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.Order;

import java.util.List;

@Getter
@Setter
public class DelivererDto {

    private Long id;
    private String name;
    private String address;
    private Double lon;
    private Double lat;
    private List<Order> orders;
}
