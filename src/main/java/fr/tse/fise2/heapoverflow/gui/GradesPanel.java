package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GradesPanel extends JPanel {

    private static final ImageIcon starAddIcon = new ImageIcon(GradesPanel.class.getResource("star_add.png"));
    private static final ImageIcon starRemoveIcon = new ImageIcon(GradesPanel.class.getResource("star_remove.png"));

    private List<GradeButton> grades = new ArrayList<>();
    private int currentGrade = 0;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    GradesPanel() {
        this.setOpaque(false);
        for (int i = 1; i < 6; i++) {
            final GradeButton gradeButton = new GradeButton(i);
            grades.add(gradeButton);
            this.add(gradeButton);
        }

    }

    private void select(int i) {
        for (GradeButton gradeButton : grades) {
            if (gradeButton.getGrade() <= i) {
                gradeButton.setIcon(starAddIcon);
            } else {
                gradeButton.setIcon(starRemoveIcon);
            }
        }
    }

    private void deselect() {
        for (GradeButton gradeButton : grades) {
            if (gradeButton.getGrade() <= this.currentGrade) {
                gradeButton.setIcon(starAddIcon);
            } else {
                gradeButton.setIcon(starRemoveIcon);
            }
        }
    }

    public List<GradeButton> getGrades() {
        return grades;
    }

    public void setCurrentGrade(int currentGrade) {
        this.currentGrade = currentGrade;
        select(this.currentGrade);
    }

    public class GradeButton extends ButtonFormat {
        private final int grade;

        GradeButton(int grade) {
            super();
            this.grade = grade;

            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
            this.setOpaque(false);

            Dimension dimension = new Dimension(10, 12);
            this.setMinimumSize(dimension);
            this.setPreferredSize(dimension);
            this.setMaximumSize(dimension);

            this.setIcon(starRemoveIcon);
            this.addMouseListener(new MouseAdapter() {
                /**
                 * {@inheritDoc}
                 *
                 * @param e
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    select(grade);
                }

                /**
                 * {@inheritDoc}
                 *
                 * @param e
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    deselect();
                }
            });
        }

        public int getGrade() {
            return grade;
        }
    }
}
