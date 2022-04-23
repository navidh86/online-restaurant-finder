package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class CreateListScreenController {
    public Button backButton;
    public TextField nameField;
    public TextArea descriptionBox;
    public MenuButton privacyButton;
    public MenuItem publicMenu;
    public MenuItem privateMenu;
    public Button createButton;
    public Label errorLabel;
    boolean isPrivate = false;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void goBack(ActionEvent actionEvent) throws Exception{
        main.showMainScreen(true);
    }

    public void selectPrivacy(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(privateMenu)) {
            isPrivate = true;
            privacyButton.setText("Private");
        }
        else {
            isPrivate = false;
            privacyButton.setText("Public");
        }
    }

    public void initialize() {
        isPrivate = false;
        errorLabel.setText("");
        createButton.setText("Create");
        nameField.clear();
        descriptionBox.clear();
    }

    public void create(ActionEvent actionEvent) throws Exception {
        PGConnector pgConnector = new PGConnector();
        if(!nameField.getText().equals("")) {
            boolean flag = pgConnector.createList(nameField.getText(), descriptionBox.getText(),
                    isPrivate, main.username);
            if(flag)
                createButton.setText("Created");
            else {
                errorLabel.setText("list already exists");
                errorLabel.setTextFill(Color.RED);
            }
        }
    }
}
