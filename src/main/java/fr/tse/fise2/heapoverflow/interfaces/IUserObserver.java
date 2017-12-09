package fr.tse.fise2.heapoverflow.interfaces;

public interface IUserObserver {
    /**
     * Notifies login
     *
     * @param username user who is authenticated
     */
    void onLogin(String username);

    /**
     * Notifies logout
     */
    void onLogout();
}
