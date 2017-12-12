package fr.tse.fise2.heapoverflow.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CacheUrlsTable {
    public static void insertUrls(String shortenUrl, String completeUrl) throws SQLException {

        final PreparedStatement preparedStatement = ConnectionDB.getConnectionDB()
                .getConnection().prepareStatement("INSERT INTO cache_urls VALUES(?,?)");

        preparedStatement.setString(1, shortenUrl);
        preparedStatement.setString(2, completeUrl);
        preparedStatement.execute();
    }

    public static CacheUrlsRow findCompleteUrl(String shortenUrl) throws SQLException {
        final PreparedStatement preparedStatement = ConnectionDB.getConnectionDB().getConnection()
                .prepareStatement("SELECT * FROM cache_urls WHERE shorten_url = ?");

        preparedStatement.setString(1, shortenUrl);

        ResultSet result = preparedStatement.executeQuery();
        CacheUrlsRow cacheUrlsRow = null;
        while (result.next()) {
            cacheUrlsRow = new CacheUrlsRow(result.getString("shorten_url"), result.getString("complete_url"));
        }
        return cacheUrlsRow;
    }

    public static boolean exists(String shortenUrl) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = ConnectionDB.getConnectionDB()
                .getConnection()
                .prepareStatement("SELECT COUNT(*) FROM cache_urls WHERE shorten_url = ?");

        preparedStatement.setString(1, shortenUrl);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(1) > 0) {
                found = true;
            }
        }
        return found;
    }

    public static void empty() throws SQLException {
        ConnectionDB.getConnectionDB().getConnection().createStatement().execute("DELETE FROM CACHE_URLS WHERE 1=1");
    }

}
