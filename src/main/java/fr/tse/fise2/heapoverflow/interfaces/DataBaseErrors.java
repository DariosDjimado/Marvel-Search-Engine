package fr.tse.fise2.heapoverflow.interfaces;

import java.sql.SQLException;

public interface DataBaseErrors {

    public void emitConnectionFailed(SQLException e);
    public void onConnectionFailed(SQLException e);

    public void emitCreateCharactersTableFailed(SQLException e);
    public void onCreateCharactersTableFailed(SQLException e);

    public void emitCreateComicsTableFailed(SQLException e);
    public void onCreateComicsTableFailed(SQLException e);

}
