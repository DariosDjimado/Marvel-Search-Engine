package fr.tse.fise2.heapoverflow.gui;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;


import fr.tse.fise2.heapoverflow.marvelapi.*;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * Class to search comics & characters via search bar
 * @author ismail  
 */


	public class Recherche extends JFrame {


		private JLabel head = new JLabel();
		private JFrame frame;
		private JPanel panel_2 = new JPanel();
		private JPanel panel_1 = new JPanel();
		private JButton Rechercher = new JButton("Rechercher");
		private JPanel panel = new JPanel();
		private  JTextField textField = new JTextField();
		private JLabel label_image = new JLabel("                                 Image");
		protected static final int BLACK_WIDOW_ID = 1009189;
		private JRadioButton rdbtnNewRadioButton = new JRadioButton("Recherche par titre ");

		private JLabel label_published = new JLabel("New label");
		private JLabel title_label = new JLabel("New label");
		private final JRadioButton rdbtnRechercheParPersonnage = new JRadioButton("Recherche par personnage");
		private JScrollPane scrollpane_creators = new JScrollPane();
		private final JLabel label_description = new JLabel("New label");
		/**
		 * Launch the application.
		 */
		
		
		public static void main(String[] args) {
			MarvelRequest request = new MarvelRequest();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					
					try {
						
						Recherche window = new Recherche();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the application.
		 */
		public Recherche() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			

			
			textField.setBounds(310, 161, 183, 34);
			textField.setColumns(10);
			frame = new JFrame();
			frame.setBounds(100, 100, 880, 640);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			
			panel.setBounds(0, 0, 862, 593);
			frame.getContentPane().add(panel);
			panel.setLayout(new CardLayout(0, 0));
				
			panel.add(panel_1, "name_332991625597835");
			panel_1.setLayout(null);
			panel_2.setBackground(Color.GRAY);
					
			panel.add(panel_2, "name_333010933330172");
			panel_2.setLayout(null);
			label_image.setForeground(Color.RED);
			
			
			label_image.setBounds(12, 100, 228, 314);
			panel_2.add(label_image);
			
			title_label.setBounds(28, 13, 497, 84);
			panel_2.add(title_label);
			
			label_published.setBounds(252, 110, 241, 43);
			panel_2.add(label_published);
			
			JLabel label_rating = new JLabel("New label");
			label_rating.setBounds(252, 170, 241, 43);
			panel_2.add(label_rating);
			
			
			scrollpane_creators.setBounds(252, 284, 256, 101);
			panel_2.add(scrollpane_creators);
			label_description.setBounds(252, 228, 256, 43);
			
			panel_2.add(label_description);
			
			Rechercher.setBounds(518, 161, 144, 30);
			panel_1.add(Rechercher);
			
			panel_1.add(textField);
			
			
			rdbtnNewRadioButton.setBounds(310, 222, 194, 25);
			panel_1.add(rdbtnNewRadioButton);
			
			rdbtnRechercheParPersonnage.setBounds(310, 277, 208, 25);
			panel_1.add(rdbtnRechercheParPersonnage);
			
			JLabel labelmarvelwelcome = new JLabel("Marvel Home Page!");
			labelmarvelwelcome.setFont(new Font("Segoe UI Semilight", Font.BOLD, 20));
			labelmarvelwelcome.setBounds(47, 13, 201, 48);
			panel_1.add(labelmarvelwelcome);
			rdbtnRechercheParPersonnage.setBounds(310, 279, 194, 25);
			
			panel_1.add(rdbtnRechercheParPersonnage);
			
			Rechercher.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					panel.remove(panel_1);		
					
					System.out.println(textField.getText());
					
					MarvelRequest request = new MarvelRequest();

					String responseToQuery = null;
					try {
						responseToQuery = request.getData("comics?titleStartsWith="+textField.getText());
						
					} catch (NoSuchAlgorithmException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

		            Comic fetched = deserializeComics(responseToQuery).getData().getResults()[2];
		            DataShow testWindow = new DataShow(fetched);
		            testWindow.getContentPane().setLayout(null);

					panel_2.add(head, BorderLayout.NORTH);
					//panel_2.add(label_image);
					
					
					panel.add(panel_2);
					    
					
				}
				
				
			});
		
			

	        
	        
	  
			
		
		}
	}

