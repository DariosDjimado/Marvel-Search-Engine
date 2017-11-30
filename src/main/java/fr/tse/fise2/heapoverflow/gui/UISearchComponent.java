package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.UIComponent;
import fr.tse.fise2.heapoverflow.events.SearchButtonListener;
import fr.tse.fise2.heapoverflow.events.SelectionChangedListener;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;

public class UISearchComponent implements UIComponent {
    private final JPanel leftWrapperPanel;
    private JRadioButton charactersRadioButton;
    private JRadioButton comicsRadioButton;
    private JTextField searchTextField;
    private SearchButtonListener searchButtonListener;
    private JPanel searchResultsPanel;
    private SelectionChangedListener selectionChangedListener;


    public UISearchComponent(JPanel leftWrapperPanel) {
        this.charactersRadioButton = new JRadioButton();
        // select character by default
        this.charactersRadioButton.setSelected(true);
        this.comicsRadioButton = new JRadioButton();
        this.searchTextField = new JTextField();

        charactersRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.comicsRadioButton.setSelected(false);
            }
        });

        this.comicsRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.charactersRadioButton.setSelected(false);
            }
        });

        //
        this.leftWrapperPanel = leftWrapperPanel;
    }

    public void setup() {
        JPanel SearchWrapperPanel = new JPanel();
        SearchWrapperPanel.setLayout(new BorderLayout(0, 0));
        SearchWrapperPanel.setAlignmentX(0.5f);
        SearchWrapperPanel.setVisible(true);
        leftWrapperPanel.add(SearchWrapperPanel, BorderLayout.NORTH);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setMinimumSize(new Dimension(42, 100));
        searchPanel.setPreferredSize(new Dimension(64, 100));
        searchPanel.setVisible(true);
        SearchWrapperPanel.add(searchPanel, BorderLayout.NORTH);

        SearchWrapperPanel.setVisible(true);


        JButton searchButton = new JButton();


        searchButton.addActionListener((ActionEvent e) -> {
            this.searchButtonListener.emitNewSearch(this.getSearchTextField().getText());
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


        this.searchResultsPanel = new JPanel();
        this.searchResultsPanel.setLayout(new CardLayout(0, 0));
        this.searchResultsPanel.setVisible(true);

        leftWrapperPanel.add(this.searchResultsPanel, BorderLayout.CENTER);

        leftWrapperPanel.setVisible(true);
    }

    @Override
    public void setSize() {

    }

    @Override
    public void build() {

    }

    @Override
    public void setVisible() {

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

    public SearchButtonListener getSearchButtonListener() {
        return searchButtonListener;
    }

    public void setSearchButtonListener(SearchButtonListener searchButtonListener) {
        this.searchButtonListener = searchButtonListener;
    }

    public void setSelectionChangedListener(SelectionChangedListener selectionChangedListener) {
        this.selectionChangedListener = selectionChangedListener;
    }

    public void setResultsComics(Comic[] comics) {
        this.searchResultsPanel.removeAll();
        JScrollPane jscrollPane = new JScrollPane();
        JList jList = new JList();

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                System.out.println(Arrays.toString(jList.getSelectedValue().toString().split("\\|")));
            }
        });

        jscrollPane.setViewportView(jList);


        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < comics.length; i++) {
            listModel.add(i, comics[i].getTitle() + "||" + comics[i].getId());
        }
        jList.setModel(listModel);

        this.searchResultsPanel.add(jscrollPane);
    }


    public void setResultsCharacters(Character[] characters) {
        this.searchResultsPanel.removeAll();
        JScrollPane scrollPane = new JScrollPane();
        JList jList = new JList();

        jList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
               // this.selectionChangedListener.emitSelectionChanged(jList.getSelectedValue().toString().split("\\|")[2]);
            }
        });

        scrollPane.setViewportView(jList);


        DefaultListModel listModel = new DefaultListModel();

        for (int i = 0; i < characters.length; i++) {
            listModel.add(i, characters[i].getName() + "||" + characters[i].getId());
        }
        jList.setModel(listModel);


        this.searchResultsPanel.add(scrollPane);
    }


}