package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;

public class LoadingInterface{
    public static void printLoadingInterface(String[] args)throws Exception {
        JFrame frame = new JFrame("Test");

        ImageIcon loading = new ImageIcon("C:/Users/Rajaona/Documents/GitHub/2017PInfo83-HeapOverflow/img/ajax-loader.gif", "icon");
        frame.add(new JLabel("loading... ", loading, JLabel.CENTER));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 150);
        frame.setVisible(true);

    }
}