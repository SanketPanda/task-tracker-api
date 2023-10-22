package com.task.tracker.service.user;

import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.model.RefUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RefUserDTO toDTO(RefUser refUser);
}
