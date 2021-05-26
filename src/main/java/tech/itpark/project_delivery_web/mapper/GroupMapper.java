package tech.itpark.project_delivery_web.mapper;

import org.mapstruct.Mapper;
import tech.itpark.project_delivery_web.dto.GroupResponseDto;
import tech.itpark.project_delivery_web.dto.GroupSaveRequestDto;
import tech.itpark.project_delivery_web.dto.GroupSaveResponseDto;
import tech.itpark.project_delivery_web.model.Group;

import java.util.List;

@Mapper
public interface GroupMapper {
//    @Mapping(target = "ownerId", defaultValue = "0")
    Group fromDto(GroupSaveRequestDto dto, long ownerId);
    GroupSaveResponseDto fromModel(Group dto);
    List<GroupResponseDto> fromModelList(List<Group> list);
}
