package fr.tse.fise2.heapoverflow.database;

import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    public static void cleanUpDB(final ConnectionDB CONNECTION_DB) {
        Statement selectAllConstraintsStatement = null;
        Statement dropAConstraintStatement = null;
        Statement dropSingleTableStatement = null;
        Statement selectAllTablesStatement = null;
        try {
            selectAllConstraintsStatement = CONNECTION_DB.getConnection().createStatement();
            dropAConstraintStatement = CONNECTION_DB.getConnection().createStatement();
            dropSingleTableStatement = CONNECTION_DB.getConnection().createStatement();
            selectAllTablesStatement = CONNECTION_DB.getConnection().createStatement();
            // drop constraints
            @Language("Derby") String dropConstraints =
                    "SELECT 'ALTER TABLE ' || S.SCHEMANAME || '.' || T.TABLENAME || ' DROP CONSTRAINT ' || C.CONSTRAINTNAME " +
                            "FROM SYS.SYSCONSTRAINTS C, SYS.SYSSCHEMAS S, SYS.SYSTABLES T " +
                            "WHERE  C.SCHEMAID = S.SCHEMAID AND C.TABLEID = T.TABLEID AND S.SCHEMANAME = 'APP' ORDER BY  C.CONSTRAINTNAME DESC";
            // drop tables
            @Language("Derby") String dropTables = "SELECT 'DROP TABLE ' || schemaname || '.' || tablename " +
                    "FROM SYS.SYSTABLES INNER JOIN SYS.SYSSCHEMAS ON SYS.SYSTABLES.SCHEMAID = SYS.SYSSCHEMAS.SCHEMAID " +
                    "WHERE schemaname = 'APP'";
            ResultSet dropStatementsResultSet = selectAllConstraintsStatement.executeQuery(dropConstraints);
            while (dropStatementsResultSet.next()) {
                dropAConstraintStatement.execute(dropStatementsResultSet.getString(1));
            }
            ResultSet dropTablesStatementsResultSet = selectAllTablesStatement.executeQuery(dropTables);
            while (dropTablesStatementsResultSet.next()) {
                dropSingleTableStatement.execute(dropTablesStatementsResultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selectAllConstraintsStatement != null) {
                    selectAllConstraintsStatement.close();
                }
                if (dropSingleTableStatement != null) {
                    dropSingleTableStatement.close();
                }
                if (dropAConstraintStatement != null) {
                    dropAConstraintStatement.close();
                }
                if (selectAllTablesStatement != null) {
                    selectAllTablesStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}