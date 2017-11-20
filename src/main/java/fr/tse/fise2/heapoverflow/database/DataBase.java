package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static void saveComicNames(Comic[] comics) throws IOException {
        Path file = Paths.get("comic-name.txt");
        List<String> lines = new ArrayList<String>();
        for (Comic aComic : comics) {
            lines.add(aComic.getTitle() + "||" + aComic.getId());
        }
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    public static List<String> getComicNames() throws IOException {
        Path file = Paths.get("comic-name.txt");
        return Files.readAllLines(file);
    }
}