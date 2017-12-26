package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.gui.GradesPanel;

import java.util.List;

/**
 * @author Darios DJIMADO
 */
public class GradesPanelController {
    private final GradesPanel gradesPanel;

    public GradesPanelController(GradesPanel gradesPanel) {
        this.gradesPanel = gradesPanel;
    }

    public void init() {
        List<GradesPanel.GradeButton> gradeButtons = this.gradesPanel.getGrades();
        for (GradesPanel.GradeButton button : gradeButtons) {
            button.addActionListener(e -> {
                this.gradesPanel.setCurrentGrade(button.getGrade());
            });
        }
    }
}
