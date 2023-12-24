package service.impl;

import dao.Dao;
import dao.impl.UserDao;
import dto.UserDto;
import entity.User;
import exception.UserNotFoundException;
import mapper.UserMapper;
import mapper.UserMapperImpl;
import service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface providing user-related operations.
 */
public class UserServiceImpl implements UserService {

    private final Dao<User> userDao = new UserDao();
    private final UserMapper mapper = new UserMapperImpl();

    /**
     * Retrieves a UserDto by ID.
     *
     * @param id The unique identifier of the user.
     * @return UserDto containing user details.
     */
    @Override
    public UserDto get(UUID id) {
        Optional<User> received = userDao.get(id);
        return mapper.toUserDto(received.get());
    }

    /**
     * Retrieves all UserDtos.
     *
     * @return List of UserDto containing details of all users.
     */
    @Override
    public List<UserDto> getAll() {
        List<User> received = userDao.getAll();
        return received.stream()
                .map(u -> mapper.toUserDto((User) u))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user based on the provided UserDto.
     *
     * @param userDto UserDto containing user details.
     */
    @Override
    public void create(UserDto userDto) {
        User user = mapper.toUser(userDto);
        userDao.save(user);
    }

    /**
     * Updates an existing user based on the provided UserDto and ID.
     *
     * @param id      The unique identifier of the user to be updated.
     * @param userDto UserDto containing updated user details.
     */
    @Override
    public void update(UUID id, UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setId(id);
        userDao.save(user);
    }

    /**
     * Deletes a user based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    @Override
    public void delete(UUID id) {
        userDao.delete(id);
    }
}
