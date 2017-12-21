package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElements;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class FavoritesTable {

    public static void insertFavorite(FavoriteRow favoriteRow) throws SQLException {
        int elementUid;
        if (favoriteRow.getType() == MarvelElements.COMIC.getValue()) {
            elementUid = MarvelElementTable.elementsExists(favoriteRow.getId(), MarvelElements.COMIC);
            if (elementUid == -1) {
                elementUid = MarvelElementTable.insertComic(favoriteRow.getId(), favoriteRow.getName());
            }
        } else {
            elementUid = MarvelElementTable.elementsExists(favoriteRow.getId(), MarvelElements.CHARACTER);
            if (elementUid == -1) {
                elementUid = MarvelElementTable.insertCharacter(favoriteRow.getId(), favoriteRow.getName());
            }
        }
        favoriteRow.setUid(elementUid);
        favoriteRow.setFavorite(true);
        if (existsFavorite(favoriteRow.getUserId(), favoriteRow.getId(), favoriteRow.getType()) == null) {
            internalManipulation(favoriteRow, "INSERT INTO ELEMENTS_ASSOCIATION(FAVORITE,UID,user_id) VALUES(?,?,?)");
        } else {
            internalManipulation(favoriteRow, "UPDATE ELEMENTS_ASSOCIATION SET FAVORITE = ? WHERE UID =? AND  user_id = ?");
        }
    }

    public static void deleteFavorite(FavoriteRow favoriteRow) throws SQLException {
        favoriteRow.setFavorite(false);
        internalManipulation(favoriteRow, "UPDATE ELEMENTS_ASSOCIATION SET FAVORITE = ? WHERE UID = ? AND user_id = ?");
    }

    public static List<FavoriteRow> findFavoriteComicsByUser(int userId) throws SQLException {
        return getFavoriteRows(userId, MarvelElements.COMIC.getValue());
    }

    public static List<FavoriteRow> findFavoriteCharactersByUser(int userId) throws SQLException {
        return getFavoriteRows(userId, MarvelElements.CHARACTER.getValue());
    }

    private static List<FavoriteRow> getFavoriteRows(int userId, int type) throws SQLException {
        final PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS_ASSOCIATION ea INNER JOIN ELEMENTS e ON ea.UID =e.UID" +
                        " WHERE user_id = ? AND TYPE = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<FavoriteRow> favorites = new ArrayList<>();
        while (resultSet.next()) {
            favorites.add(new FavoriteRow(resultSet.getInt("uid"),
                    resultSet.getInt("id"),
                    resultSet.getInt("type"),
                    resultSet.getString("name"),
                    resultSet.getInt("user_id"),
                    resultSet.getBoolean("favorite")));
        }
        return favorites;
    }

    private static void internalManipulation(FavoriteRow favoriteRow, @Language("Derby") String s) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement(s);
        preparedStatement.setBoolean(1, favoriteRow.isFavorite());
        preparedStatement.setInt(2, favoriteRow.getUid());
        preparedStatement.setInt(3, favoriteRow.getUserId());
        preparedStatement.execute();
    }

    public static FavoriteRow existsFavorite(int userID, int id, int type) throws SQLException {
        FavoriteRow favoriteRow = null;
        PreparedStatement preparedStatement = ConnectionDB.getInstance()
                .getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS_ASSOCIATION  " +
                        "INNER JOIN ELEMENTS ON ELEMENTS_ASSOCIATION.UID = ELEMENTS.UID" +
                        " WHERE ELEMENTS.ID = ? AND TYPE = ? AND USER_ID = ?");

        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, type);
        preparedStatement.setInt(3, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            favoriteRow = new FavoriteRow(resultSet.getInt("uid"),
                    resultSet.getInt("id"),
                    resultSet.getInt("type"), resultSet.getString("name"),
                    resultSet.getInt("user_id"),
                    resultSet.getBoolean("favorite"));
        }
        return favoriteRow;
    }
}
