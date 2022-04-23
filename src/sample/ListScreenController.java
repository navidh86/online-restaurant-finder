package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.ResultSet;

public class ListScreenController {
    public Button backButton;
    public ListView lists;
    public Button detailsButton;
    public Label titleLabel;
    String lastPage;
    String username;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    void initialize(String lastPage, String username) throws Exception {
        titleLabel.setText("Lists of " + username);
        this.lastPage = lastPage;
        this.username = username;
        PGConnector pgConnector = new PGConnector();
        Boolean isMine = (username.equals(main.username));
        ResultSet rs = pgConnector.getLists(username, isMine); //true for my list
        lists.getItems().clear();
        while(rs.next()) {
            lists.getItems().add(rs.getString(1));
        }
    }

    public void goBack(ActionEvent actionEvent) throws Exception{
        if(lastPage.equals("MainScreen")) {
            main.showMainScreen(true);
        }
        else {
            main.showUserScreen(username, "MainScreen", "null");
        }
    }

    public void showDetails(ActionEvent actionEvent) throws Exception {
        if(lists.getSelectionModel().getSelectedItem() != null) {
            //if(lastPage.equals("MainScreen"))
                main.showListDetailsScreen((String)lists.getSelectionModel().getSelectedItem(), this.username);
        }
    }
}
