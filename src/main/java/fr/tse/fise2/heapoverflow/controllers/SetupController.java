package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelElement.CHARACTER;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelElement.COMIC;

public class SetupController {
    private SetupModel model;
    private SetupView view = null;


    public SetupController(SetupModel model) {
        this.model = model;
    }

    public void prevPage() {
        model.prevPage();
    }

    public void nextPage() {
        this.model.nextPage();
        if (this.model.getCurrentPage() == SetupModel.INSTALL) {
            if (this.view.getRemoteConfig().isSelected()) {
                this.model.setLocalSetup(false);
            } else {
                this.model.setLocalSetup(true);
            }
            if (this.model.isLocalSetup()) {
                Thread thread = new Thread() {
                    /**
                     * When an object implementing interface <code>Runnable</code> is used
                     * to create a thread, starting the thread causes the object's
                     * <code>run</code> method to be called in that separately executing
                     * thread.
                     * <p>
                     * The general contract of the method <code>run</code> is that it may
                     * take any action whatsoever.
                     *
                     * @see Thread#run()
                     */
                    @Override
                    public void run() {
                        try {
                            view.onLog("creating database");
                            if (CreateTables.createAllTables()) {
                                view.onLog("database created");
                            } else {
                                view.onLog("an error occured when creating database");
                            }
                            saveElement("characters_sample.csv", CHARACTER);
                            saveElement("comics_sample.csv", COMIC);
                            if (view != null) {
                                view.setInstallFinished();
                            }
                            model.nextPage();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            } else {
                if (view != null) {
                    view.onLog("not yet");
                }
            }
        }
    }

    private void saveElement(String filePath, MarvelElement elements) throws IOException {
        File sample = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(sample));
        String line;
        while ((line = reader.readLine()) != null) {
            if (view != null) {
                String[] lineArray = line.split(";");
                int id = Integer.parseInt(lineArray[0]);
                String name = lineArray[1];
                switch (elements) {
                    case CHARACTER: {
                        MarvelElementTable.insertCharacter(id, name);
                        break;
                    }
                    case COMIC: {
                        MarvelElementTable.insertComic(id, name);
                        break;
                    }
                    default:
                        break;
                }

                view.onLog("insert " + elements.name().toLowerCase() + " " + name + " into database");
            }
        }
    }

    public void setView(SetupView view) {
        this.view = view;
    }

}
