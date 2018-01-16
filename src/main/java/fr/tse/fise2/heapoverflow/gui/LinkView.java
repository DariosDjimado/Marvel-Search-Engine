package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Link view
 *
 * @author Darios DJIMADO
 */
class LinkView extends JButton {
    private static final ImageIcon URL_OUT_IMAGE_ICON = new ImageIcon(ComicsSearchListRenderer.class.getResource("url_out.png"));

    private String url;

    /**
     * Creates a button with no set text or icon.
     */
    LinkView(String text, String url) {
        super("<HTML><FONT color=\"#000099\"><U>" + text + "</U></FONT></HTML>");

        this.setIcon(URL_OUT_IMAGE_ICON);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setOpaque(false);
        this.setBorderPainted(false);
        this.setToolTipText(this.url);
        this.setContentAreaFilled(false);
        this.addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(this.url));
                } catch (IOException | URISyntaxException e1) {
                    AppErrorHandler.onError(e1);
                }
            }
        });

        this.url = url;
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
