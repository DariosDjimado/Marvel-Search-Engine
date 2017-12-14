package fr.tse.fise2.heapoverflow.database;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionsListTable {
    public static void insertCollection(String title, String description) throws SQLException {
        PreparedStatement statement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("INSERT INTO collections(title,description)" +
                        " VALUES (?,?)");
        statement.setString(1, title);
        statement.setString(2, description);

        statement.execute();
    }

    public static CollectionsListRow findCollection(int id) throws SQLException {
        PreparedStatement statement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT * FROM collections WHERE COLLECTION_ID = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        CollectionsListRow collectionsListRow = null;
        while (resultSet.next()) {
            collectionsListRow = new CollectionsListRow(resultSet.getInt(1),
                    resultSet.getString(2), resultSet.getString(3));
        }
        return collectionsListRow;
    }

    @NotNull
    public static List<CollectionsListRow> findCollections() throws SQLException {
        PreparedStatement statement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT * FROM collections");
        ResultSet resultSet = statement.executeQuery();
        List<CollectionsListRow> collectionsListRows = new ArrayList<>();
        while (resultSet.next()) {
            collectionsListRows.add(new CollectionsListRow(resultSet.getInt(1),
                    resultSet.getString(2), resultSet.getString(3)));
        }
        return collectionsListRows;
    }


    public static boolean existCollection(int id) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT COUNT(*) FROM COLLECTIONS WHERE COLLECTION_ID = ? ");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(1) > 0) {
                found = true;
                System.out.println(id);
            }
        }
        return found;
    }
}
