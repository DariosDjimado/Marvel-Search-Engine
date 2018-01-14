package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

final class DefaultButton extends JButton {
    /**
     * Creates a button with text.
     *
     * @param text the text of the button
     */
    DefaultButton(String text) {
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
    PrimaryButton(String text) {
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
    AccentButton(String text) {
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
        g.fillRect(x, y + height - 4, width, 1);
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

        g2d.setPaint(this.getBackground());
        // fill oval
        g2d.fillOval(5, 1, radius * 2, radius * 2);

        g2d.setPaint(Color.WHITE);

        Font currentFont = g2d.getFont();
        // increase current current font
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        g2d.setFont(newFont);

        FontMetrics metrics = g2d.getFontMetrics(newFont);
        // center the text
        int x = ((radius * 2 - metrics.stringWidth(getText().toUpperCase())) / 2) + 5;
        int y = ((radius * 2 - metrics.getHeight()) / 2) + metrics.getAscent();

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
    CustomRadioButton() {
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
    CustomProgressBar() {
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
    ButtonFormat() {
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

    CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        this.setBorder(BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR));

        this.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e focus event
             */
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(MaterialPlaceholder.getDefaultBorder());
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e focus event
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

    CustomPasswordTextField(String placeholder) {
        this.placeholder = placeholder;
        this.setBorder(BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR));
        this.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e focus event
             */
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(MaterialPlaceholder.getDefaultBorder());
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e focus event
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

final class CustomDialog extends JDialog {
    private final Frame owner;

    /**
     * Creates a dialog with the specified title, owner {@code Frame}
     * and modality. If {@code owner} is {@code null},
     * a shared, hidden frame will be set as the owner of this dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: Any popup components ({@code JComboBox},
     * {@code JPopupMenu}, {@code JMenuBar})
     * created within a modal dialog will be forced to be lightweight.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param title the {@code String} to display in the dialog's
     *              title bar
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *                           returns {@code true}.
     * @see ModalityType
     * @see ModalityType#MODELESS
     * @see Dialog#DEFAULT_MODALITY_TYPE
     * @see Dialog#setModal
     * @see Dialog#setModalityType
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    CustomDialog(Frame owner, String title) {
        super(owner, title, true);
        this.owner = owner;
    }

    public void customSetVisible() {
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIColor.HEADER_SHADOW_COLOR));
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(this.owner);
        this.setVisible(true);
    }
}

final class CustomTextArea extends JTextArea {

    private String placeholder;

    /**
     * Constructs a new TextArea.  A default model is set, the initial string
     * is null, and rows/columns are set to 0.
     */
    CustomTextArea(String placeholder) {
        this.setLineWrap(true);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, UISize.TEXT_FIELD_BORDER_BOTTOM, 0, UIColor.TEXT_FIELD_DISABLE_COLOR));
        this.placeholder = placeholder;
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
        MaterialPlaceholder.drawPlaceholder(this.placeholder, String.valueOf(this.getText()), g, -5, this.getInsets().left);
    }
}

final class CustomScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(UIColor.SCROLLBAR_TRACK);
        graphics2D.fillRect(trackBounds.x, trackBounds.y, c.getWidth(), c.getHeight());
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(UIColor.SCROLLBAR_THUMB);
        graphics2D.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createEmptyButton();
    }

    private JButton createEmptyButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}

final class CustomScrollPane extends JScrollPane {
    /**
     * Creates a <code>JScrollPane</code> that displays the
     * contents of the specified
     * component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     *
     * @param view the component to display in the scrollpane's viewport
     * @see #setViewportView
     */
    CustomScrollPane(Component view) {
        super(view);
        this.custom();
    }

    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * with specified
     * scrollbar policies. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @param vsbPolicy an integer that specifies the vertical
     *                  scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     * @see #setViewportView
     */
    CustomScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        this.custom();
    }

    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * where both horizontal and vertical scrollbars appear when needed.
     */
    CustomScrollPane() {
        this.custom();
    }


    /**
     * Creates a <code>JScrollPane</code> that displays the view
     * component in a viewport
     * whose view position can be controlled with a pair of scrollbars.
     * The scrollbar policies specify when the scrollbars are displayed,
     * For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code>
     * then the vertical scrollbar only appears if the view doesn't fit
     * vertically. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @param view      the component to display in the scrollpanes viewport
     * @param hsbPolicy an integer that specifies the horizontal
     * @see #setViewportView
     */
    CustomScrollPane(Component view, int hsbPolicy) {
        super(view, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, hsbPolicy);
        this.custom();
    }

    private void custom() {
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setMaximumSize(new Dimension(UISize.VERTICAL_SCROLLBAR_WIDTH, Integer.MAX_VALUE));
        this.getVerticalScrollBar().setPreferredSize(new Dimension(UISize.VERTICAL_SCROLLBAR_WIDTH, Integer.MAX_VALUE));
        this.getHorizontalScrollBar().setMaximumSize(new Dimension(Short.MAX_VALUE, UISize.HORIZONTAL_SCROLLBAR_HEIGH));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(Short.MAX_VALUE, UISize.HORIZONTAL_SCROLLBAR_HEIGH));
    }
}