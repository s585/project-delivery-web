package tech.itpark.project_delivery_web.dto.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.dto.RoleDto;
import tech.itpark.project_delivery_web.model.Order;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String address;
    private RoleDto role;
    private List<Order> orders;
    private Double lon;
    private Double lat;
}
