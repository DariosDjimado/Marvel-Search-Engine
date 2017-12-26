package fr.tse.fise2.heapoverflow.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observer;

/**
 * @author Darios DJIMADO
 */
public class ReadButtonView extends ReadButton implements Observer {

    ReadButtonView() {
        this.initConfig();
    }

    private void initConfig() {
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                updateIcon(true);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                updateIcon(false);
            }
        });
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
    public void update(java.util.Observable o, Object arg) {
        this.refresh();
    }
}
