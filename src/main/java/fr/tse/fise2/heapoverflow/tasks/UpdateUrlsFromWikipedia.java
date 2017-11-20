package fr.tse.fise2.heapoverflow.tasks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.io.IOException;


/**
 * @author Darios DJIMADO
 */
public class UpdateUrlsFromWikipedia {
    static int count = 0;

    public static void main(String[] args) {


        UpdateUrlsFromWikipedia updateUrlsFromWikipedia = new UpdateUrlsFromWikipedia();
        try {
            String url = updateUrlsFromWikipedia.getEnUrls("https://en.wikipedia.org/wiki/Category:Marvel_Comics_superheroes");
            while (url != null) {
                url = updateUrlsFromWikipedia.getEnUrls(url);
            }

            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getEnUrls(String url) throws IOException {
        String nextPage = null;

        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();


        // handle next page href attributes
        if (url.startsWith("/w/")) {
            url = "http://en.wikipedia.org" + url;
        }
        // get the document
        Document doc = Jsoup.connect(url).get();
        Element mainPage = doc.getElementById("mw-pages");
        Elements uls = mainPage.getElementsByTag("ul");
        for (Element ul : uls) {
            for (Element li : ul.getElementsByTag("li")) {
                count++;

                try {
                    ItemDocument itemDocument = (ItemDocument) wbdf.getEntityDocumentByTitle("enwiki", li.text());

                    System.out.println(li.text() + "    --->     https://en.wikipedia.org" +
                            li.select("a").attr("href"));
                    if(itemDocument!=null){
                        System.out.println("-->  " + itemDocument.getLabels().get("en") + "-->  " + itemDocument.getAliases().get("en"));
                    } else{
                        System.out.println(li.text());
                    }

                } catch (MediaWikiApiErrorException e) {
                    e.printStackTrace();
                }


            }
        }

        Element isTheLastPage = mainPage.getElementsByTag("a").last();

        if (isTheLastPage.text().equalsIgnoreCase("next page")) {
            nextPage = isTheLastPage.attr("href");
        } else {
            nextPage = null;
        }
        return nextPage;
    }
}
