package tech.itpark.project_delivery_web.dto.user;

import tech.itpark.project_delivery_web.model.Order;

import java.util.List;

public class UserDto {

    private Long id;

    private String password;

    private String secret;

    private String name;

    private String address;

    private List<Order> orders;

    private Double longitude;

    private Double latitude;
}
