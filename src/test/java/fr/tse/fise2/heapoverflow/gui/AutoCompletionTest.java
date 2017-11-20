package fr.tse.fise2.heapoverflow.gui;
import fr.tse.fise2.heapoverflow.database.DataBase;
import fr.tse.fise2.heapoverflow.main.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AutoCompletionTest {
    public AutoCompletionTest() {
        final Controller controller = new Controller();


        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(300,400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTextField f = new JTextField(20);

        AutoCompletion autoCompletion = new AutoCompletion(controller,f, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {

                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();

                try {
                    for(String aComicName: DataBase.getComicNames()){
                        words.add(aComicName.toLowerCase());
                    }
                    words.sort(Comparator.naturalOrder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setDictionary(words);

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };

        JPanel p = new JPanel();

        p.add(f);

        frame.add(p);

        frame.pack();
        frame.setVisible(true);
    }
    //How this class works
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AutoCompletionTest();
            }
        });
    }

}