package fr.tse.fise2.heapoverflow.controllers;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.gui.MarvelListElement;
import fr.tse.fise2.heapoverflow.gui.UILibrary;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryController {
    private static LibraryController singleton;
    private UILibrary uiLibrary;
    private ActionListener libFavListener;
    private ActionListener libReadListener;
    private ActionListener libUnOwnListener;
    private ActionListener libUnReadListener;
    private ActionListener favOwnListener;
    private ActionListener favReadListener;
    private ActionListener favUnFavListener;
    private ActionListener favUnReadListener;

    private LibraryController() {
        libFavListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getLibList().getSelectedValue();
                    ElementsAssociation.updateFavoriteCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), true, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        libReadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getLibList().getSelectedValue();
                    ElementsAssociation.updateReadCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), true, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        favReadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getFavList().getSelectedValue();
                    ElementsAssociation.updateReadCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), true, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        favOwnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getFavList().getSelectedValue();
                    ElementsAssociation.updateOwnedCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), true);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        favUnFavListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getFavList().getSelectedValue();
                    ElementsAssociation.updateFavoriteCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), false, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        libUnReadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getLibList().getSelectedValue();
                    ElementsAssociation.updateReadCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), false, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        favUnReadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getFavList().getSelectedValue();
                    ElementsAssociation.updateReadCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), false, MarvelElement.COMIC);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };

        libUnOwnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = UserAuthenticationModel.getUser();
                if (user != null) {
                    MarvelListElement selected = uiLibrary.getLibList().getSelectedValue();
                    ElementsAssociation.updateOwnedCreateAsNeeded(Integer.valueOf(selected.getShortURI()), selected.getDispName(), user.getId(), false);
                }
                uiLibrary.refreshFav();
                uiLibrary.refreshLib();
            }
        };
    }

    public static LibraryController getController() {
        if (singleton == null) {
            singleton = new LibraryController();
        }
        return singleton;
    }

    public UILibrary getUiLibrary() {
        return uiLibrary;
    }

    public void setUiLibrary(UILibrary uiLibrary) {
        if (uiLibrary != null) {
            if (this.uiLibrary != null) {
                this.uiLibrary.getFavBtnOwn().removeActionListener(favOwnListener);
                this.uiLibrary.getFavBtnRead().removeActionListener(favReadListener);
                this.uiLibrary.getFavBtnUnFav().removeActionListener(favUnFavListener);
                this.uiLibrary.getFavBtnUnRead().removeActionListener(favUnReadListener);

                this.uiLibrary.getLibBtnFav().removeActionListener(libFavListener);
                this.uiLibrary.getLibBtnRead().removeActionListener(libReadListener);
                this.uiLibrary.getLibBtnUnOwn().removeActionListener(libUnOwnListener);
                this.uiLibrary.getLibBtnUnRead().removeActionListener(libUnReadListener);
            }

            this.uiLibrary = uiLibrary;

            uiLibrary.getFavBtnOwn().addActionListener(favOwnListener);
            uiLibrary.getFavBtnRead().addActionListener(favReadListener);
            uiLibrary.getFavBtnUnFav().addActionListener(favUnFavListener);
            uiLibrary.getFavBtnUnRead().addActionListener(favUnReadListener);

            uiLibrary.getLibBtnFav().addActionListener(libFavListener);
            uiLibrary.getLibBtnRead().addActionListener(libReadListener);
            uiLibrary.getLibBtnUnOwn().addActionListener(libUnOwnListener);
            uiLibrary.getLibBtnUnRead().addActionListener(libUnReadListener);
        }
    }
}
