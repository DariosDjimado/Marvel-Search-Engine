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
    private final ConnectionDB connectionDB;


    public MarvelElementTable(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    public void insertComic(int id, String title) throws SQLException {
        insertElement(id, title, MarvelElements.COMIC.getValue());
    }

    public void insertCharacter(int id, String name) throws SQLException {
        insertElement(id, name, MarvelElements.CHARACTER.getValue());
    }

    public CharacterRow findCharacterByName(String characterName) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME = ? AND TYPE=" +
                        MarvelElements.CHARACTER.getValue() +
                        " OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");
        preparedStatement.setString(1, characterName);

        return getCharacterRow(preparedStatement);
    }

    public List<CharacterRow> findCharactersLike(String pattern, int offset, int next) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
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

    public CharacterRow findCharacterById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE ID = ? AND TYPE=" +
                        MarvelElements.CHARACTER.getValue());
        preparedStatement.setInt(1, id);

        return getCharacterRow(preparedStatement);
    }

    public List<CharacterRow> findCharacters() throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE TYPE=" + MarvelElements.CHARACTER.getValue());

        return getCharacterRows(preparedStatement);
    }

    public List<ComicRow> findComics() throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE TYPE=" + MarvelElements.COMIC.getValue());

        return getComicRows(preparedStatement);
    }

    /**
     * @param id id of comic
     * @return ComicRow
     * @throws SQLException
     */
    public ComicRow findComicById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE ID = ? AND TYPE=" + MarvelElements.COMIC.getValue());
        preparedStatement.setInt(1, id);

        return getComicRow(preparedStatement);
    }

    public ComicRow findComicByTitle(String comicTitle) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS WHERE NAME = ? AND TYPE=" +
                        MarvelElements.COMIC.getValue() +
                        " OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");

        preparedStatement.setString(1, comicTitle);

        return getComicRow(preparedStatement);

    }

    public List<ComicRow> findComicsLike(String pattern, int offset, int next) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
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

    public boolean comicExists(int id) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT COUNT(*) FROM ELEMENTS WHERE ID = ? AND TYPE = ?");

        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, MarvelElements.COMIC.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(1) > 0) {
                found = true;
                System.out.println(id);
            }
        }
        return found;
    }


    private void insertElement(int id, String title, int type) throws SQLException {
        PreparedStatement statement = this.connectionDB
                .getConnection()
                .prepareStatement("INSERT INTO ELEMENTS(ID,NAME,TYPE)" +
                        " VALUES (?,?,?)");
        statement.setInt(1, id);
        statement.setString(2, title);
        statement.setInt(3, type);

        statement.execute();
    }

    @Nullable
    private ComicRow getComicRow(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        ComicRow comicRow = null;
        while (resultSet.next()) {
            comicRow = new ComicRow(resultSet.getInt("id"),
                    resultSet.getString("name"));
        }

        return comicRow;
    }

    @NotNull
    private List<ComicRow> getComicRows(PreparedStatement preparedStatement) throws SQLException {
        List<ComicRow> comicRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            comicRowList.add(new ComicRow(resultSet.getInt("id"),
                    resultSet.getString("name")));
        }
        return comicRowList;
    }

    @Nullable
    private CharacterRow getCharacterRow(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        CharacterRow characterRow = null;
        while (resultSet.next()) {
            characterRow = new CharacterRow(resultSet.getInt("id"),
                    resultSet.getString("name"));
        }

        return characterRow;
    }

    @NotNull
    private List<CharacterRow> getCharacterRows(PreparedStatement preparedStatement) throws SQLException {
        List<CharacterRow> characterRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            characterRowList.add(new CharacterRow(resultSet.getInt("id"),
                    resultSet.getString("name")));
        }
        return characterRowList;
    }


}
