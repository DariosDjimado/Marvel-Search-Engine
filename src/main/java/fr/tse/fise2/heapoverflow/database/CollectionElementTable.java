package fr.tse.fise2.heapoverflow.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CollectionElementTable {
    public static void insertElement(CollectionElementRow collectionElementRow, CollectionsListRow collectionsListRow)throws SQLException {
        PreparedStatement statement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("INSERT INTO collection_elements(collection_id,marvel_element_id)" +
                        " VALUES (?,?)");
        statement.setInt(1, collectionsListRow.getId());
        statement.setInt(2, collectionElementRow.getElement_id());

        statement.execute();
    }
    }
