package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.database.FirstAppearanceTable;
import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.database.WikipediaUrlsTable;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
                        view.onLog("creating database");
                        if (CreateTables.createAllTables()) {
                            view.onLog("database created");
                        } else {
                            view.onLog("an error occured when creating database");
                        }
                        saveCharactersUrls();
                        saveCharacterFirstAppearance();
                        saveElement("characters_sample.csv", CHARACTER);
                        saveElement("comics_sample.csv", COMIC);
                        if (view != null) {
                            view.setInstallFinished();
                        }
                        model.nextPage();
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

    private void saveElement(String filePath, MarvelElement elements) {
        File sample = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(sample))) {
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
        } catch (Exception e) {
            AppErrorHandler.onError(e);
        }
    }
    private void saveCharacterFirstAppearance() {
        File sample = new File("characters_first_appearance.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(sample))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (view != null) {
                    String[] lineArray = line.split(";");
                    String character = lineArray[0];
                    String date = lineArray[1];
                    String comic = lineArray[2];
                    FirstAppearanceTable.insert(character,date,comic);
                    view.onLog("insert first appearance of " + character);
                }
            }
        } catch (Exception e) {
            AppErrorHandler.onError(e);
        }
    }

    private void saveCharactersUrls() {
        File sample = new File("characters_urls_sample.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(sample))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (view != null) {
                    String[] lineArray = line.split(";");
                    String characterName = lineArray[0].trim();
                    String characterLabel = lineArray[1].trim().equals("-") ? "" : lineArray[1].trim();
                    String characterUrl = lineArray[2].trim();
                    String characterAlias = lineArray[3].trim().equals("-") ? "" : lineArray[3].trim();
                    String characterDescription = lineArray[4].trim().equals("-") ? "" : lineArray[4].trim();
                    WikipediaUrlsTable.insert(characterName, characterLabel, characterUrl, characterAlias, characterDescription);
                    view.onLog("insert url: --> " + characterUrl);
                }
            }
        } catch (Exception e) {
            AppErrorHandler.onError(e);
        }
    }

    public void setView(SetupView view) {
        this.view = view;
    }

}
