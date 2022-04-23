package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.sql.ResultSet;

public class ListDetailsScreenController {
    public Button backButton;
    public Label nameLabel;
    public TextArea descriptionBox;
    public ListView restaurants;
    public Button privacyButton;
    public Button removeButton;
    public Button detailsButton;
    String username;
    String listName;
    Main main;

    public void initialize(String listName, String username) throws Exception{
        this.listName = listName;
        this.username = username;
        nameLabel.setText(listName + " (" + username + ")");
        PGConnector pgConnector = new PGConnector();
        ResultSet rs = pgConnector.getListDetails(listName, username);
        rs.next();
        descriptionBox.clear();
        descriptionBox.setText(rs.getString("description"));
        if(!username.equals(main.username)) {
            privacyButton.setDisable(true);
            privacyButton.setVisible(false);
            removeButton.setDisable(true);
            removeButton.setVisible(false);
        }
        else {
            privacyButton.setVisible(true);
            privacyButton.setDisable(false);
            removeButton.setVisible(true);
            removeButton.setDisable(false);
            if(rs.getString("is_private").equals("t")) {
                privacyButton.setText("Make Public");
            }
            else privacyButton.setText("Make Private");
        }
        rs = pgConnector.getListedRestaurants(listName, username);
        restaurants.getItems().clear();
        while(rs.next()) {
            restaurants.getItems().add((String)rs.getString(1));
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void remove(ActionEvent actionEvent) throws Exception{
        String rname = (String)restaurants.getSelectionModel().getSelectedItem();
        if(rname != null) {
            PGConnector pgConnector = new PGConnector();
            pgConnector.enlist(rname, listName, username, true);
        }
    }

    public void changePrivacy(ActionEvent actionEvent) throws Exception {
        PGConnector pgConnector = new PGConnector();
        boolean value;
        if(privacyButton.getText().equals("Make Private")) {
            privacyButton.setText("Make Public");
            value = true;
        }
        else {
            privacyButton.setText("Make Private");
            value = false;
        }
        pgConnector.changePrivacy(listName, username, value);
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        main.showListScreen("MainScreen", this.username);
    }

    public void showDetails() throws Exception{
        String rname = (String)restaurants.getSelectionModel().getSelectedItem();
        if(rname != null) {
            main.showDetailsScreen(rname, "MainScreen");
        }
    }
}
