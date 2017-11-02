package display;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class dataShowTest {
    public static void main(String[] args) {
        MarvelRequest request = new MarvelRequest();
//        Path myFile = Paths.get("comicSample.json");
        try {
            String response = request.getData("comics/64785");
//            Files.write(myFile, Arrays.asList(response));
//            String response = Files.readAllLines(myFile).get(0);
            Comic fetched = deserializeComics(response).getData().getResults()[0];
            DataShow testWindow = new DataShow(fetched);
        }
        catch(Exception e)  {
            System.out.println(e);
        }

    }
}
