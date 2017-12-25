package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Darios DJIMADO
 */
public class UICustomComponents {
}

final class DefaultButton extends JButton {
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

final class PrimaryButton extends JButton {
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

final class PlusButton extends JButton {

    /**
     * Creates a button with text.
     */
    public PlusButton() {
        super("+");
        //this.setOpaque(true);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(true);
        this.setForeground(UIColor.MAIN_BACKGROUND_COLOR);
        this.setBackground(UIColor.PRIMARY_COLOR);
    }
}

final class AccentButton extends JButton {
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

final class BoxShadow implements Border {


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

final class CircleButton extends JButton {
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

final class CustomRadioButton extends JRadioButton {
    /**
     * Creates an initially unselected radio button
     * with no set text.
     */
    public CustomRadioButton() {
        super();
        this.setOpaque(true);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(true);
        this.setForeground(UIColor.MAIN_BACKGROUND_COLOR);
        this.setBackground(UIColor.PRIMARY_COLOR);
    }
}

final class CustomProgressBar extends JProgressBar {
    /**
     * Creates a horizontal progress bar
     * that displays a border but no progress string.
     * The initial and minimum values are 0,
     * and the maximum is 100.
     *
     * @see #setOrientation
     * @see #setBorderPainted
     * @see #setStringPainted
     * @see #setString
     * @see #setIndeterminate
     */
    public CustomProgressBar() {
        super();
        this.setForeground(UIColor.SUCCESS_COLOR);
        this.setBackground(UIColor.DEFAULT_COLOR);
        this.setBorderPainted(false);
    }
}

abstract class ButtonFormat extends JButton {
    /**
     * Creates a button with no set text or icon.
     */
    public ButtonFormat() {
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);

        this.setMaximumSize(new Dimension(25, 20));
        this.setPreferredSize(new Dimension(25, 20));
        this.setMinimumSize(new Dimension(25, 20));
    }
}

final class CustomTextField extends JTextField {
    private final String placeholder;
    private int minTextSize = 2;
    private int maxTextSize = 100;

    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        this.setBorder(BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR));

        this.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(MaterialPlaceholder.getDefaultBorder());
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().length() < minTextSize || getText().length() > maxTextSize) {
                    setBorder(MaterialPlaceholder.getInvalidBorder());
                } else {
                    setBorder(MaterialPlaceholder.getValidBorder());
                }
            }
        });


    }

    public int getMinTextSize() {
        return minTextSize;
    }

    public void setMinTextSize(int minTextSize) {
        this.minTextSize = minTextSize;
    }

    public int getMaxTextSize() {
        return maxTextSize;
    }

    public void setMaxTextSize(int maxTextSize) {
        this.maxTextSize = maxTextSize;
    }

    public void resetTextField() {
        this.setText("");
        this.setBorder(MaterialPlaceholder.getValidBorder());
    }

    public boolean validateField() {
        return getText().length() >= this.minTextSize && getText().length() <= this.maxTextSize;
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
        super.paintComponent(g);
        MaterialPlaceholder.drawPlaceholder(this.placeholder, this.getText(), g, this.getInsets().top, this.getInsets().left);
    }
}

final class CustomPasswordTextField extends JPasswordField {
    private final String placeholder;
    private int minTextSize = 4;
    private int maxTextSize = 100;

    public CustomPasswordTextField(String placeholder) {
        this.placeholder = placeholder;
        this.setBorder(BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR));
        this.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(MaterialPlaceholder.getDefaultBorder());
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length < minTextSize || getPassword().length > maxTextSize) {
                    setBorder(MaterialPlaceholder.getInvalidBorder());
                } else {
                    setBorder(MaterialPlaceholder.getValidBorder());
                }
            }
        });

    }

    public void resetTextField() {
        this.setText("");
        this.setBorder(MaterialPlaceholder.getValidBorder());
    }

    public boolean validateField() {
        return getPassword().length >= minTextSize && getPassword().length <= maxTextSize;
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
        super.paintComponent(g);
        MaterialPlaceholder.drawPlaceholder(this.placeholder, String.valueOf(this.getPassword()), g, this.getInsets().top, this.getInsets().left);
    }
}

final class MaterialPlaceholder {
    public static void drawPlaceholder(String placeholder, String text, Graphics g, int insetTop, int insetLeft) {
        if (placeholder.length() == 0 || text.length() > 0) {
            return;
        }
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.drawString(placeholder, insetLeft, g.getFontMetrics().getMaxAscent() + insetTop + 5);
    }

    public static Border getInvalidBorder() {
        return BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.ACCENT_COLOR);
    }

    public static Border getValidBorder() {
        return BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR);
    }

    public static Border getDefaultBorder() {
        return BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.PRIMARY_COLOR);

    }
}
