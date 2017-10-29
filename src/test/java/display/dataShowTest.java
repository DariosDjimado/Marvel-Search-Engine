package display;

import fr.tse.fise2.heapoverflow.display.DataShow;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class dataShowTest {
    public static void main(String[] args) {
        MarvelRequest request = new MarvelRequest();
        try {
            String response = request.getData("comics");
            Comic fetched = deserializeComics(response).getData().getResults()[0];
            DataShow testWindow = new DataShow(fetched);
        }
        catch(Exception e)  {
            System.out.println(e);
        }
    }
}
