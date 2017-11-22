package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import javax.swing.*;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;

public class dataShowTestCharacter {
    public static void main(String[] args) {
        MarvelRequest request = new MarvelRequest();
//        Path myFile = Paths.get("comicSample.json");
        try {
            String response = request.getData("characters/1009367");
//            Files.write(myFile, Arrays.asList(response));
//            String response = Files.readAllLines(myFile).get(0);
            Character fetched = deserializeCharacters(response).getData().getResults()[0];
            JFrame frame = new JFrame();
            frame.setTitle("Comic 39595 - " + fetched.getName());
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            DataShow.DrawCharacter(panel, fetched);
            frame.setContentPane(panel);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
