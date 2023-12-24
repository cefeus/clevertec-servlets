package service;

import dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDto get(UUID id);

    List<UserDto> getAll();

    void create(UserDto productDto);

    void update(UUID id, UserDto productDto);
    void delete(UUID id);
}
