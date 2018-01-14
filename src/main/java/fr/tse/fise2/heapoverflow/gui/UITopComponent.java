package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.controllers.UserAuthenticationController;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;

class UITopComponent {
    private final JPanel topPanel;
    private final JPanel topRightPanel;
    private final UI ui;
    private UserAuthenticationView userAuthenticationView;


    UITopComponent(final UI ui, final JPanel topPanel) {
        this.ui = ui;


        this.topPanel = topPanel;
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setBorder(new BoxShadow());

        this.topRightPanel = new JPanel();


        this.configUserAuthenticationMVC();
    }

    private void configUserAuthenticationMVC() {
        UserAuthenticationModel model = UserAuthenticationModel.getInstance();
        UserAuthenticationController controller = new UserAuthenticationController(model);

        this.userAuthenticationView = new UserAuthenticationView(this.ui, model, controller);
        model.addObserver(this.userAuthenticationView);

    }

    public void init() {
        this.setSize();
        this.build();
    }


    private void setSize() {
        final Dimension minDimension = new Dimension(200, 50);
        this.topPanel.setMinimumSize(minDimension);
        this.topPanel.setPreferredSize(minDimension);
    }

    private void build() {

        this.topPanel.setLayout(new BorderLayout());

        // top right panel
        this.topRightPanel.setLayout(new BorderLayout());
        this.topPanel.add(this.topRightPanel, BorderLayout.WEST);

        // shadow
        this.topPanel.add(this.userAuthenticationView, BorderLayout.EAST);
    }
}