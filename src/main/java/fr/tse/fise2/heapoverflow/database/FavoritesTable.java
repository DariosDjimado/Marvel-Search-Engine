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
        internalManipulation(favoriteRow, "INSERT INTO favorites(id,type,user_id) VALUES(?,?,?)");
    }

    public static void deleteFavorite(FavoriteRow favoriteRow) throws SQLException {
        internalManipulation(favoriteRow, "DELETE FROM favorites WHERE id = ? AND type = ? AND user_id = ?");
    }

    public static List<FavoriteRow> findFavoriteComicsByUser(int userId) throws SQLException {
        return getFavoriteRows(userId, MarvelElements.COMIC.getValue());

    }

    public static List<FavoriteRow> findFavoriteCharactersByUser(int userId) throws SQLException {
        return getFavoriteRows(userId, MarvelElements.CHARACTER.getValue());

    }

    private static List<FavoriteRow> getFavoriteRows(int userId, int type) throws SQLException {
        final PreparedStatement preparedStatement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT * FROM FAVORITES WHERE user_id = ? AND TYPE = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<FavoriteRow> favorites = new ArrayList<>();
        while (resultSet.next()) {
            favorites.add(new FavoriteRow(resultSet.getInt("id"), resultSet.getInt("type"),
                    resultSet.getInt("user_id")));
        }
        return favorites;
    }

    private static void internalManipulation(FavoriteRow favoriteRow, @Language("Derby") String s) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement(s);
        preparedStatement.setInt(1, favoriteRow.getId());
        preparedStatement.setInt(2, favoriteRow.getType());
        preparedStatement.setInt(3, favoriteRow.getUserId());
        preparedStatement.execute();
    }
}
