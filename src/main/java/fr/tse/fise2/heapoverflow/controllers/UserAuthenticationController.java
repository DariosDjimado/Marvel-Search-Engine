package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.database.UserRow;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import java.sql.SQLException;

public class UserAuthenticationController {

    private final UserAuthenticationModel model;

    public UserAuthenticationController(UserAuthenticationModel model) {
        this.model = model;
    }

    public void login(String username, char[] password) throws Exception {
        model.login(username, password);
    }

    public void logout() {
        model.logout();
    }

    public void signUp(UserRow userRow) throws SQLException {
        this.model.signUp(userRow);
    }
}
