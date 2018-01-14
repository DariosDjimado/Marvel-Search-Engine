package fr.tse.fise2.heapoverflow.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Darios DJIMADO
 */
public class UsersTable {
    public static void insertUser(UserRow userRow) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("INSERT INTO users(username,email,last_name,first_name,password) VALUES (?,?,?,?,?)");
        preparedStatement.setString(1, userRow.getUsername());
        preparedStatement.setString(2, userRow.getEmail());
        preparedStatement.setString(3, userRow.getLastName());
        preparedStatement.setString(4, userRow.getFirstName());
        preparedStatement.setString(5, userRow.getPassword());
        preparedStatement.execute();
    }

    /**
     * Finds user by id
     *
     * @param id user's unique id
     * @return UserRow user's info
     */
    public static UserRow findUserById(int id) {
        return getUserRow(id);
    }

    /**
     * Finds user by id
     *
     * @param username user's unique username
     * @return UserRow user's info
     */
    public static UserRow findUserByUsername(String username) {
        return getUserRow(username, "SELECT * FROM users WHERE username = ?");
    }


    /**
     * Finds user by id
     *
     * @param email user's unique email
     * @return UserRow user's info
     */
    public static UserRow findUserByEmail(String email) {
        return getUserRow(email, "SELECT * FROM users WHERE email = ?");
    }

    private static UserRow getUserRow(int id) {
        return getUserRow(id, null, "SELECT * FROM users WHERE id = ?");
    }

    private static UserRow getUserRow(String uniqueConstraint, String s) {
        return getUserRow(Integer.MIN_VALUE, uniqueConstraint, s);
    }

    private static UserRow getUserRow(int id, String uniqueConstraint, String s) {

        UserRow userRow = null;
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement(s)) {

            if (uniqueConstraint != null) {
                preparedStatement.setString(1, uniqueConstraint);
            } else {
                preparedStatement.setInt(1, id);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userRow = new UserRow(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("email"), resultSet.getString("last_name"),
                        resultSet.getString("first_name"), resultSet.getString("password"));
            }
        } catch (SQLException e) {

        }
        return userRow;
    }

}
