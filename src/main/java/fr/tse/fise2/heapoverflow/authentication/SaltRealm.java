package fr.tse.fise2.heapoverflow.authentication;

import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaltRealm extends JdbcRealm {
    private final ConnectionDB connectionDB;

    public SaltRealm(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();
        AuthenticationInfo info = null;
        if (username == null) {
            throw new AccountException("Null username");
        }
        final String password = getPasswordForUser(username);
        if (password == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        info = simpleAuthenticationInfo;
        return info;
    }

    private String getPasswordForUser(String username) {
        String password = null;
        try (PreparedStatement preparedStatement = this.connectionDB.getConnection().prepareStatement(
                "SELECT PASSWORD FROM USERS WHERE USERNAME = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean found = false;
            while (resultSet.next()) {
                if (found) {
                    throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                }
                password = resultSet.getString("password");
                found = true;
            }
        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            throw new AuthenticationException(message, e);
        }
        return password;
    }
}
