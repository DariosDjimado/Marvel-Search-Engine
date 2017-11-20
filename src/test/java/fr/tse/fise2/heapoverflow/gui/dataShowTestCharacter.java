package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

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
            DataShow testWindow = new DataShow(fetched);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
