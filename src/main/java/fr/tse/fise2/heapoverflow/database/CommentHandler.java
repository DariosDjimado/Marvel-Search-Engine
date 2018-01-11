package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElementBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentHandler {
    public static List<ElementAssociationRow> findCommentsByComic(int id) {
        List<ElementAssociationRow> elementAssociationRows = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS_ASSOCIATION EA INNER JOIN ELEMENTS E ON EA.UID = E.UID WHERE E.ID = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String comment = resultSet.getString("comment");
                if (resultSet.wasNull()) {
                    comment = "";
                }
                int collectionID = resultSet.getInt("collection_id");
                if (resultSet.wasNull()) {
                    collectionID = -1;
                }

                elementAssociationRows.add(new ElementAssociationRow(resultSet.getInt("uid"),
                        resultSet.getInt("id"),
                        MarvelElementBase.convertToMarvelElement(resultSet.getInt("type")),
                        resultSet.getString("name"),
                        resultSet.getInt("user_id"),
                        resultSet.getBoolean("favorite"),
                        collectionID,
                        resultSet.getBoolean("is_read"),
                        resultSet.getInt("grade"),
                        comment,
                        resultSet.getBoolean("owned")
                ));
            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
        return elementAssociationRows;
    }
}
