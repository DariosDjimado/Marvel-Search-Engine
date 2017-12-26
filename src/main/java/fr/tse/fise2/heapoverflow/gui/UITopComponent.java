package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.controllers.UserAuthenticationController;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import java.awt.*;

public class UITopComponent {

    private final JButton libraryButton;
    private final JPanel topPanel;
    private final JButton createCollectionButton;
    private final JPanel topRightPanel;
    private final UI ui;
    private final JButton commentButton;
    private UserAuthenticationView userAuthenticationView;


    UITopComponent(final UI ui, final JPanel topPanel) {
        this.libraryButton = new AccentButton("My library");
        this.ui = ui;


        this.topPanel = topPanel;
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setBorder(new BoxShadow());

        this.createCollectionButton = new DefaultButton("Create Collections");
        this.topRightPanel = new JPanel();

        this.commentButton = new CommentButton();

        this.configUserAuthenticationMVC();
        this.commentButtonActionListener();
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


    public void setSize() {
        final Dimension minDimension = new Dimension(200, 50);
        this.topPanel.setMinimumSize(minDimension);
        this.topPanel.setPreferredSize(minDimension);
    }

    private void build() {

        this.topPanel.setLayout(new BorderLayout());

        // top right panel
        this.createCollectionButton.setForeground(UIColor.PRIMARY_COLOR);

        this.commentButton.setContentAreaFilled(true);
        this.commentButton.setBackground(UIColor.HEADER_TEXT_COLOR);

        this.topRightPanel.setLayout(new BorderLayout());
        this.topRightPanel.add(this.libraryButton, BorderLayout.WEST);
        this.topRightPanel.add(this.createCollectionButton, BorderLayout.CENTER);
        this.topRightPanel.add(this.commentButton, BorderLayout.EAST);
        this.topPanel.add(this.topRightPanel, BorderLayout.WEST);


        // shadow
        this.topPanel.add(this.userAuthenticationView, BorderLayout.EAST);


    }

    private void commentButtonActionListener() {
        this.commentButton.addActionListener(e -> {
            final CustomDialog dialog = new CustomDialog(this.ui, "Comment", true);
            final CustomTextArea descArea = new CustomTextArea("Comment");

            JScrollPane scrollPane = new JScrollPane(descArea);
            scrollPane.setBorder(null);

            JPanel buttonsPanel = new JPanel();
            JButton cancelButton = new DefaultButton("cancel");
            cancelButton.addActionListener(event -> dialog.dispose());
            buttonsPanel.add(cancelButton);
            JButton saveButton = new PrimaryButton("save");
            buttonsPanel.add(saveButton);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(UIColor.DEFAULT_COLOR);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setMinimumSize(new Dimension(300, 200));
            mainPanel.setPreferredSize(new Dimension(300, 200));
            mainPanel.setMaximumSize(new Dimension(300, 200));
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

            dialog.add(mainPanel);
            dialog.customSetVisible();
        });
    }

    public UserAuthenticationView getUserAuthenticationView() {
        return userAuthenticationView;
    }

    public JButton getCreateCollectionButton() {
        return createCollectionButton;
    }

    public JButton getCommentButton() {
        return commentButton;
    }
}