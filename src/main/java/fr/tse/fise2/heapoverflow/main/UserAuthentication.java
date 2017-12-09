package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.interfaces.IUserObserver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class UserAuthentication implements PasswordService {
    private static final transient Logger log = LoggerFactory.getLogger(UserAuthentication.class);
    private static Set<IUserObserver> userObservers = new HashSet<>();
    private final Subject currentUser;
    private final PasswordService passwordService;

    UserAuthentication() {
        Ini ini = new Ini();

        passwordService = new DefaultPasswordService();
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        currentUser = SecurityUtils.getSubject();
    }

    public static void subscribe(IUserObserver userObserver) {
        userObservers.add(userObserver);
    }

    public static void unsubscribe(IUserObserver userObserver) {
        userObservers.remove(userObserver);
    }

    public void login(String username, String password) {
        if (!currentUser.isAuthenticated()) {

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);

            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }

            for (IUserObserver observer : userObservers) {
                observer.onLogin(username);
            }
        }
    }

    public void logout() {
        currentUser.logout();
        for (IUserObserver observer : userObservers) {
            observer.onLogout();
        }
    }

    @Override
    public String encryptPassword(Object o) throws IllegalArgumentException {
        return passwordService.encryptPassword(o);
    }

    @Override
    public boolean passwordsMatch(Object o, String s) {
        return passwordService.passwordsMatch(o, s);
    }
}
