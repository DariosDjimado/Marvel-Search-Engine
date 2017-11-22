package fr.tse.fise2.heapoverflow.database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComicsTable {

    private final ConnectionDB connectionDB;

    public ComicsTable(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }


    public boolean insertIntoComics(int id, String title)
            throws SQLException {
        int execute;

        PreparedStatement statement = this.connectionDB
                    .getConnection()
                    .prepareStatement("INSERT INTO comics(id,title)" +
                            " VALUES (?,?)");
        statement.setInt(1, id);
        statement.setString(2, title);

        execute = statement.executeUpdate();

        return execute == 1;

    }



    public List<ComicRow> findComics() throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM COMICS");

        ResultSet resultSet = preparedStatement.executeQuery();
        List<ComicRow> comicRowList = new ArrayList<>();

        while (resultSet.next()) {
            comicRowList.add(new ComicRow(resultSet.getInt(1),
                    resultSet.getString(2)));
        }

        return comicRowList;
    }

    public ComicRow findComicById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM COMICS WHERE ID = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        ComicRow comicRow = null;

        while (resultSet.next()) {
            comicRow = new ComicRow(resultSet.getInt(1),
                    resultSet.getString(2));

        }

        return comicRow;

    }

    public ComicRow findComicByTitle(String comicTitle) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM COMICS WHERE NAME = ? OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");

        preparedStatement.setString(1, comicTitle);

        ResultSet resultSet = preparedStatement.executeQuery();
        ComicRow comicRow = null;
        while (resultSet.next()) {
            comicRow = new ComicRow(resultSet.getInt(1),
                    resultSet.getString(2));
        }

        return comicRow;

    }

    public List<ComicRow> findComicsLike(String pattern, int offset, int next) throws SQLException {

        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM COMICS WHERE TITLE LIKE ? ORDER BY NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        pattern = pattern
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");

        preparedStatement.setString(1, "%" + pattern + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, next);

        List<ComicRow> comicRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            comicRowList.add(new ComicRow(resultSet.getInt(1),
                    resultSet.getString(2)));

        }

        return comicRowList;

    }




}
