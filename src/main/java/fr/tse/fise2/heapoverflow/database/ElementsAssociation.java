package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElementBase;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelElement.CHARACTER;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelElement.COMIC;

/**
 * @author Darios DJIMADO
 */
public class ElementsAssociation {

    /**
     * Updates grade in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateGrade(int elementUid, int userId, int value) {
        updateElementInt(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET GRADE = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * Updates user comment in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateUserComment(int elementUid, int userId, String value) {
        updateElementString(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET COMMENT = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * Updates read status in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateRead(int elementUid, int userId, boolean value) {
        updateElementBoolean(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET IS_READ = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * Updates favorite status in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateFavorite(int elementUid, int userId, boolean value) {
        updateElementBoolean(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET FAVORITE = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * Updates collection id column in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateCollection(int elementUid, int userId, int value) {
        updateElementInt(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET COLLECTION_ID = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * Updates owned status column in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     */
    public static void updateOwned(int elementUid, int userId, boolean value) {
        updateElementBoolean(elementUid, userId, value, "UPDATE ELEMENTS_ASSOCIATION SET OWNED = ? WHERE UID = ? AND USER_ID = ?");
    }

    /**
     * updates element in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     * @param s          sql string
     */
    private static void updateElementBoolean(int elementUid, int userId, boolean value, @Language("Derby") String s) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection().prepareStatement(s)) {
            preparedStatement.setBoolean(1, value);
            preparedStatement.setInt(2, elementUid);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();

        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    /**
     * updates element in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     * @param s          sql string
     */
    private static void updateElementInt(int elementUid, int userId, int value, @Language("Derby") String s) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection().prepareStatement(s)) {
            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, elementUid);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();

        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    /**
     * updates element in elements association table
     *
     * @param elementUid unique element id
     * @param userId     user unique id
     * @param value      new value
     * @param s          sql string
     */
    private static void updateElementString(int elementUid, int userId, String value, @Language("Derby") String s) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection().prepareStatement(s)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, elementUid);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();

        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    public static List<ElementAssociationRow> findComicsByCollection(int userID, int collectionID) {
        return findElementsByUser(userID, COMIC, "SELECT * FROM ELEMENTS_ASSOCIATION ea INNER JOIN ELEMENTS e ON ea.UID =e.UID" +
                " WHERE user_id = ? AND TYPE = ? AND COLLECTION_ID = " + collectionID);
    }


    /**
     * Selects all rows that match user and comic constraints
     *
     * @param userId user unique id
     * @return list of ElementAssociationRow
     */
    public static List<ElementAssociationRow> findFavoriteComicsByUser(int userId) {
        return findElementsByUser(userId, COMIC, "SELECT * FROM ELEMENTS_ASSOCIATION ea INNER JOIN ELEMENTS e ON ea.UID =e.UID" +
                " WHERE user_id = ? AND TYPE = ? AND FAVORITE = TRUE ");
    }

    /**
     * Selects all rows that match user and character constraints
     *
     * @param userId user unique id
     * @return list of ElementAssociationRow
     */
    public static List<ElementAssociationRow> findCharactersByUser(int userId) {
        return findElementsByUser(userId, CHARACTER, "SELECT * FROM ELEMENTS_ASSOCIATION ea INNER JOIN ELEMENTS e ON ea.UID =e.UID" +
                " WHERE user_id = ? AND TYPE = ?");
    }

    /**
     * Selects all rows that match user and element constraints
     *
     * @param userId user unique id
     * @param type   character or comic
     * @return list of ElementAssociationRow
     */
    @NotNull
    private static List<ElementAssociationRow> findElementsByUser(int userId, MarvelElement type,
                                                                  @Language("Derby") String sql) {
        List<ElementAssociationRow> elementAssociationRows = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, type.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                elementAssociationRows.add(new ElementAssociationRow(resultSet.getInt("uid"),
                        resultSet.getInt("id"),
                        MarvelElementBase.convertToMarvelElement(resultSet.getInt("type")),
                        resultSet.getString("name"),
                        resultSet.getInt("user_id"),
                        resultSet.getBoolean("favorite"),
                        resultSet.getInt("collection_id"),
                        resultSet.getBoolean("is_read"),
                        resultSet.getInt("grade"),
                        resultSet.getString("comment"),
                        resultSet.getBoolean("owned")
                ));
            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
        return elementAssociationRows;
    }

    /**
     * Selects a row that match user and element constraints
     *
     * @param userID user unique id
     * @param type   character or comic
     * @return list of ElementAssociationRow
     */
    @Nullable
    public static ElementAssociationRow findElement(int userID, int id, MarvelElement type) {
        ElementAssociationRow elementAssociationRow = null;

        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("SELECT * FROM ELEMENTS_ASSOCIATION  " +
                        "INNER JOIN ELEMENTS ON ELEMENTS_ASSOCIATION.UID = ELEMENTS.UID" +
                        " WHERE ELEMENTS.ID = ? AND TYPE = ? AND USER_ID = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, type.getValue());
            preparedStatement.setInt(3, userID);
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

                elementAssociationRow = new ElementAssociationRow(resultSet.getInt("uid"),
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
                );
            }
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
        return elementAssociationRow;
    }

    public static void updateCommentCreateAsNeeded(int elementId, String elementName, int userId, String value, MarvelElement marvelElement) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, marvelElement);
        if (findElement(userId, elementId, marvelElement) == null) {
            updateElementString(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(COMMENT,UID,user_id) VALUES(?,?,?)");
        } else {
            updateUserComment(elementUID, userId, value);
        }
    }

    public static void updateReadCreateAsNeeded(int elementId, String elementName, int userId, boolean value, MarvelElement marvelElement) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, marvelElement);
        if (findElement(userId, elementId, marvelElement) == null) {
            updateElementBoolean(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(IS_READ,UID,user_id) VALUES(?,?,?)");
        } else {
            updateRead(elementUID, userId, value);
        }
    }

    public static void updateGradeAsNeeded(int elementId, String elementName, int userId, int value, MarvelElement marvelElement) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, marvelElement);
        if (findElement(userId, elementId, marvelElement) == null) {
            updateElementInt(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(GRADE,UID,user_id) VALUES(?,?,?)");
        } else {
            updateGrade(elementUID, userId, value);
        }
    }

    public static void updateCollectionCreateAsNeeded(int elementId, String elementName, int userId, int value, MarvelElement marvelElement) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, marvelElement);
        if (findElement(userId, elementId, marvelElement) == null) {
            updateElementInt(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(COLLECTION_ID,UID,user_id) VALUES(?,?,?)");
        } else {
            updateCollection(elementUID, userId, value);
        }
    }

    public static void updateFavoriteCreateAsNeeded(int elementId, String elementName, int userId, boolean value, MarvelElement marvelElement) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, marvelElement);
        if (findElement(userId, elementId, marvelElement) == null) {
            updateElementBoolean(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(FAVORITE,UID,user_id) VALUES(?,?,?)");
        } else {
            updateFavorite(elementUID, userId, value);
        }
    }

    public static void updateOwnedCreateAsNeeded(int elementId, String elementName, int userId, boolean value) {
        int elementUID = getElementUidFromMarvelElementTable(elementId, elementName, MarvelElement.COMIC);
        if (findElement(userId, elementId, MarvelElement.COMIC) == null) {
            updateElementBoolean(elementUID, userId, value, "INSERT INTO ELEMENTS_ASSOCIATION(OWNED,UID,user_id) VALUES(?,?,?)");
        } else {
            updateOwned(elementUID, userId, value);
        }
    }


    public static void onDeleteCollection(int collectionID, int userID) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection().
                prepareStatement("UPDATE  ELEMENTS_ASSOCIATION SET COLLECTION_ID = NULL " +
                        "WHERE COLLECTION_ID = ? AND USER_ID = ?")) {
            preparedStatement.setInt(1, collectionID);
            preparedStatement.setInt(2, userID);
            preparedStatement.execute();
        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }

    public static void removeCollection(int userID, int collectionID, int elementID) {
        try (PreparedStatement preparedStatement = ConnectionDB.getInstance().getConnection()
                .prepareStatement("UPDATE ELEMENTS_ASSOCIATION SET COLLECTION_ID = NULL " +
                        "WHERE USER_ID = ? AND COLLECTION_ID = ? AND UID = (SELECT UID FROM ELEMENTS WHERE ID = ?)")) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, collectionID);
            preparedStatement.setInt(3, elementID);

            preparedStatement.execute();

        } catch (SQLException e) {
            AppErrorHandler.onError(e);
        }
    }


    private static int getElementUidFromMarvelElementTable(int elementId, String elementName, MarvelElement marvelElement) {
        int elementUID;
        switch (marvelElement) {
            case COMIC: {
                elementUID = MarvelElementTable.elementsExists(elementId, COMIC);
                if (elementUID == -1) {
                    elementUID = MarvelElementTable.insertComic(elementId, elementName);
                }
                break;
            }
            case CHARACTER: {
                elementUID = MarvelElementTable.elementsExists(elementId, CHARACTER);
                if (elementUID == -1) {
                    elementUID = MarvelElementTable.insertCharacter(elementId, elementName);
                }
                break;
            }
            default:
                elementUID = -1;
                break;
        }
        return elementUID;
    }
}
