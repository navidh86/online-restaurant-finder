package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.ResultSet;

public class UserScreenController {
    Main main;
    String username;
    String lastPage; //which page did we come here from
    public Button backButton;
    public Button followButton;
    public Label nameLabel;
    public Label followersLabel;
    public Label followingLabel;
    public Button listButton;
    public String rname;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showPeople(MouseEvent mouseEvent) throws Exception{
        if(mouseEvent.getSource().equals(followersLabel))
            main.showPeopleScreen(username, "followers");
        else main.showPeopleScreen(username, "following");
    }

    public void goBack() throws Exception{
        if(lastPage.equals("ReviewScreen")) {
            main.showReviewScreen(rname);
        }
        else main.showMainScreen(true);
    }

    public void follow() throws Exception{
        if(followButton.getText().equals("Follow"))
            followButton.setText("Unfollow");
        else followButton.setText("Follow");
        PGConnector pgConnector = new PGConnector();
        pgConnector.customInsert("follows", main.username, username, 0);
    }

    public void initialize(String uname, String lastPage) throws Exception{
        username = uname;
        nameLabel.setText(username);
        this.lastPage = lastPage;
        PGConnector pgConnector = new PGConnector();
        String details = pgConnector.getUserDetails(uname);
        String[] temp = details.split(" ");
        followersLabel.setText("Followers: " + temp[0]);
        followingLabel.setText("Following: " + temp[1]);
        if(username.equals(main.username)) {
            followButton.setVisible(false);
            followButton.setDisable(true);
        }
        else {
            followButton.setVisible(true);
            followButton.setDisable(false);
            boolean doesFollow = pgConnector.doesFollow(main.username, username);
            if(doesFollow)
                followButton.setText("Unfollow");
            else followButton.setText("Follow");
        }
    }

    public void seeLists() throws  Exception{
        main.showListScreen("UserScreen", username);
    }
}
