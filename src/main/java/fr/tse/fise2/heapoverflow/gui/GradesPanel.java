package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GradesPanel extends JPanel {

    private static final ImageIcon starAddIcon = new ImageIcon(GradesPanel.class.getResource("star_add.png"));
    private static final ImageIcon starAddAvgIcon = new ImageIcon(GradesPanel.class.getResource("star_add_owner.png"));
    private static final ImageIcon starRemoveIcon = new ImageIcon(GradesPanel.class.getResource("star_remove.png"));

    private List<GradeButton> grades = new ArrayList<>();
    private int currentGrade = 0;
    private int id;
    private MarvelElement type;
    private String elementName;
    private boolean isAverageGrade;


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
        this.isAverageGrade = false;

    }

    private void select(int i) {
        for (GradeButton gradeButton : grades) {
            if (gradeButton.getGrade() <= i) {
                if (isAverageGrade) {
                    gradeButton.setIcon(starAddAvgIcon);
                } else {
                    gradeButton.setIcon(starAddIcon);
                }
            } else {
                gradeButton.setIcon(starRemoveIcon);
            }
        }
    }

    private void deselect() {
        for (GradeButton gradeButton : grades) {
            if (gradeButton.getGrade() <= this.currentGrade) {
                if (isAverageGrade) {
                    gradeButton.setIcon(starAddAvgIcon);
                } else {
                    gradeButton.setIcon(starAddIcon);
                }

            } else {
                gradeButton.setIcon(starRemoveIcon);
            }
        }
    }

    public List<GradeButton> getGrades() {
        return grades;
    }

    public void setCurrentGrade(int currentGrade) {
        this.isAverageGrade = false;
        this.currentGrade = currentGrade;
        select(this.currentGrade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            ElementAssociationRow row = ElementsAssociation.findElement(user.getId(), this.id, this.type);
            updateState(row);
        } else {
            this.select(0);
            this.currentGrade = 0;
        }
    }

    public MarvelElement getType() {
        return type;
    }

    public String getElementName() {
        return elementName;
    }

    public void setComic(@NotNull Comic comic) {
        this.elementName = comic.getTitle();
        this.type = MarvelElement.COMIC;
        this.setId(comic.getId());
    }

    public void setCharacter(@NotNull Character character) {
        this.id = character.getId();
        this.elementName = character.getName();
        this.type = MarvelElement.CHARACTER;
        this.setId(character.getId());
    }


    public void setComic(Comic comic, ElementAssociationRow row) {
        this.type = MarvelElement.COMIC;
        this.elementName = comic.getTitle();
        this.id = comic.getId();
        updateState(row);
    }

    public void setCharacter(Character character, ElementAssociationRow row) {
        this.type = MarvelElement.CHARACTER;
        this.elementName = character.getName();
        this.id = character.getId();
        updateState(row);
    }

    private void updateState(ElementAssociationRow row) {
        if (row != null) {
            this.isAverageGrade = false;
            this.select(row.getGrade());
            this.currentGrade = row.getGrade();
        } else {
            final int avgGrade = ElementsAssociation.getAverageGradeByComic(id);
            this.isAverageGrade = true;
            this.select(avgGrade);
            this.currentGrade = avgGrade;
        }
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
