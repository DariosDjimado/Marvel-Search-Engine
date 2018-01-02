package fr.tse.fise2.heapoverflow.models;

import fr.tse.fise2.heapoverflow.authentication.SaltRealm;
import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.UserRow;
import fr.tse.fise2.heapoverflow.database.UsersTable;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Observable;

/**
 * @author Darios DJIMADO
 */
public class UserAuthenticationModel extends Observable implements PasswordService {
    private static final transient Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationModel.class);
    private static Subject currentUser = null;
    private static UserAuthenticationModel instance;
    private PasswordService passwordService;

    private UserAuthenticationModel() {
        this.passwordService = new DefaultPasswordService();
        SaltRealm saltRealm = new SaltRealm();
        SecurityManager securityManager = new DefaultSecurityManager(saltRealm);
        SecurityUtils.setSecurityManager(securityManager);
        currentUser = SecurityUtils.getSubject();
    }

    @NotNull
    public static UserAuthenticationModel getInstance() {
        if (instance == null) {
            instance = new UserAuthenticationModel();
        }
        return instance;
    }


    @Nullable
    public static User getUser() {
        if (!currentUser.isAuthenticated()) {
            return null;
        }
        User user = null;
        try {
            // TODO DANGEROUS DO NOT USE USERNAME
            final UserRow userRow = UsersTable.findUserByUsername((String) currentUser.getPrincipal());
            user = getUser(userRow);
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        } catch (ExpiredSessionException | UnknownSessionException e) {
            getInstance().stopCurrentSession();
            getInstance().logout();
        }
        return user;
    }

    @NotNull
    private static User getUser(UserRow userRow) {
        return new User(userRow.getUsername(), userRow.getId(), userRow.getFirstName(), userRow.getLastName());
    }


    public void login(String username, char[] password) throws Exception {
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
            try {
                this.stopCurrentSession();
                currentUser = SecurityUtils.getSubject();
                currentUser.login(token);
                if (currentUser.isAuthenticated()) {
                    // one hour
                    currentUser.getSession().setTimeout(3600000);
                    setChanged();
                    notifyObservers(username);
                }
            } catch (Exception e) {
                AppErrorHandler.onError(e);
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage(), e);
                }
                throw new Exception(e);
            }
        }
    }

    public void logout() {
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        setChanged();
        notifyObservers(null);
    }

    public void signUp(UserRow userRow) throws SQLException {
        String encryptedPassword = this.encryptPassword(userRow.getPassword());
        userRow.setPassword(encryptedPassword);
        UsersTable.insertUser(userRow);
    }

    @Override
    public String encryptPassword(Object o) throws IllegalArgumentException {
        return passwordService.encryptPassword(o);
    }

    @Override
    public boolean passwordsMatch(Object o, String s) {
        return passwordService.passwordsMatch(o, s);
    }

    private void stopCurrentSession() {
        try {
            if (currentUser == null) {
                return;
            }
            currentUser.logout();
            // we also want to stop the current session
            Session session = currentUser.getSession(false);
            if (session != null) {
                session.stop();
            }

        } catch (InvalidSessionException e) {
            // if we can't logout the user # UnknownAccountException can bring us here.
            Session session = currentUser.getSession(false);
            if (session != null) {
                session.stop();
            }
        }
    }
}
