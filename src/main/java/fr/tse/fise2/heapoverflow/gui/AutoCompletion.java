package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.CharacterRow;
import fr.tse.fise2.heapoverflow.database.ComicRow;
import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.main.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * Auto suggests title to user
 *
 * @author Darios DJIMADO
 * @author Lionel RAJAONA
 */
public class AutoCompletion {
    private final static String DOWN_RELEASED = "Down released";
    private final JFrame frame;

    private final JWindow popUpWindow;
    private final JList<ItemSuggested> suggestedList;
    private final JTextField searchTextField;
    private final DefaultListModel<ItemSuggested> listModel;
    private final Controller controller;

    public AutoCompletion(JFrame frame, JTextField searchTextField, Controller controller) {
        this.controller = controller;
        this.frame = frame;
        this.searchTextField = searchTextField;
        this.popUpWindow = new JWindow(this.frame);
        this.listModel = new DefaultListModel<>();
        this.suggestedList = new JList<>();
        this.suggestedList.setModel(listModel);
    }

    public void initComponent() {
        // pop window
        this.popUpWindow.setMinimumSize(new Dimension(350, 50));
        JPanel panel = (JPanel) this.popUpWindow.getContentPane();
        panel.add(new CustomScrollPane(this.suggestedList));
        panel.setBorder(BorderFactory.createLineBorder(UIColor.PRIMARY_COLOR));
        this.configureSuggestedListActionListener();
        this.configureSearchTextFieldActionListener();
        this.configureFrameActionListener();
        this.showSuggestionsWindow();
    }

    private void configureSuggestedListActionListener() {
        this.suggestedList.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been released.
             *
             * @param e event
             */
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ItemSuggested itemSuggested = suggestedList.getSelectedValue();

                    if (itemSuggested != null) {
                        searchTextField.setText(itemSuggested.getName());
                        search(itemSuggested.getId());
                    }
                    popUpWindow.setVisible(false);
                }
            }
        });

        this.suggestedList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                ItemSuggested itemSuggested = this.suggestedList.getSelectedValue();

                if (itemSuggested != null) {
                    this.searchTextField.setText(itemSuggested.getName());
                    this.search(itemSuggested.getId());
                }
                this.popUpWindow.setVisible(false);
            }
        });
    }

    private void showSuggestionsWindow() {
        if (!this.searchTextField.getText().isEmpty() && !this.listModel.isEmpty() && this.searchTextField.hasFocus()) {
            this.popUpWindow.setVisible(true);
            // calculate the positions of pop up window
            int posX;
            int posY;
            Point convertedPoint = SwingUtilities.convertPoint(this.searchTextField, this.searchTextField.getLocation(), this.frame);
            posX = this.frame.getX() + convertedPoint.x - this.searchTextField.getX();
            posY = this.frame.getY() + this.searchTextField.getHeight() + convertedPoint.y - this.searchTextField.getMargin().top - this.searchTextField.getInsets().top;
            this.popUpWindow.setLocation(posX, posY);

            // repaint
            this.popUpWindow.pack();
            this.popUpWindow.revalidate();
            this.popUpWindow.repaint();
        } else {
            this.popUpWindow.setVisible(false);
        }

    }

    private void configureSearchTextFieldActionListener() {
        this.searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getSuggested();
                showSuggestionsWindow();
                ensureSearchTextFieldFocused();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getSuggested();
                showSuggestionsWindow();
                ensureSearchTextFieldFocused();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getSuggested();
                showSuggestionsWindow();
                ensureSearchTextFieldFocused();
            }
        });

        this.searchTextField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), DOWN_RELEASED);

        this.searchTextField.getActionMap().put(DOWN_RELEASED, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listModel.isEmpty()) {
                    popUpWindow.toFront();
                    popUpWindow.requestFocusInWindow();
                    suggestedList.requestFocusInWindow();
                }
            }
        });
    }

    private void configureFrameActionListener() {
        this.frame.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e event
             */
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                popUpWindow.setVisible(false);
            }

            /**
             * Invoked when the component's position changes.
             *
             * @param e event
             */
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                popUpWindow.setVisible(false);
            }
        });

        this.frame.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                popUpWindow.setVisible(false);
            }
        });
    }

    private void ensureSearchTextFieldFocused() {
        this.frame.toFront();
        this.frame.requestFocusInWindow();
        this.searchTextField.requestFocusInWindow();
    }

    private void getSuggested() {
        if (this.searchTextField.hasFocus() && !this.searchTextField.getText().isEmpty()) {
            try {
                this.listModel.clear();
                if (this.controller.getUi().getUiSearchComponent().getComicsRadioButton().isSelected()) {
                    for (ComicRow comicRow : MarvelElementTable.findComicsLike(this.searchTextField.getText(), 0, 50)) {
                        ItemSuggested itemSuggested = new ItemSuggested(comicRow.getTitle(), comicRow.getId());
                        this.listModel.addElement(itemSuggested);
                    }
                }

                if (this.controller.getUi().getUiSearchComponent().getCharactersRadioButton().isSelected()) {
                    for (CharacterRow characterRow : MarvelElementTable.findCharactersLike(this.searchTextField.getText(), 0, 50)) {
                        ItemSuggested itemSuggested = new ItemSuggested(characterRow.getName(), characterRow.getId());
                        this.listModel.addElement(itemSuggested);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void search(int id) {
        if (this.controller.getUi().getUiSearchComponent().getComicsRadioButton().isSelected()) {
            this.controller.emitSearchComicById(id);
        }

        if (this.controller.getUi().getUiSearchComponent().getCharactersRadioButton().isSelected()) {
            this.controller.emitSearchCharacterById(id);
        }
    }

    public JWindow getPopUpWindow() {
        return popUpWindow;
    }

    private class ItemSuggested {
        String name;
        int id;

        ItemSuggested(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
