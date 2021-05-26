package tech.itpark.project_delivery_web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupSaveRequestDto {
    private long id;
    private String name;
}
