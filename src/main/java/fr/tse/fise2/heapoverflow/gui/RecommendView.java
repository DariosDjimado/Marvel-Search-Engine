package fr.tse.fise2.heapoverflow.gui;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RecommendView extends JPanel implements Observer {

    private DefaultListModel<Comic> bestRankModel;
    private DefaultListModel<Comic> mostFavModel;
    private DefaultListModel<Comic> nextToReadModel;

    private JList<Comic> bestRankList;
    private JList<Comic> mostFavList;
    private JList<Comic> nextToReadList;

    private final ComputeNextToReadThread cntrt = ComputeNextToReadThread.getInstance();

    private JPanel nextToReadPane;

    public RecommendView() {
        this.bestRankModel = new DefaultListModel<>();
        this.mostFavModel = new DefaultListModel<>();
        this.nextToReadModel = new DefaultListModel<>();

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel bestRankPane = new JPanel();
        bestRankPane.setLayout(new BoxLayout(bestRankPane, BoxLayout.PAGE_AXIS));
        JLabel bestRankTitle = new JLabel("Best ranked comics");
        bestRankTitle.setFont(Fonts.title2);
        bestRankPane.add(bestRankTitle);
        bestRankList = new JList<>(this.bestRankModel);
        bestRankList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bestRankList.setVisibleRowCount(-1);
        bestRankList.setLayoutOrientation(JList.VERTICAL);
        bestRankList.setCellRenderer(new ComicsListRenderer());
        bestRankPane.add(new CustomScrollPane(bestRankList));

        JPanel mostFavPane = new JPanel();
        mostFavPane.setLayout(new BoxLayout(mostFavPane, BoxLayout.PAGE_AXIS));
        JLabel mostFavTitle = new JLabel("Most faved comics");
        mostFavTitle.setFont(Fonts.title2);
        mostFavPane.add(mostFavTitle);
        mostFavList = new JList<>(this.mostFavModel);
        mostFavList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mostFavList.setVisibleRowCount(-1);
        mostFavList.setLayoutOrientation(JList.VERTICAL);
        mostFavList.setCellRenderer(new ComicsListRenderer());
        mostFavPane.add(new CustomScrollPane(mostFavList));

        nextToReadPane = new JPanel();
        nextToReadPane.setLayout(new BoxLayout(nextToReadPane, BoxLayout.PAGE_AXIS));
        JLabel nextToReadTitle = new JLabel("Next comics to read");
        nextToReadTitle.setFont(Fonts.title2);
        nextToReadPane.add(nextToReadTitle);
        nextToReadList = new JList<>(this.nextToReadModel);
        nextToReadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nextToReadList.setVisibleRowCount(-1);
        nextToReadList.setLayoutOrientation(JList.VERTICAL);
        nextToReadList.setCellRenderer(new ComicsListRenderer());
        nextToReadPane.add(new CustomScrollPane(nextToReadList));

        this.add(bestRankPane);
        this.add(mostFavPane);
        this.add(nextToReadPane);

        nextToReadPane.setVisible(false);
        UserAuthenticationModel.getInstance().addObserver(this);
        refresh();
    }

    public void refresh() {
        this.bestRankModel.clear();
        List<ElementAssociationRow> bestRankedRows = ElementsAssociation.findBestRankedComics(10);
        CollectionsView.fillList(bestRankedRows, bestRankModel);

        this.mostFavModel.clear();
        List<ElementAssociationRow> mostFavedRows = ElementsAssociation.findMostFavedComics(10);
        CollectionsView.fillList(mostFavedRows, mostFavModel);

        if (UserAuthenticationModel.isAuthencated()) {
            nextToReadPane.setVisible(true);
            this.nextToReadModel.clear();
            List<ElementAssociationRow> ReadRows = ElementsAssociation.findReadComicsByUser(UserAuthenticationModel.getUser().getId());
            nextToReadModel.clear();
            cntrt.refresh(ReadRows, nextToReadModel);
        } else {
            nextToReadPane.setVisible(false);
        }
    }

    public JList<Comic> getBestRankList() {
        return bestRankList;
    }

    public JList<Comic> getMostFavList() {
        return mostFavList;
    }

    public JList<Comic> getNextToReadList() {
        return nextToReadList;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        refresh();
    }
}
