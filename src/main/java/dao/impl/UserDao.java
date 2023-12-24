package dao.impl;

import config.db.ConnectionSingleton;
import dao.Dao;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static util.constants.SqlQueryConstants.*;
import static util.constants.SqlQueryConstants.SQL_GET_ALL_USERS_PAGED;

/**
 * Implementation of the DAO (Data Access Object) for managing User entities.
 */
public class UserDao implements Dao<User> {

    private final Connection connection;

    {
        try {
            connection = ConnectionSingleton.getConnection().open();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get db connection");
        }
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     * @throws RuntimeException If unable to fetch the user from the database.
     */
    @Override
    public Optional<User> get(UUID id) {
        try (var statement = prepare(SQL_GET_USER_BY_ID, id)) {
            try (var rs = statement.executeQuery()) {
                return rs.first()
                        ? Optional.of(buildUser(rs))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get user");
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list containing all users.
     * @throws RuntimeException If unable to fetch all users from the database.
     */
    @Override
    public List<User> getAll() {
        try (var statement = prepare(SQL_GET_ALL_USERS)) {
            try (var rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(buildUser(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all users");
        }
    }

    @Override
    public List<User> getAll(int size, int offset) {
        try (var statement = prepare(SQL_GET_ALL_USERS_PAGED, size, offset)) {
            try (var rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(buildUser(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all users");
        }
    }

    /**
     * Saves or updates a user in the database.
     *
     * @param user The user to be saved or updated.
     * @throws RuntimeException If unable to perform the save or update operation.
     */
    @Override
    public void save(User user) {
        try (var statement = user.getId() == null
                ? prepare(SQL_CREATE_USER, user.getName(), user.getSurname(), user.getEmail(), user.getAge())
                : prepare(SQL_UPDATE_USER, user.getName(), user.getSurname(), user.getEmail(), user.getAge(), user.getId())) {
            var rs = statement.executeUpdate();
            if (rs != 1) {
                throw new RuntimeException("Unable to save or update");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     * @throws RuntimeException If unable to delete the user.
     */
    @Override
    public void delete(UUID id) {
        try (var statement = prepare(SQL_DELETE_USER, id)) {
            var rs = statement.executeUpdate();
            if (rs != 1) {
                throw new RuntimeException("Unable delete user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prepares a PreparedStatement with the given SQL query and parameters.
     *
     * @param query  The SQL query to be prepared.
     * @param params The parameters to be set in the query.
     * @return The prepared PreparedStatement.
     * @throws RuntimeException If unable to prepare the PreparedStatement.
     */
    private PreparedStatement prepare(String query, Object... params) {
        try {
            var statement = connection.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a User entity from the ResultSet obtained from the database.
     *
     * @param rs The ResultSet containing user data.
     * @return A User entity constructed from the ResultSet.
     * @throws SQLException If an SQL error occurs during ResultSet parsing.
     */
    private User buildUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(UUID.fromString(rs.getString("id")))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .email(rs.getString("email"))
                .age(rs.getInt("age"))
                .build();
    }

}
