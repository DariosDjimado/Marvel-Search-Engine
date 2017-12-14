package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.IUserObserver;
import fr.tse.fise2.heapoverflow.interfaces.UIComponent;

import javax.swing.*;
import java.awt.*;

public class UITopComponent implements UIComponent, IUserObserver {

    private final JButton logInButton;
    private final JButton signedInButton;
    private final JButton signUpButton;
    private final JButton libraryButton;
    private final JPanel topPanel;
    private final JPanel signContainer;
    private final JLabel confirmPasswordLabel;
    private final JButton createCollectionButton;
    private final JPanel topRightPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPasswordTextField;
    private JPanel authenticationPanel;
    private JLabel usernameLabel;
    private JTextField emailTextField;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;


    UITopComponent(final JPanel topPanel) {
        this.libraryButton = new AccentButton("My library");
        this.logInButton = new DefaultButton("Log in");
        this.logInButton.setForeground(UIColor.ACCENT_COLOR);
        this.signedInButton = new CircleButton("p");


        this.signUpButton = new PrimaryButton("Sign up");
        this.topPanel = topPanel;
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setBorder(new BoxShadow());
        this.signContainer = new JPanel();
        this.confirmPasswordLabel = new JLabel();

        this.createCollectionButton = new DefaultButton("Create Collections");
        this.topRightPanel = new JPanel();
    }

    public void init() {
        this.setSize();
        this.build();
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    @Override
    public void setSize() {
        final Dimension minDimension = new Dimension(200, 50);
        this.topPanel.setMinimumSize(minDimension);
        this.topPanel.setPreferredSize(minDimension);
    }

    @Override
    public void build() {

        this.topPanel.setLayout(new BorderLayout());

        // top right panel
        this.createCollectionButton.setForeground(UIColor.PRIMARY_COLOR);
        this.libraryButton.setBackground(UIColor.ACCENT_COLOR);
        this.libraryButton.setForeground(UIColor.HEADER_TEXT_COLOR);

        this.topRightPanel.setLayout(new BorderLayout());
        this.topRightPanel.add(this.libraryButton, BorderLayout.WEST);
        this.topRightPanel.add(this.createCollectionButton, BorderLayout.EAST);
        this.topPanel.add(this.topRightPanel, BorderLayout.WEST);


        // sign in button
        this.logInButton.setOpaque(false);
        this.logInButton.setFocusPainted(false);
        this.logInButton.setBorderPainted(false);
        this.logInButton.setContentAreaFilled(false);

        // hide signed in button
        this.signedInButton.setVisible(false);

        this.signContainer.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.signContainer.setLayout(new BorderLayout());
        this.signContainer.add(this.signedInButton, BorderLayout.CENTER);
        this.signContainer.add(this.signUpButton, BorderLayout.EAST);
        this.signContainer.add(this.logInButton, BorderLayout.WEST);

        // shadow
        this.topPanel.add(this.signContainer, BorderLayout.EAST);


    }

    @Override
    public void setVisible() {

    }

    public void showSignUpField(boolean b) {
        // hide confirm password
        this.confirmPasswordTextField.setVisible(b);
        this.confirmPasswordLabel.setVisible(b);
        // hide first name
        this.firstNameLabel.setVisible(b);
        this.firstNameTextField.setVisible(b);
        // hide last name
        this.lastNameLabel.setVisible(b);
        this.lastNameTextField.setVisible(b);
        // username
        this.usernameLabel.setVisible(b);
        this.usernameTextField.setVisible(b);

        if (!b) {
            this.authenticationPanel.setMinimumSize(new Dimension(250, 100));
            this.authenticationPanel.setPreferredSize(new Dimension(250, 100));
        } else {
            this.authenticationPanel.setMinimumSize(new Dimension(250, 335));
            this.authenticationPanel.setPreferredSize(new Dimension(250, 335));
        }
    }

    public void buildAuthenticationPanel() {
        authenticationPanel = new JPanel();
        authenticationPanel.setLayout(new GridBagLayout());
        authenticationPanel.setMinimumSize(new Dimension(250, 100));
        authenticationPanel.setPreferredSize(new Dimension(250, 100));
        usernameLabel = new JLabel();
        usernameLabel.setText("Username :");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(usernameLabel, gbc);
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(passwordLabel, gbc);
        confirmPasswordLabel.setText("Confirm Password :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(confirmPasswordLabel, gbc);
        usernameTextField = new JTextField();
        usernameTextField.setMinimumSize(new Dimension(100, 30));
        usernameTextField.setPreferredSize(new Dimension(100, 30));
        usernameTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(usernameTextField, gbc);
        confirmPasswordTextField = new JPasswordField();
        confirmPasswordTextField.setMinimumSize(new Dimension(14, 30));
        confirmPasswordTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(confirmPasswordTextField, gbc);
        lastNameLabel = new JLabel();
        lastNameLabel.setText("Last name :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(lastNameLabel, gbc);
        lastNameTextField = new JTextField();
        lastNameTextField.setMinimumSize(new Dimension(14, 30));
        lastNameTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(lastNameTextField, gbc);
        firstNameTextField = new JTextField();
        firstNameTextField.setMinimumSize(new Dimension(14, 30));
        firstNameTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(firstNameTextField, gbc);
        firstNameLabel = new JLabel();
        firstNameLabel.setText("First name :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(firstNameLabel, gbc);
        emailTextField = new JTextField();
        emailTextField.setMinimumSize(new Dimension(14, 30));
        emailTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(emailTextField, gbc);
        passwordTextField = new JPasswordField();
        passwordTextField.setMinimumSize(new Dimension(14, 30));
        passwordTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        authenticationPanel.add(passwordTextField, gbc);
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        authenticationPanel.add(emailLabel, gbc);
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JPasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public JPasswordField getConfirmPasswordTextField() {
        return confirmPasswordTextField;
    }

    public JPanel getAuthenticationPanel() {
        return authenticationPanel;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JButton getCreateCollectionButton() {
        return createCollectionButton;
    }

    /**
     * Notifies login
     *
     * @param username user who is authenticated
     */
    @Override
    public void onLogin(String username) {
        this.logInButton.setVisible(false);
        this.signedInButton.setText(username.substring(0, 1));
        this.signedInButton.setVisible(true);
        this.signUpButton.setText("Logout");
    }

    /**
     * Notifies logout
     */
    @Override
    public void onLogout() {
        this.logInButton.setVisible(true);
        this.signedInButton.setVisible(false);
        this.signUpButton.setText("Sign up");
    }
}