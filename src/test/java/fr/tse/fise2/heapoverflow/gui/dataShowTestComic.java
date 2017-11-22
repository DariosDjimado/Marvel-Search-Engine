package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class dataShowTestComic {
    public static void main(String[] args) {
        MarvelRequest request = new MarvelRequest();
//        Path myFile = Paths.get("comicSample.json");
        try {
            String response = request.getData("comics/39595");
//            Files.write(myFile, Arrays.asList(response));
//            String response = Files.readAllLines(myFile).get(0);
            Comic fetched = deserializeComics(response).getData().getResults()[0];
            JFrame frame = new JFrame();
            frame.setTitle("Comic 39595 - " + fetched.getTitle());
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            DataShow.DrawComic(panel, fetched);
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch(Exception e)  {
            System.out.println(e);
        }

    }
}
