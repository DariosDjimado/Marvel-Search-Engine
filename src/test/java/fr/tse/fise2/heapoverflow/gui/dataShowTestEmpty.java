package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import javax.swing.*;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;

public class dataShowTestEmpty {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Empty windows");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        DataShow dataShow = new DataShow(panel);
        frame.setVisible(true);
        dataShow.DrawEmpty();
    }
}
