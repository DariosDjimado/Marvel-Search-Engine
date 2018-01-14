package fr.tse.fise2.heapoverflow.tasks;


import fr.tse.fise2.heapoverflow.database.FirstAppearanceTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Rajaona
 * This class procides Marvel characters first appearance : which comic at which date.
 * It is integrated in SetupMVC class, as all pieces of data are saved in a csv file.
 */
public class FirstAppearance {
    public static void getFirstAppearance() throws IOException, SQLException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_Marvel_Comics_superhero_debuts").get();
        Elements tables = doc.select("table");
        for (int i = 1; i < tables.size() - 1; i++) {
            Element tbody = tables.get(i);
            for (Element tr : tbody.getElementsByTag("tr")) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() < 4 || tds.size() > 5) {
                    continue;
                }
                String character = tds.get(0).text().toLowerCase();
                String date = tds.get(1).text();
                String comic = tds.get(3).text();
                FirstAppearanceTable.insert(character, date, comic);
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        FirstAppearance.getFirstAppearance();
    }
}
