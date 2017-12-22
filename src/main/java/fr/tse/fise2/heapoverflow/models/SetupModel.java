package fr.tse.fise2.heapoverflow.models;

import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.database.DataBase;

import java.util.Observable;

public class SetupModel extends Observable {
    public final static int SETUP = 0;
    public final static int INSTALL = 1;
    public final static int FINISHED = 2;
    private boolean isLocalSetup = true;
    private int currentPage = 0;

    public int getCurrentPage() {
        return currentPage;
    }

    public void prevPage() {
        if (this.currentPage > SETUP) {
            this.gotoPage(this.currentPage - 1);
        }
    }

    public void nextPage() {
        if (this.currentPage < FINISHED) {
            this.gotoPage(this.currentPage + 1);
        }
    }

    private void gotoPage(int page) {
        this.currentPage = page;
        setChanged();
        notifyObservers();
    }

    public void cancelConfig() {
        DataBase.cleanUpDB(ConnectionDB.getInstance());
    }

    public boolean isLocalSetup() {
        return isLocalSetup;
    }

    public void setLocalSetup(boolean localSetup) {
        isLocalSetup = localSetup;
    }

}
