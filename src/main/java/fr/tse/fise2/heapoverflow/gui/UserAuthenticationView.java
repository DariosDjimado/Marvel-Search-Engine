package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.controllers.UserAuthenticationController;
import fr.tse.fise2.heapoverflow.database.UserRow;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Darios DJIMADO
 */
class UserAuthenticationView extends JPanel implements Observer {
    private final UserAuthenticationModel model;
    private final UserAuthenticationController controller;
    private final JButton logInButton;
    private final JButton signedInButton;
    private final UI ui;
    private JPanel authenticationPanel;
    private CustomTextField firstNameTextField;
    private CustomTextField lastNameTextField;
    private CustomTextField emailTextField;
    private CustomPasswordTextField passwordTextField;
    private CustomPasswordTextField confirmPasswordTextField;
    private JButton signUpButton;
    private JButton cancelSignUpButton;
    private JButton confirmActionButton;
    private CustomTextField usernameTextField;
    private JLabel signUpLabel;


    UserAuthenticationView(UI ui, UserAuthenticationModel model, UserAuthenticationController controller) {
        this.model = model;
        this.controller = controller;
        this.ui = ui;

        this.logInButton = new DefaultButton("Log in");
        this.logInButton.setForeground(UIColor.ACCENT_COLOR);
        this.signedInButton = new CircleButton("p");
        this.signedInButton.setBackground(UIColor.SIGNED_IN_TEXT_COLOR);
        this.signUpButton = new PrimaryButton("Sign up");

        // sign in button
        this.logInButton.setOpaque(false);
        this.logInButton.setFocusPainted(false);
        this.logInButton.setBorderPainted(false);
        this.logInButton.setContentAreaFilled(false);

        // hide signed in button
        this.signedInButton.setVisible(false);

        this.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());
        this.add(this.signedInButton, BorderLayout.CENTER);
        this.add(this.signUpButton, BorderLayout.EAST);
        this.add(this.logInButton, BorderLayout.WEST);

