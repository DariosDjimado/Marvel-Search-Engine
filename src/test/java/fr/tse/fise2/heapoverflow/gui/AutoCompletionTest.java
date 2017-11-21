package fr.tse.fise2.heapoverflow.gui;
import fr.tse.fise2.heapoverflow.database.DataBase;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.main.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;
import static org.junit.Assert.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

public class AutoCompletionTest {
    public AutoCompletionTest() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(300,400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel();

        frame.getContentPane().add(p);
        
                JTextField f = new JTextField(20);
                
                        AutoCompletion autoCompletion = new AutoCompletion(f, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
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
                        
                                p.add(f);
                                
                                JButton btnSearchCharacters = new JButton("Search Characters");
                                btnSearchCharacters.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e2) {
                                        MarvelRequest request = new MarvelRequest();

                                      try {
                                          String response = request.getData("characters?titleStartsWith="+TextField.getText());

                                          Character fetched = deserializeCharacters(response).getData().getResults()[0];
                                          DataShow testWindow = new DataShow(fetched);
                                      } catch (Exception e3) {
                                          System.out.println(e3);
                                      }
                                		
                                	}
                                });
                                p.add(btnSearchCharacters);
                                
                                JButton btnSearchComics = new JButton("Search Comics");
                                btnSearchComics.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e) {
                                        MarvelRequest request = new MarvelRequest();
                                      try {
                                          String response = request.getData("comics?titleStartsWith="+TextField.getText());

                                          Comic fetched = deserializeComics(response).getData().getResults()[0];
                                          DataShow testWindow = new DataShow(fetched);
                                      }
                                      catch(Exception e1)  {
                                          System.out.println(e1);
                                      }
                                	}
                                });
                                p.add(btnSearchComics);
        
        

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