package fr.tse.fise2.heapoverflow.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;

public class WikipediaUrlsTableTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void findByLabel() {
        String tonyStark = "Tony Stark";
        String ironManUrl = "https://en.wikipedia.org/wiki/Iron_Man";
        assertEquals(WikipediaUrlsTable.findByLabel(tonyStark), ironManUrl);

        String captainAmerica = "Captain America";
        String captainAmericaUrl = "https://en.wikipedia.org/wiki/Captain_America";
        assertEquals(WikipediaUrlsTable.findByLabel(captainAmerica), captainAmericaUrl);

        String pip = "Pip";
        String pipTheTrollUrl = "https://en.wikipedia.org/wiki/Pip_the_Troll";
        assertEquals(WikipediaUrlsTable.findByLabel(pip), pipTheTrollUrl);

        String scottLang = "Ant-Man (Scott Lang)";
        String scottLangUrl = "https://en.wikipedia.org/wiki/Ant-Man_(Scott_Lang)";
        assertEquals(WikipediaUrlsTable.findByLabel(scottLang), scottLangUrl);

        String ericOGrady = "Ant-Man (Eric O'Grady)";
        String ericOGradyUrl = "https://en.wikipedia.org/wiki/Eric_O%27Grady";
        assertEquals(WikipediaUrlsTable.findByLabel(ericOGrady), ericOGradyUrl);
    }

    @Test
    public void findUrls() {
        List<WikipediaUrlRow> wikipediaUrlRows = WikipediaUrlsTable.findUrls();
        assertNotNull(wikipediaUrlRows);
        assertTrue(wikipediaUrlRows.size() >= 1357);
    }
}