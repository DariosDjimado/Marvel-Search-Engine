package fr.tse.fise2.heapoverflow.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharactersTable {

    private final ConnectionDB connectionDB;

    public CharactersTable(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }


    public boolean insertIntoCharacters(int id, String name, String wikipedia_en_url) throws SQLException {
        return insertIntoCharacters(id, name, wikipedia_en_url, null);
    }

    public boolean insertIntoCharacters(int id, String name) throws SQLException {
        return insertIntoCharacters(id, name, null, null);
    }

    public boolean insertIntoCharacters(int id, String name, String wikipedia_en_url, String wikipedia_fr_url)
            throws SQLException {
        int execute;

        if (wikipedia_fr_url == null) {
            if (wikipedia_en_url == null) {

                PreparedStatement statement = this.connectionDB
                        .getConnection()
                        .prepareStatement("INSERT INTO characters(id,name) VALUES (?,?)");
                statement.setInt(1, id);
                statement.setString(2, name);
                execute = statement.executeUpdate();

            } else {
                PreparedStatement statement = this.connectionDB
                        .getConnection()
                        .prepareStatement("INSERT INTO characters(id,name,wikipedia_en_url) VALUES (?,?,?)");

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, wikipedia_en_url);
                execute = statement.executeUpdate();

            }

        } else {
            PreparedStatement statement = this.connectionDB
                    .getConnection()
                    .prepareStatement("INSERT INTO characters(id,name,wikipedia_en_url,wikipedia_fr_url)" +
                            " VALUES (?,?,?,?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, wikipedia_en_url);
            statement.setString(4, wikipedia_fr_url);

            execute = statement.executeUpdate();
        }

        return execute == 1;

    }


    public List<CharacterRow> findCharacters() throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM CHARACTERS");

        ResultSet resultSet = preparedStatement.executeQuery();
        List<CharacterRow> characterRowList = new ArrayList<>();

        while (resultSet.next()) {
            characterRowList.add(new CharacterRow(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)));
        }

        return characterRowList;
    }

    public CharacterRow findCharacterById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM CHARACTERS WHERE ID = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        CharacterRow characterRow = null;

        while (resultSet.next()) {
            characterRow = new CharacterRow(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

        }

        return characterRow;

    }

    public CharacterRow findCharacterByName(String characterName) throws SQLException {
        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM CHARACTERS WHERE NAME = ? OFFSET 0 ROWS FETCH  NEXT 1 ROWS ONLY ");

        preparedStatement.setString(1, characterName);

        ResultSet resultSet = preparedStatement.executeQuery();
        CharacterRow characterRow = null;
        while (resultSet.next()) {
            characterRow = new CharacterRow(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
        }

        return characterRow;

    }

    public List<CharacterRow> findCharactersLike(String pattern, int offset, int next) throws SQLException {

        PreparedStatement preparedStatement = this.connectionDB
                .getConnection()
                .prepareStatement("SELECT * FROM CHARACTERS WHERE NAME LIKE ? ORDER BY NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        pattern = pattern
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");

        preparedStatement.setString(1, "%" + pattern + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, next);

        List<CharacterRow> characterRowList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            characterRowList.add(new CharacterRow(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)));
        }

        return characterRowList;

    }




}


