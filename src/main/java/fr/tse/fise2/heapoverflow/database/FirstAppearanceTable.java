package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FirstAppearanceTable {

    public static void insert(String character, String date, String comic) throws SQLException {
        PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("INSERT INTO " +
                        "FIRST_APPEARANCE(CHARACTERR," +
                        "DATE," +
                        "COMIC)" +
                        "VALUES (?,?,?)");
        {
            preparedStatement.setString(1, character);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, comic);
            preparedStatement.execute();
        }
    }


    public static List<FirstAppearanceRow> findAll() {
        List<FirstAppearanceRow> firstAppearanceRows = new ArrayList<>();


        try (Statement statement = ConnectionDB.getInstance().getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FIRST_APPEARANCE");


            while (resultSet.next()) {
                firstAppearanceRows.add(new FirstAppearanceRow(
                        resultSet.getString("characterr"),
                        resultSet.getString("date"),
                        resultSet.getString("comic")));
            }


        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
        return firstAppearanceRows;
    }


    @Nullable
    public static FirstAppearanceRow getFirstAppearanceRow(String characterName) {

        FirstAppearanceRow firstAppearanceRow = null;

        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("SELECT * FROM FIRST_APPEARANCE WHERE CHARACTERR = ?")) {

            preparedStatement.setString(1, characterName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                firstAppearanceRow = new FirstAppearanceRow(
                        resultSet.getString("characterr"),
                        resultSet.getString("date"),
                        resultSet.getString("comic"));
            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }

        return firstAppearanceRow;
    }

    public static boolean exist(String character) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("SELECT * FROM FIRST_APPEARANCE WHERE CHARACTERR = ?");
        preparedStatement.setString(1, character);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString(1) != null) {
                found = true;
            }
        }

        return found;

    }
}
