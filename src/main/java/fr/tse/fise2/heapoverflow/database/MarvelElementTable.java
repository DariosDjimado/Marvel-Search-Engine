package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarvelElementTable {

    public static int insertComic(int id, String title) throws SQLException {
        return insertElement(id, title, MarvelElements.COMIC.getValue());
    }

    public static int insertCharacter(int id, String name) throws SQLException {
        return insertElement(id, name, MarvelElements.CHARACTER.getValue());
    }

    public static CharacterRow findCharacterByName(String characterName) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME = ? AND TYPE=" +
                        MarvelElements.CHARACTER.getValue() +
                        " OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");
        preparedStatement.setString(1, characterName);

        return getCharacterRow(preparedStatement);
    }

    public static List<CharacterRow> findCharactersLike(String pattern, int offset, int next) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME LIKE ? AND TYPE="
                        + MarvelElements.CHARACTER.getValue() +
                        " ORDER BY NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        pattern = pattern
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");

        preparedStatement.setString(1, "%" + pattern + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, next);

        return getCharacterRows(preparedStatement);

    }

    public static CharacterRow findCharacterById(int id) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE ID = ? AND TYPE=" +
                        MarvelElements.CHARACTER.getValue());
        preparedStatement.setInt(1, id);

        return getCharacterRow(preparedStatement);
    }

    public static List<CharacterRow> findCharacters() throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE TYPE=" + MarvelElements.CHARACTER.getValue());

        return getCharacterRows(preparedStatement);
    }

    public static List<ComicRow> findComics() throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE TYPE=" + MarvelElements.COMIC.getValue());

        return getComicRows(preparedStatement);
    }

    /**
     * @param id id of comic
     * @return ComicRow
     * @throws SQLException
     */
    public static ComicRow findComicById(int id) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE ID = ? AND TYPE=" + MarvelElements.COMIC.getValue());
        preparedStatement.setInt(1, id);

        return getComicRow(preparedStatement);
    }

    public static ComicRow findComicByTitle(String comicTitle) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME = ? AND TYPE=" +
                        MarvelElements.COMIC.getValue() +
                        " OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");

        preparedStatement.setString(1, comicTitle);

        return getComicRow(preparedStatement);

    }

    public static List<ComicRow> findComicsLike(String pattern, int offset, int next) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME LIKE ? AND TYPE=" +
                        MarvelElements.COMIC.getValue() +
                        " ORDER BY NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
        pattern = pattern
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        preparedStatement.setString(1, "%" + pattern + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, next);

        return getComicRows(preparedStatement);
    }

    public static int elementsExists(int id, MarvelElements marvelElements) throws SQLException {
        int elementId = -1;
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE ID = ? AND TYPE = ?");

        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, marvelElements.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            elementId = resultSet.getInt("uid");
        }
        return elementId;
    }

    private static int insertElement(int id, String title, int type) throws SQLException {
        PreparedStatement prepareStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("INSERT INTO ELEMENTS(ID,NAME,TYPE)" +
                        " VALUES (?,?,?)", new String[]{"UID"});
        prepareStatement.setInt(1, id);
        prepareStatement.setString(2, title);
        prepareStatement.setInt(3, type);

        prepareStatement.execute();

        ResultSet rs = prepareStatement.getGeneratedKeys();
        int generatedID = -1;
        while (rs.next()) {
            generatedID = rs.getInt(1);
        }
        return generatedID;
    }

    @Nullable
    private static ComicRow getComicRow(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        ComicRow comicRow = null;
        while (resultSet.next()) {
            comicRow = new ComicRow(resultSet.getInt("id"),
                    resultSet.getString("name"));
        }

        return comicRow;
    }

    @NotNull
    private static List<ComicRow> getComicRows(PreparedStatement preparedStatement) throws SQLException {
        List<ComicRow> comicRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            comicRowList.add(new ComicRow(resultSet.getInt("id"),
                    resultSet.getString("name")));
        }
        return comicRowList;
    }

    @Nullable
    private static CharacterRow getCharacterRow(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        CharacterRow characterRow = null;
        while (resultSet.next()) {
            characterRow = new CharacterRow(resultSet.getInt("id"),
                    resultSet.getString("name"));
        }

        return characterRow;
    }

    @NotNull
    private static List<CharacterRow> getCharacterRows(PreparedStatement preparedStatement) throws SQLException {
        List<CharacterRow> characterRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            characterRowList.add(new CharacterRow(resultSet.getInt("id"),
                    resultSet.getString("name")));
        }
        return characterRowList;
    }


}
