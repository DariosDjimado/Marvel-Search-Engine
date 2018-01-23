package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.WikipediaUrlsTable;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;

import java.io.IOException;


/**
 * @author Darios DJIMADO
 */
public final class UpdateUrlsFromWikipedia {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUrlsFromWikipedia.class);
    private final static String[] urls = {
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_superheroes",
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_aliens",
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_superhero_teams",
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_characters_who_can_move_at_superhuman_speeds",
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_Asgardians",
            "https://en.wikipedia.org/wiki/Category:Fictional_organizations_in_Marvel_Comics",
            "https://en.wikipedia.org/wiki/Category:Marvel_Comics_martial_artists"
    };
    private final SetupView view;


    public UpdateUrlsFromWikipedia(@NotNull SetupView view) {
        this.view = view;
    }

    public void init() {
        for (String url : urls) {
            String tempUrl = url;
            do {
                try {
                    tempUrl = getEnUrls(tempUrl);
                } catch (Exception e) {
                    AppErrorHandler.onError(e);
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            } while (tempUrl != null);
        }
    }

    private String getEnUrls(String url) {
        String nextPage;
        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();

        // handle next page href attributes
        if (url.startsWith("/w/")) {
            url = "http://en.wikipedia.org" + url;
        }
        // get the document
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        if (doc != null) {
            Element mainPage = doc.getElementById("mw-pages");
            Elements uls = mainPage.getElementsByTag("ul");
            for (Element ul : uls) {
                for (Element li : ul.getElementsByTag("li")) {
                    String characterName;
                    String characterLabel;
                    String characterUrl;
                    String characterAlias;
                    String characterDescription;

                    ItemDocument itemDocument;
                    try {
                        itemDocument = (ItemDocument) wbdf.getEntityDocumentByTitle("enwiki", li.text());

                        characterName = li.text();
                        characterUrl = "https://en.wikipedia.org" + li.select("a").attr("href");
                        if (itemDocument != null) {
                            characterLabel = itemDocument.getLabels().get("en") != null ? itemDocument.getLabels().get("en").getText() : "";
                            characterAlias = itemDocument.getAliases().get("en") != null ? itemDocument.getAliases().get("en").get(0).getText() : "";
                            characterDescription = itemDocument.getDescriptions().get("en") != null ? itemDocument.getDescriptions().get("en").getText() : "";
                            WikipediaUrlsTable.insert(characterName, characterLabel, characterUrl, characterAlias, characterDescription);
                            this.view.onLog("insert url -> " + characterUrl);
                        }
                    } catch (DerbySQLIntegrityConstraintViolationException scie) {
                        // do nothing since we know that we'll have some duplicated elements
                    } catch (Exception e) {
                        AppErrorHandler.onError(e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            }
            // handle the last element
            Element isTheLastPage = mainPage.getElementsByTag("a").last();
            if (isTheLastPage.text().equalsIgnoreCase("next page")) {
                nextPage = isTheLastPage.attr("href");
            } else {
                nextPage = null;
            }
            return nextPage;
        }
        return null;
    }
}
