package tech.itpark.project_delivery_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.framework.security.Auth;
import tech.itpark.project_delivery_web.dto.GroupResponseDto;
import tech.itpark.project_delivery_web.dto.GroupSaveRequestDto;
import tech.itpark.project_delivery_web.dto.GroupSaveResponseDto;
import tech.itpark.project_delivery_web.exception.PermissionDeniedException;
import tech.itpark.project_delivery_web.mapper.GroupMapper;
import tech.itpark.project_delivery_web.model.Group;
import tech.itpark.project_delivery_web.repository.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository repository;
    private final GroupMapper mapper;

    public List<GroupResponseDto> getAll(){
        return mapper.fromModelList(repository.getAll());
    }

    public GroupSaveResponseDto save(Auth auth, GroupSaveRequestDto dto){
        if (auth.isAnonymous()) {
            throw new PermissionDeniedException();
        }
        final var toSave = mapper.fromDto(dto, auth.getId());
        final var saved = repository.save(new Group(dto.getId(), auth.getId(), dto.getName()));
        return mapper.fromModel(saved);
    }
}
