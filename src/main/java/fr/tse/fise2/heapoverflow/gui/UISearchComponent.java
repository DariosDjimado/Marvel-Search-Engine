package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class UISearchComponent implements UIComponent {
    private JRadioButton charactersRadioButton;
    private JRadioButton comicsRadioButton;
    private JTextField searchTextField;


    public UISearchComponent() {
        this.charactersRadioButton = new JRadioButton();
        this.comicsRadioButton =  new JRadioButton();
        this.searchTextField = new JTextField();

        charactersRadioButton.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                this.comicsRadioButton.setSelected(false);
            }
        });

        this.comicsRadioButton.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                this.charactersRadioButton.setSelected(false);
            }
        });
    }

    public void setup(JPanel leftWrapperPanel) {
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


        JButton searchLabel = new JButton();
        searchLabel.setText("Search");
        searchLabel.setVerticalAlignment(0);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 0, 0);
        searchLabel.setVisible(true);
        searchPanel.add(searchLabel, gbc);



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


        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new CardLayout(0, 0));
        searchResultsPanel.setVisible(true);
        leftWrapperPanel.add(searchResultsPanel, BorderLayout.CENTER);

        leftWrapperPanel.setVisible(true);
    }

    public JTextField getSearchTextField() {
        return searchTextField;
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
}
