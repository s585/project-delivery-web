package tech.itpark.project_delivery_web.dto.user;

import tech.itpark.project_delivery_web.dto.RoleDto;
import tech.itpark.project_delivery_web.model.Order;

import java.util.List;

public class UserDtoRegistration {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String secret;

    private String address;

    private RoleDto role;

    private List<Order> orders;

    private Double lon;

    private Double lat;
}
