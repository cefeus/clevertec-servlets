package mapper;

import dto.UserDto;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    /**
     * Maps DTO to user without UUID
     *
     * @param userDto - DTO for mapping
     * @return new user
     */
    @Mapping(target = "id", ignore = true)
    User toUser(UserDto userDto);

    /**
     * Maps user to DTO without UUID
     *
     * @param user - user for mapping
     * @return new DTO
     */
    UserDto toUserDto(User user);

}
