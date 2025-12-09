package ma.fstt.usermanagementservice.mapper;

import ma.fstt.usermanagementservice.dto.*;
import ma.fstt.usermanagementservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    User toEntity(UserCreateDto dto);

    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);
}
