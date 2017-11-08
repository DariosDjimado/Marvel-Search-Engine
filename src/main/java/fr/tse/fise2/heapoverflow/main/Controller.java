package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.DataShow;
import fr.tse.fise2.heapoverflow.gui.SearchHandler;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class Controller {
    private static DataShow dataShow;


    public static void emitEvent(String str) {


        if (dataShow != null) {

            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                System.out.println(fetched);

                dataShow.onComicAvailable(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MarvelRequest request = new MarvelRequest();
            try {
                String response = request.getData("comics/" + SearchHandler.getCurrentSearch());
                Comic fetched = deserializeComics(response).getData().getResults()[0];
                System.out.println(fetched);
                dataShow = new DataShow(fetched);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


