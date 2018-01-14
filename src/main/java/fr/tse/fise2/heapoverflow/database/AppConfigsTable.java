package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;

import java.sql.*;

public class AppConfigsTable {

    public static void insertConfig(AppConfigRow appConfigRow) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("INSERT INTO APP_CONFIGS(APP_VERSION,APP_SECTION,REQUEST_COUNT,REQUEST_LAST_CHECK)" +
                        " VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, appConfigRow.getAppVersion());
            preparedStatement.setString(2, appConfigRow.getAppSection());
            preparedStatement.setInt(3, appConfigRow.getRequestCount());
            preparedStatement.setDate(4, appConfigRow.getRequestLastCheck());
            preparedStatement.execute();
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    public static void updateRequestCount(int id) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("UPDATE APP_CONFIGS SET REQUEST_COUNT = ? WHERE APP_VERSION = ? AND APP_SECTION = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    public static AppConfigRow getConfig() throws SQLException {
        AppConfigRow appConfigRow = null;
        try (Statement statement = ConnectionDB.getInstance().getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM APP_CONFIGS");
            boolean found = false;
            while (resultSet.next()) {
                if (!found) {
                    appConfigRow = new AppConfigRow(resultSet.getString("app_version"),
                            resultSet.getString("app_section"),
                            resultSet.getInt("request_count"),
                            resultSet.getDate("request_last_check"));
                    found = true;
                } else {
                    throw new SQLException("more than one config found");
                }
            }
        }


        return appConfigRow;
    }

}
