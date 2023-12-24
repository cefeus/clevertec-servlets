package util.constants;

import lombok.experimental.UtilityClass;

/**
 * Utility class containing SQL queries constants for user-related operations.
 */
@UtilityClass
public class SqlQueryConstants {

    public static String SQL_GET_USER_BY_ID = "SELECT * FROM \"reflection-task\".users WHERE id = ?";
    public static String SQL_GET_ALL_USERS = "SELECT * FROM \"reflection-task\".users";
    public static String SQL_GET_ALL_USERS_PAGED = "SELECT * FROM \"reflection-task\".users LIMIT ? OFFSET ?";
    public static String SQL_CREATE_USER = "INSERT INTO \"reflection-task\".users (name, surname, email, age) VALUES ( ?, ?, ?, ?)";
    public static String SQL_UPDATE_USER = "UPDATE \"reflection-task\".users SET name = ?, surname = ?, email = ?, age = ? WHERE id = ?";
    public static String SQL_DELETE_USER = "DELETE FROM \"reflection-task\".users WHERE id = ?";

}
