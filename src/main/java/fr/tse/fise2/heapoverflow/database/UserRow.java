package fr.tse.fise2.heapoverflow.database;

/**
 * @author Darios DJIMADO
 */
public final class UserRow {
    /**
     * user's unique id (primary key)
     */
    private int id;
    /**
     * user's unique username
     */
    private String username;
    /**
     * user's unique email
     */
    private String email;
    /**
     * user's last name
     */
    private String lastName;
    /**
     * user's first name
     */
    private String firstName;
    /**
     * user's encrypted password
     */
    private String password;

    /**
     * Constructor
     *
     * @param username  user's unique username
     * @param email     user's unique email
     * @param lastName  user's last name
     * @param firstName user's first name
     * @param password  user's encrypted password
     */
    public UserRow(String username, String email, String lastName, String firstName, String password) {
        this(Integer.MIN_VALUE, username, email, lastName, firstName, password);
    }

    /**
     * Constructor
     *
     * @param id        user's unique id (primary key)
     * @param username  user's unique username
     * @param email     user's unique email
     * @param lastName  user's last name
     * @param firstName user's first name
     * @param password  user's encrypted password
     */
    public UserRow(int id, String username, String email, String lastName, String firstName, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    /**
     * @return int user's unique id (primary key)
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String user's unique username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return user's unique email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return user's encrypted password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
