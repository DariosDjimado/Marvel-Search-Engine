package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import java.util.List;

public class LibraryView extends FavoriteView {
    @Override
    protected void refresh() {
        this.comicsListModel.clear();

        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            List<ElementAssociationRow> elementAssociationRows = ElementsAssociation.findOwnedComicsByUser(user.getId());
            CollectionsView.fillList(elementAssociationRows, comicsListModel);
        }
    }
}
