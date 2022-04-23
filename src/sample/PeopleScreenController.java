package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.ResultSet;

public class PeopleScreenController {
    String username;
    String type; //followers or following
    Main main;
    public Button backButton;
    public Button detailsButton;
    public ListView<String> people;
    public Label topLabel;

    public void goBack() throws Exception{
        if(!type.equals("search"))
            main.showUserScreen(username,"MainScreen","null");
        else main.showMainScreen(true);
    }

    public void showDetails() throws Exception {
        String temp = (String)people.getSelectionModel().getSelectedItem();
        if(temp != null)
            main.showUserScreen(temp, "MainScreen", "null");
    }

    public void initialize(String uname, String type) throws Exception {
        username = uname;
        this.type = type;
        people.getItems().clear();
        PGConnector pgConnector = new PGConnector();
        ResultSet rs;
        if(!type.equals("search"))
            rs = pgConnector.getPeople(username, type);
        else rs = pgConnector.getPeople(username);
        if(type.equals("followers"))
            topLabel.setText("Followers of " + username);
        else if (type.equals("following"))
            topLabel.setText("People followed by " + username);
        else topLabel.setText("");
        while(rs.next()) {
            people.getItems().add(rs.getString(1));
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
