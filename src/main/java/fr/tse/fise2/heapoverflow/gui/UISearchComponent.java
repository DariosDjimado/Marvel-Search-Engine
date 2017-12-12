package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UISearchComponent {
    private final JPanel leftWrapperPanel;
    private Controller controller;
    private JRadioButton charactersRadioButton;
    private JRadioButton comicsRadioButton;
    private JTextField searchTextField;
    private JPanel searchResultsPanel;


    UISearchComponent(JPanel leftWrapperPanel) {
        this.charactersRadioButton = new CustomRadioButton();
        // select character by default
        this.charactersRadioButton.setSelected(true);
        this.comicsRadioButton = new CustomRadioButton();
        this.searchTextField = new JTextField();

        this.searchTextField.requestFocusInWindow();

        this.charactersRadioButton.setSelected(true);


        //
        this.leftWrapperPanel = leftWrapperPanel;
    }

    void setup() {
        JPanel SearchWrapperPanel = new JPanel();
        SearchWrapperPanel.setLayout(new BorderLayout(0, 0));
        SearchWrapperPanel.setAlignmentX(0.5f);
        SearchWrapperPanel.setOpaque(true);
        leftWrapperPanel.add(SearchWrapperPanel, BorderLayout.NORTH);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setMinimumSize(new Dimension(42, 100));
        searchPanel.setPreferredSize(new Dimension(64, 100));
        searchPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        SearchWrapperPanel.add(searchPanel, BorderLayout.NORTH);


        JButton searchButton = new JButton();


        searchButton.addActionListener((ActionEvent e) -> {
            this.controller.searchStartsWith(this.getSearchTextField().getText());
        });


        searchButton.setText("Search");
        searchButton.setVerticalAlignment(0);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 0, 0);
        searchButton.setVisible(true);
        searchPanel.add(searchButton, gbc);


        this.searchTextField.setHorizontalAlignment(10);
        this.searchTextField.setMargin(new Insets(5, 5, 5, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        searchPanel.add(this.searchTextField, gbc);


        comicsRadioButton.setMargin(new Insets(2, 2, 2, 2));
        comicsRadioButton.setText("Comics");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        searchPanel.add(comicsRadioButton, gbc);


        this.charactersRadioButton.setText("Characters");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        searchPanel.add(charactersRadioButton, gbc);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.comicsRadioButton);
        buttonGroup.add(this.charactersRadioButton);


        this.searchResultsPanel = new JPanel();
        this.searchResultsPanel.setLayout(new CardLayout(0, 0));
        this.searchResultsPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        leftWrapperPanel.add(this.searchResultsPanel, BorderLayout.CENTER);

        leftWrapperPanel.setVisible(true);
    }


    public JRadioButton getCharactersRadioButton() {
        return charactersRadioButton;
    }

    public JRadioButton getComicsRadioButton() {
        return comicsRadioButton;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public void setResultsComics(Comic[] comics) {
        this.searchResultsPanel.removeAll();
        JScrollPane scrollPane = new JScrollPane();


        DefaultListModel<Comic> listModel = new DefaultListModel<>();

        for (Comic c : comics) {
            listModel.addElement(c);
        }
        JList<Comic> jList = new JList<>(listModel);
        jList.setCellRenderer(new ComicsSearchListRenderer());
        scrollPane.setViewportView(jList);

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (this.controller != null) {
                    this.controller.showComic(jList.getSelectedValue());
                    this.controller.fetchComicsInSameSeries(jList.getSelectedValue());
                }

            }
        });


        this.searchResultsPanel.add(scrollPane);
        this.searchResultsPanel.setVisible(true);

    }


    public void setResultsCharacters(Character[] characters) {
        this.searchResultsPanel.removeAll();
        JScrollPane scrollPane = new JScrollPane();


        DefaultListModel<Character> listModel = new DefaultListModel<>();

        for (Character c : characters) {
            listModel.addElement(c);
        }
        JList<Character> jList = new JList<>(listModel);
        jList.setCellRenderer(new CharactersSerachListRenderer());
        scrollPane.setViewportView(jList);

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (this.controller != null) {
                    this.controller.showCharacter(jList.getSelectedValue());
                    this.controller.fetchCharactersInSameComic(jList.getSelectedValue());
                }
            }
        });


        this.searchResultsPanel.add(scrollPane);
        this.searchResultsPanel.setVisible(true);
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}

