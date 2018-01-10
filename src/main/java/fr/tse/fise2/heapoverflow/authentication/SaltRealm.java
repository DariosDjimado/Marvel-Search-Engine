package fr.tse.fise2.heapoverflow.authentication;

import fr.tse.fise2.heapoverflow.database.UserRow;
import fr.tse.fise2.heapoverflow.database.UsersTable;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;

import java.sql.SQLException;

public class SaltRealm extends JdbcRealm {
    private final PasswordService passwordService;

    public SaltRealm() {
        this.passwordService = new DefaultPasswordService();
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();
        final char[] password = usernamePasswordToken.getPassword();

        AuthenticationInfo info;
        if (username == null) {
            throw new AccountException("Null username");
        }
        final UserRow userRow = getUser(username);

        if (userRow == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        if (!this.passwordService.passwordsMatch(password, userRow.getPassword())) {
            throw new UnknownAccountException(" password No account found for user [" + username + "]");
        }


        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        // TODO DANGEROUS DO NOT USE USERNAME
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(String.valueOf(userRow.getUsername())));
        info = simpleAuthenticationInfo;
        return info;
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        return super.getAuthenticationCacheKey(token);
    }

    private UserRow getUser(String username) {
        UserRow userRow;
        try {
            userRow = UsersTable.findUserByUsername(username);
        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            throw new AuthenticationException(message, e);
        }
        return userRow;
    }
}
