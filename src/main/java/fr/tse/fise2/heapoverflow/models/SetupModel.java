package fr.tse.fise2.heapoverflow.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Observable;

public class SetupModel extends Observable {
    private boolean isLocalSetup;
    private int currentPage = 0;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;

    }

    public void gotoPage(int page) {
        this.currentPage = page;
        setChanged();
        notifyObservers();
    }

    public boolean isLocalSetup() {
        return isLocalSetup;
    }

    public void setLocalSetup(boolean localSetup) {
        isLocalSetup = localSetup;
        setChanged();
        notifyObservers();
    }

}
