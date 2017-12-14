package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionsListTable {
    public static void insertCollection(String title, String description) throws SQLException {
        PreparedStatement statement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("INSERT INTO collections(collection_id,title,description)" +
                        " VALUES (?,?,?)");
        statement.setString(2, title);
        statement.setString(3, description);

        statement.execute();
    }

    public static List<CollectionsListRow> findCollection(int id) throws Exception{
        PreparedStatement statement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT FROM collections where COLLECTION_ID = ?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        List<CollectionsListRow> collectionsListRows = new ArrayList<>();
        while (resultSet.next()) {
            collectionsListRows.add(new CollectionsListRow(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));
        }
        return collectionsListRows;


        statement.execute();
    }

    public static boolean existCollection(int id) throws Exception{
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
}
