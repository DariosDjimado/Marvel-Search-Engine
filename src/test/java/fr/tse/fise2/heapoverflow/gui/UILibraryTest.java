package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;

import static org.junit.Assert.*;

public class UILibraryTest {
    public static void main(String[] args) {
        JFrame testWin = new JFrame();
        testWin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        testWin.setSize(1000, 1000);
        JPanel pane = new JPanel();
        testWin.setContentPane(pane);
        UILibrary lib = new UILibrary(pane);
        testWin.setVisible(true);
    }
}