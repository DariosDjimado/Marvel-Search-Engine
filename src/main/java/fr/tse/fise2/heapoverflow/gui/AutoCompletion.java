package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.CharacterRow;
import fr.tse.fise2.heapoverflow.database.ComicRow;
import fr.tse.fise2.heapoverflow.main.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoCompletion {
    private final Controller controller;

    private final JTextField textField;
    private final Window container;
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow;
    private String typedWord;
    private int tW, tH;

    public AutoCompletion(Controller controller, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {

        this.controller = controller;

        Window mainWindow = this.controller.getUi();


        this.textField = this.controller.getUi().getUiSearchComponent().getSearchTextField();
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                checkForAndShowSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                checkForAndShowSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                checkForAndShowSuggestions();
            }
        });


        typedWord = "";
        tW = 0;
        tH = 0;

        autoSuggestionPopUpWindow = new JWindow(mainWindow);
        autoSuggestionPopUpWindow.setOpacity(opacity);
        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setBackground(popUpBackground);

        addKeyBindingToRequestFocusInPopUpWindow();


        AutoCompletion that = this;
        this.container.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                that.autoSuggestionPopUpWindow.setVisible(false);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                that.autoSuggestionPopUpWindow.setVisible(false);
            }
        });

        this.controller.getUi().addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                autoSuggestionPopUpWindow.setVisible(false);
                super.mouseClicked(e);
            }
        });


    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    private ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        //used to calcualte size of JWindow as new Jlabels are added
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    private void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(this.controller, word, suggestionFocusedColor, suggestionsTextColor, this);
        calculatePopUpWindowSize(suggestionLabel);
        suggestionsPanel.add(suggestionLabel);
    }

    String getCurrentlyTypedWord() {//get newest word after last white space if any or the first word if no white spaces
        String wordBeingTyped = textField.getText();
        return wordBeingTyped.trim();
    }

    private void calculatePopUpWindowSize(JLabel label) {
        //so we can size the JWindow correctly
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() {
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX;
        int windowY;

        windowX = container.getX() + textField.getX();
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
        } else {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
        }

        autoSuggestionPopUpWindow.setLocation(windowX + this.textField.getMargin().left, 50 + windowY + this.textField.getHeight() - this.textField.getMargin().bottom);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(350, 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }


    JTextField getTextField() {
        return textField;
    }


    private boolean wordTyped(String typedWord) {

        if (typedWord.isEmpty()) {
            return false;
        }

        boolean suggestionAdded = false;


        try {
            if (this.controller.getUi().getUiSearchComponent().getComicsRadioButton().isSelected()) {
                for (ComicRow a : this.controller.getMarvelElementTable().findComicsLike(typedWord, 0, 20)) {
                    addWordToSuggestions(a.getTitle());
                    suggestionAdded = true;
                }
            }

            if (this.controller.getUi().getUiSearchComponent().getCharactersRadioButton().isSelected()) {
                for (CharacterRow a : this.controller.getMarvelElementTable().findCharactersLike(typedWord, 0, 20)) {
                    addWordToSuggestions(a.getName());
                    suggestionAdded = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestionAdded;
    }
}

class SuggestionLabel extends JLabel {
    private final Controller controller;

    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final AutoCompletion autoCompletion;
    private boolean focused = false;
    private Color suggestionsTextColor, suggestionBorderColor;

    SuggestionLabel(final Controller controller, String string, final Color borderColor, Color suggestionsTextColor, AutoCompletion autoCompletion) {
        super(string);

        this.controller = controller;

        this.suggestionsTextColor = suggestionsTextColor;
        this.autoCompletion = autoCompletion;
        this.textField = autoCompletion.getTextField();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoCompletion.getAutoSuggestionPopUpWindow();

        initComponent();
    }

    private void initComponent() {
        setFocusable(true);
        setForeground(suggestionsTextColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }

    boolean isFocused() {
        return focused;
    }

    void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    private void replaceWithSuggestedText() {
        String suggestedWord = getText();
        String text = textField.getText();
        String typedWord = autoCompletion.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp + " ");


        if (this.controller.getUi().getUiSearchComponent().getComicsRadioButton().isSelected()) {
            this.controller.emitSearchComicById(tmp);
        }

        if (this.controller.getUi().getUiSearchComponent().getCharactersRadioButton().isSelected()) {
            this.controller.emitSearchCharacterById(tmp);
        }


    }
}