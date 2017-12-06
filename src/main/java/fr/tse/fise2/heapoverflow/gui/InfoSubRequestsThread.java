package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.util.Queue;
import java.util.Stack;

public class InfoSubRequestsThread implements Runnable {
    Thread th;
    Queue<Job> jobs;


    @Override
    public void run() {
        while (true) {
            if (jobs.isEmpty()) {
                //TODO pause thread
            }
            //TODO accept next job
        }
    }

    //TODO add job
}

class Job {
    Class eClass;
    ListModel modelToFill;
    String shortUri;

    public Job(Class eClass, ListModel modelToFill, String shortUri) {
        this.eClass = eClass;
        this.modelToFill = modelToFill;
    }
}