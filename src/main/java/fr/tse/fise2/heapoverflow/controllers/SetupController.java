package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class SetupController {
    private SetupModel model;
    private SetupView view = null;


    public SetupController(SetupModel model) {
        this.model = model;
    }

    public void prevPage() {
        model.gotoPage(0);
    }

    public void nextPage() {
        model.gotoPage(1);
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
                    File file = new File("characters.csv");
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (view != null) {
                            view.onLog(Arrays.toString(line.split(";")));
                        }

                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

    public void setView(SetupView view) {
        this.view = view;
    }

}
