package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Darios DJIMADO
 */
public class WikipediaUrlsTable {

    public static void insert(String characterName, String characterLabel, String characterUrl, String characterAlias, String characterDescription) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("INSERT INTO " +
                        "WIKIPEDIA_URLS(CHARACTER_NAME," +
                        "CHARACTER_LABEL," +
                        "CHARACTER_URL," +
                        "CHARACTER_ALIAS," +
                        "CHARACTER_DESCRIPTION) VALUES (?,?,?,?,?)")) {
            preparedStatement.setString(1, characterName);
            preparedStatement.setString(2, characterLabel);
            preparedStatement.setString(3, characterUrl);
            preparedStatement.setString(4, characterAlias);
            preparedStatement.setString(5, characterDescription);

            preparedStatement.execute();
        }
    }

    public static String findByLabel(String characterLabel) {
        if (characterLabel.contains("(") && !characterLabel.toLowerCase().contains("ultimate")) {

            String characterLabelTemp = characterLabel.substring(0, characterLabel.indexOf('(')).trim();

            String characterAlias = characterLabel.substring(characterLabel.indexOf('(') + 1, characterLabel.indexOf(')'));

            List<WikipediaUrlRow> wur = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL = ?", characterLabelTemp, false);


            /*
            -------------------------------------- From wikipedia ------------------------------------
            character_name || character_label || character_url || character_alias
            Enchantress (Marvel Comics) || Enchantress || https://en.wikipedia.org/wiki/Enchantress_(Marvel_Comics) || Amora
            -------------------------------------- From Marvel API ------------------------------------
            name : Enchantress (Amora)
            */

            /*
            -------------------------------------- From wikipedia ------------------------------------
            character_name || character_label || character_url || character_alias
            Ant-Man (Scott Lang) ||	Ant-Man ||	https://en.wikipedia.org/wiki/Ant-Man_(Scott_Lang) || Scott Lang
            Eric O'Grady ||	Ant-Man ||	https://en.wikipedia.org/wiki/Eric_O%27Grady ||	Eric O'Grady

            -------------------------------------- From Marvel API ------------------------------------
            name : Ant-Man (Scott Lang)
            name : Ant-Man (Eric O'Grady)
            */

            // if we found at least one element
            if (wur.size() > 0) {
                // remove all rows that don't contain the alias neither in their alias nor in their name
                wur.removeIf(wikipediaUrlRow -> !wikipediaUrlRow.getCharacterAlias().toLowerCase()
                        .contains(characterAlias.toLowerCase()) &&
                        !wikipediaUrlRow.getCharacterName().toLowerCase()
                                .contains(characterAlias.toLowerCase()) &&
                        // Mar-Vell --> Mar Vell
                        !wikipediaUrlRow.getCharacterAlias().toLowerCase()
                                .contains(characterAlias.toLowerCase().replaceAll("[^A-Za-z0-9]", " "))

                );
                System.out.println(characterAlias.toLowerCase().replaceAll("[^A-Za-z0-9]", " "));
                // if there is exactly one element we are ok
                if (wur.size() == 1) {
                    return wur.get(0).getCharacterUrl();
                }

                // if we don't found the label, we are going to search by character's name
                // we do this by using the like operator

                List<WikipediaUrlRow> wurLike = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_NAME LIKE ?", characterAlias, true);
                // we expect exactly one element
                if (wurLike.size() == 1) {
                    return wurLike.get(0).getCharacterUrl();
                }

                // Thunderbolt (bill carver)
                // forget about the alias
                List<WikipediaUrlRow> wurLabelList = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL = ?", characterLabelTemp, false);
                if (wurLabelList.size() == 1) {
                    return wurLabelList.get(0).getCharacterUrl();
                }

                // remove special characters
                List<WikipediaUrlRow> wurLabelListWithoutSpecialCharacters = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL = ?", characterLabelTemp.replaceAll("[^A-Za-z0-9]", " "), false);
                if (wurLabelListWithoutSpecialCharacters.size() == 1) {
                    return wurLabelListWithoutSpecialCharacters.get(0).getCharacterUrl();
                }

            }

        } else {
            List<WikipediaUrlRow> wur = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL = ?", characterLabel, false);
            if (wur.size() == 1) {
                return wur.get(0).getCharacterUrl();
            }

            // remove special characters
            List<WikipediaUrlRow> wurLabelListWithoutSpecialCharacters = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL = ?", characterLabel.replaceAll("[^A-Za-z0-9]", " "), false);
            if (wurLabelListWithoutSpecialCharacters.size() == 1) {
                return wurLabelListWithoutSpecialCharacters.get(0).getCharacterUrl();
            }

            // Pip --> Pip the Troll
            List<WikipediaUrlRow> wurLabelLikeList = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_LABEL LIKE ?", characterLabel, true);
            if (wurLabelLikeList.size() == 1) {
                return wurLabelLikeList.get(0).getCharacterUrl();
            }

            if (wurLabelLikeList.size() > 0) {
                wurLabelLikeList.removeIf(wikipediaUrlRow ->
                        !Arrays.asList(wikipediaUrlRow.getCharacterName().toLowerCase().split("\\s+"))
                                .contains(characterLabel.toLowerCase()));
                if (wurLabelLikeList.size() == 1) {
                    return wurLabelLikeList.get(0).getCharacterUrl();
                }
            }

            // Namor The Sub-Mariner --> Sub-Mariner
            List<WikipediaUrlRow> wurAlisLikeList = getRowsWithOneParameter("SELECT * FROM WIKIPEDIA_URLS WHERE CHARACTER_ALIAS LIKE ?", characterLabel, true);
            // we expect exactly one element
            if (wurAlisLikeList.size() == 1) {
                return wurAlisLikeList.get(0).getCharacterUrl();
            }


        }

        return "";
    }

    public static List<WikipediaUrlRow> fincUrls() {
        List<WikipediaUrlRow> wikipediaUrlRows = new LinkedList<>();
        try (Statement statement = ConnectionDB.getInstance().getConnection().createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM WIKIPEDIA_URLS");
            while (resultSet.next()) {
                wikipediaUrlRows.add(new WikipediaUrlRow(resultSet.getString("character_name"),
                        resultSet.getString("character_label"),
                        resultSet.getString("character_url"),
                        resultSet.getString("character_alias"),
                        resultSet.getString("character_description")
                ));
            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
        return wikipediaUrlRows;
    }


    private static List<WikipediaUrlRow> getRowsWithOneParameter(@Language("Derby") String sql, String parameter, boolean isLike) {
        List<WikipediaUrlRow> wikipediaUrlRows = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)) {
            if (isLike) {
                preparedStatement.setString(1, "%" + parameter + "%");
            } else {
                preparedStatement.setString(1, parameter);
            }
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                wikipediaUrlRows.add(new WikipediaUrlRow(result.getString("character_name"),
                        result.getString("character_label"),
                        result.getString("character_url"),
                        result.getString("character_alias"),
                        result.getString("character_description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wikipediaUrlRows;
    }
}