        this.configActionsListeners();
    }

    private void buildAuthenticationPanel() {
        this.authenticationPanel = new JPanel();
        this.authenticationPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.authenticationPanel.setLayout(new GridBagLayout());
        this.authenticationPanel.setMaximumSize(new Dimension(400, 380));
        this.authenticationPanel.setMinimumSize(new Dimension(400, 380));
        this.authenticationPanel.setPreferredSize(new Dimension(400, 380));
        this.emailTextField = new CustomTextField("Email");
        this.emailTextField.setColumns(3);
        this.emailTextField.setPreferredSize(new Dimension(47, 30));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 20);
        this.authenticationPanel.add(this.emailTextField, gbc);
        this.passwordTextField = new CustomPasswordTextField("Password");
        this.passwordTextField.setPreferredSize(new Dimension(59, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 20);
        this.authenticationPanel.add(this.passwordTextField, gbc);
        this.confirmPasswordTextField = new CustomPasswordTextField("Confirm password");
        this.confirmPasswordTextField.setPreferredSize(new Dimension(14, 30));
        this.confirmPasswordTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 20);
        this.authenticationPanel.add(this.confirmPasswordTextField, gbc);
        this.firstNameTextField = new CustomTextField("First name");
        this.firstNameTextField.setMinimumSize(new Dimension(70, 30));
        this.firstNameTextField.setPreferredSize(new Dimension(70, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 10);
        this.authenticationPanel.add(this.firstNameTextField, gbc);
        this.lastNameTextField = new CustomTextField("Last name");
        this.lastNameTextField.setMinimumSize(new Dimension(70, 30));
        this.lastNameTextField.setPreferredSize(new Dimension(70, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 0, 20);
        this.authenticationPanel.add(this.lastNameTextField, gbc);
        final JPanel bottomButtonsPanel = new JPanel();
        bottomButtonsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(30, 0, 0, 20);
        this.authenticationPanel.add(bottomButtonsPanel, gbc);
        this.cancelSignUpButton = new DefaultButton("Cancel");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomButtonsPanel.add(this.cancelSignUpButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomButtonsPanel.add(spacer1, gbc);
        this.confirmActionButton = new PrimaryButton("Sign Up");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomButtonsPanel.add(this.confirmActionButton, gbc);
        this.usernameTextField = new CustomTextField("Username");
        this.usernameTextField.setPreferredSize(new Dimension(14, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 20);
        this.authenticationPanel.add(this.usernameTextField, gbc);
        this.signUpLabel = new JLabel();
        this.signUpLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIColor.HEADER_SHADOW_COLOR));
        Font signUpLabelFont = this.authenticationPanel.getFont().deriveFont(this.authenticationPanel.getFont().getStyle(), 28);
        this.signUpLabel.setFont(signUpLabelFont);
        this.signUpLabel.setPreferredSize(new Dimension(150, 50));
        this.signUpLabel.setText("Sign Up");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 0);
        this.authenticationPanel.add(this.signUpLabel, gbc);
    }

    private void resetAuthenticationPanel() {
        this.lastNameTextField.resetTextField();
        this.firstNameTextField.resetTextField();
        this.usernameTextField.resetTextField();
        this.emailTextField.resetTextField();
        this.passwordTextField.resetTextField();
        this.confirmPasswordTextField.resetTextField();
    }

    private void showSignUpField(boolean b) {
        this.resetAuthenticationPanel();


        // hide confirm password
        this.firstNameTextField.setVisible(b);
        this.lastNameTextField.setVisible(b);
        this.emailTextField.setVisible(b);
        this.confirmPasswordTextField.setVisible(b);

        if (b) {
            String actionText = "Sign Up";
            this.signUpLabel.setText(actionText);
            this.confirmActionButton.setText(actionText);
            this.authenticationPanel.setMaximumSize(new Dimension(400, 380));
            this.authenticationPanel.setMinimumSize(new Dimension(400, 380));
            this.authenticationPanel.setPreferredSize(new Dimension(400, 380));
        } else {
            String actionText = "Log In";
            this.signUpLabel.setText(actionText);
            this.confirmActionButton.setText(actionText);
            this.authenticationPanel.setMaximumSize(new Dimension(400, 230));
            this.authenticationPanel.setMinimumSize(new Dimension(400, 230));
            this.authenticationPanel.setPreferredSize(new Dimension(400, 230));
        }


    }

    private void configActionsListeners() {
        this.buildAuthenticationPanel();
        this.configureSignInActionListener();
        this.configureSignUpActionListener();
    }

    private void configureSignInActionListener() {
        // log in
        this.logInButton.addActionListener(e -> {
            // hide sign up fields
            this.showSignUpField(false);

            // create new dialog dynamically
            CustomDialog dialog = new CustomDialog(ui, "Authentication");

            // create new action listener and store its reference
            final ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (authenticationFieldsValidate(false)) {
                        boolean errorCurred = false;
                        try {
                            controller.login(usernameTextField.getText(), passwordTextField.getPassword());
                        } catch (Exception ex) {
                            AppErrorHandler.onError(ex);
                            JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Log in error",
                                    JOptionPane.ERROR_MESSAGE);
                            errorCurred = true;
                        }
                        if (!errorCurred) {
                            confirmActionButton.removeActionListener(this);
                            dialog.dispose();
                        }
                    }
                }
            };

            // add action listener
            this.confirmActionButton.addActionListener(actionListener);

            // when window is closing remove action listener
            dialog.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e event
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    confirmActionButton.removeActionListener(actionListener);
                }
            });

            // when cancel button is clicked remove action listener
            this.cancelSignUpButton.addActionListener(event -> {
                confirmActionButton.removeActionListener(actionListener);
                dialog.dispose();
            });


            dialog.add(this.authenticationPanel);
            dialog.customSetVisible();

        });
    }

    private void configureSignUpActionListener() {
        // sign up
        this.signUpButton.addActionListener((ActionEvent e) -> {
            if (this.signUpButton.getText().equals("Sign up")) {
                // show all fields
                this.showSignUpField(true);

                // create new dialog dynamically
                CustomDialog dialog = new CustomDialog(ui, "Authentication");

                // create new action listener and store its reference
                final ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (authenticationFieldsValidate(true)) {
                            UserRow userRow = new UserRow(usernameTextField.getText(),
                                    emailTextField.getText(),
                                    lastNameTextField.getText(),
                                    firstNameTextField.getText(),
                                    String.valueOf(passwordTextField.getPassword()));
                            boolean errorCurred = false;
                            try {
                                controller.signUp(userRow);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Sign Up error",
                                        JOptionPane.ERROR_MESSAGE);
                                errorCurred = true;
                            }
                            if (!errorCurred) {
                                confirmActionButton.removeActionListener(this);
                                JOptionPane.showMessageDialog(dialog, "Signed up successfully", "Sign Up",
                                        JOptionPane.INFORMATION_MESSAGE);
                                dialog.dispose();
                            }
                        }
                    }
                };

                // add action listener
                this.confirmActionButton.addActionListener(actionListener);

                // when window is closing remove action listener
                dialog.addWindowListener(new WindowAdapter() {
                    /**
                     * Invoked when a window is in the process of being closed.
                     * The close operation can be overridden at this point.
                     *
                     * @param e event
                     */
                    @Override
                    public void windowClosing(WindowEvent e) {
                        confirmActionButton.removeActionListener(actionListener);
                    }
                });

                // when cancel button is clicked remove action listener
                this.cancelSignUpButton.addActionListener(event -> {
                    confirmActionButton.removeActionListener(actionListener);
                    dialog.dispose();
                });


                dialog.add(this.authenticationPanel);
                dialog.customSetVisible();

            } else {
                this.controller.logout();
            }
        });
    }

    private boolean authenticationFieldsValidate(boolean isSignUp) {
        boolean loginValidation = this.usernameTextField.validateField() && this.passwordTextField.validateField();
        if (isSignUp) {
            return loginValidation &&
                    this.lastNameTextField.validateField() &&
                    this.firstNameTextField.validateField() &&
                    this.emailTextField.validateField() &&
                    this.confirmPasswordTextField.validateField()
                    && Arrays.equals(this.passwordTextField.getPassword(), this.confirmPasswordTextField.getPassword());
        } else {
            return loginValidation;
        }
    }


    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            if (arg == null) {
                this.logInButton.setVisible(true);
                this.signedInButton.setVisible(false);
                this.signUpButton.setText("Sign up");
            } else {
                this.logInButton.setVisible(false);
                this.signedInButton.setText(((String) arg).substring(0, 1));
                this.signedInButton.setVisible(true);
                this.signUpButton.setText("Logout");
            }
        }
    }
}
