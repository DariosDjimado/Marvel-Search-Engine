package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.util.List;

public class LibraryView extends FavoriteView {
    private static final ImageIcon EMPTY_STATE_IMAGE_ICON = new ImageIcon(LibraryView.class.getResource("folder.png"));

    LibraryView() {
        super();
        this.initEmptyState("Your library is empty", EMPTY_STATE_IMAGE_ICON);
    }

    @Override
    public void refresh() {
        this.comicsListModel.clear();

        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            List<ElementAssociationRow> elementAssociationRows = ElementsAssociation.findOwnedComicsByUser(user.getId());
            this.toggleState(elementAssociationRows.size() != 0);
            CollectionsView.fillList(elementAssociationRows, comicsListModel);
        }
    }
}
