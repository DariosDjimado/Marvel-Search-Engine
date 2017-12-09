package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.IUserObserver;
import fr.tse.fise2.heapoverflow.interfaces.UIComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UITopComponent implements UIComponent, IUserObserver {

    private final JButton logInButton;
    private final JButton signedInButton;
    private final JButton signUpButton;
    private final JButton libraryButton;
    private final JPanel topPanel;
    private final JPanel signContainer;
    private final JLabel confirmPasswordLabel;
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

        this.libraryButton.setBackground(UIColor.ACCENT_COLOR);
        this.libraryButton.setForeground(UIColor.HEADER_TEXT_COLOR);
        // shadow
        this.topPanel.setLayout(new BorderLayout());
        this.topPanel.add(this.libraryButton, BorderLayout.WEST);
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

    private static final class BoxShadow implements Border {


        /**
         * Paints the border for the specified component with the specified
         * position and size.
         *
         * @param c      the component for which this border is being painted
         * @param g      the paint graphics
         * @param x      the x position of the painted border
         * @param y      the y position of the painted border
         * @param width  the width of the painted border
         * @param height the height of the painted border
         */
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(UIColor.HEADER_SHADOW_COLOR);
            g.fillRect(x, y + height - 4, width, 4);
        }

        /**
         * Returns the insets of the border.
         *
         * @param c the component for which this border insets value applies
         */
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 8, 8);
        }

        /**
         * Returns whether or not the border is opaque.  If the border
         * is opaque, it is responsible for filling in it's own
         * background when painting.
         */
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    private static final class CircleButton extends JButton {
        private int radius;

        /**
         * Creates a button with text.
         *
         * @param text the text of the button
         */
        CircleButton(String text) {
            super(text);
            this.setMinimumSize(new Dimension(50, 50));
            this.setPreferredSize(new Dimension(50, 50));
            this.radius = 18;

            this.setOpaque(false);
            this.setFocusPainted(false);
            this.setBorderPainted(false);
        }

        /**
         * Calls the UI delegate's paint method, if the UI delegate
         * is non-<code>null</code>.  We pass the delegate a copy of the
         * <code>Graphics</code> object to protect the rest of the
         * paint code from irrevocable changes
         * (for example, <code>Graphics.translate</code>).
         * <p>
         * If you override this in a subclass you should not make permanent
         * changes to the passed in <code>Graphics</code>. For example, you
         * should not alter the clip <code>Rectangle</code> or modify the
         * transform. If you need to do these operations you may find it
         * easier to create a new <code>Graphics</code> from the passed in
         * <code>Graphics</code> and manipulate it. Further, if you do not
         * invoker super's implementation you must honor the opaque property,
         * that is
         * if this component is opaque, you must completely fill in the background
         * in a non-opaque color. If you do not honor the opaque property you
         * will likely see visual artifacts.
         * <p>
         * The passed in <code>Graphics</code> object might
         * have a transform other than the identify transform
         * installed on it.  In this case, you might get
         * unexpected results if you cumulatively apply
         * another transform.
         *
         * @param g the <code>Graphics</code> object to protect
         * @see #paint
         * @see ComponentUI
         */
        @Override
        protected void paintComponent(Graphics g) {
            final Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setPaint(UIColor.SIGNED_IN_TEXT_COLOR);
            // fill oval
            g2d.fillOval(5, 1, radius * 2, radius * 2);

            g2d.setPaint(Color.WHITE);

            Font currentFont = g2d.getFont();
            FontMetrics metrics = g2d.getFontMetrics(currentFont);
            // center the text
            int x = 4 + (radius * 2 - metrics.stringWidth(getText().toUpperCase())) / 2;
            int y = 2 + ((radius * 2 - metrics.getHeight()) / 2) + metrics.getAscent();
            // increase current current font
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
            g2d.setFont(newFont);
            // draw the text
            g2d.drawString(getText().toUpperCase(), x, y);
            g2d.dispose();
        }

    }

    private static final class DefaultButton extends JButton {
        /**
         * Creates a button with text.
         *
         * @param text the text of the button
         */
        public DefaultButton(String text) {
            super(text);
            this.setOpaque(false);
            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
        }
    }

    private static final class PrimaryButton extends JButton {
        /**
         * Creates a button with text.
         *
         * @param text the text of the button
         */
        public PrimaryButton(String text) {
            super(text);
            this.setOpaque(true);
            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(true);
            this.setForeground(UIColor.MAIN_BACKGROUND_COLOR);
            this.setBackground(UIColor.PRIMARY_COLOR);
        }
    }

    private static final class AccentButton extends JButton {
        /**
         * Creates a button with text.
         *
         * @param text the text of the button
         */
        public AccentButton(String text) {
            super(text);
            this.setOpaque(true);
            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(true);
            this.setForeground(UIColor.MAIN_BACKGROUND_COLOR);
            this.setBackground(UIColor.ACCENT_COLOR);
        }
    }
}