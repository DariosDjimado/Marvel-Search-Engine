package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.interfaces.DataBaseErrors;

import java.sql.SQLException;

/**
 * @author Darios DJIMADO
 */

public class DataBaseErrorHandler implements DataBaseErrors {

    @Override
    public void emitConnectionFailed(SQLException e) {
        onConnectionFailed(e);
    }

    @Override
    public void onConnectionFailed(SQLException e) {
        e.printStackTrace();
    }

    @Override
    public void emitCreateCharactersTableFailed(SQLException e) {
        onCreateCharactersTableFailed(e);
    }

    @Override
    public void onCreateCharactersTableFailed(SQLException e) {

    }

    @Override
    public void emitCreateComicsTableFailed(SQLException e) {
        onCreateComicsTableFailed(e);
    }

    @Override
    public void onCreateComicsTableFailed(SQLException e) {

    }
}
