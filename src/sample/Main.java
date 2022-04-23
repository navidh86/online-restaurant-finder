package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.ResultSet;

public class Main extends Application {

    final static int SIZE = 15;  //used for the number of pages
    public Stage myStage;
    public Parent[] roots = new Parent[SIZE]; //all fxml files will be stored here
    public FXMLLoader[] loaders = new FXMLLoader[SIZE];
    Scene[] scenes = new Scene[SIZE];
    MainScreenController mainScreenController;
    ResultScreenController resultScreenController;
    LoginScreenController loginScreenController;
    DetailsScreenController detailsScreenController;
    ReviewScreenController reviewScreenController;
    UserScreenController userScreenController;
    PeopleScreenController peopleScreenController;
    ListScreenController listScreenController;
    ListDetailsScreenController listDetailsScreenController;
    CreateListScreenController createListScreenController;
    String username;

    @Override
    public void start(Stage primaryStage) throws Exception{
        myStage = primaryStage;
        myStage.setTitle("3Square");
        myStage.setResizable(false);
        //myStage.setOnCloseRequest(e -> closeProperly()); //close the program properly
        //showMainScreen(true);
        showLoginScreen();
    }

    void showLoginScreen() throws Exception {
        if(loaders[2] == null){
            loaders[2] = new FXMLLoader();
            loaders[2].setLocation(getClass().getResource("LoginScreen.fxml"));
            roots[2] = loaders[2].load();
            // Loading the controller
            loginScreenController = loaders[2].getController();
            loginScreenController.setMain(this);
        }
        loginScreenController.setUp();
        changeScene(2);
    }

    void showMainScreen(boolean initialize) throws Exception{
        if(loaders[0] == null){
            loaders[0] = new FXMLLoader();
            loaders[0].setLocation(getClass().getResource("MainScreen.fxml"));
            roots[0] = loaders[0].load();
            // Loading the controller
            mainScreenController = loaders[0].getController();
            mainScreenController.setMain(this);
        }
        if(initialize)
            mainScreenController.initialize();
        mainScreenController.setUp();
        changeScene(0);
    }

    void showResultScreen(String value) throws Exception {
        if(loaders[1] == null){
            loaders[1] = new FXMLLoader();
            loaders[1].setLocation(getClass().getResource("ResultScreen.fxml"));
            roots[1] = loaders[1].load();
            // Loading the controller
            resultScreenController = loaders[1].getController();
            resultScreenController.setMain(this);
            resultScreenController.results.refresh();
        }
        if(value != "null")
            resultScreenController.findRestaurant(value);
        changeScene(1);
    }

    void showResultScreen(ResultSet rs) throws Exception {
        if(loaders[1] == null){
            loaders[1] = new FXMLLoader();
            loaders[1].setLocation(getClass().getResource("ResultScreen.fxml"));
            roots[1] = loaders[1].load();
            // Loading the controller
            resultScreenController = loaders[1].getController();
            resultScreenController.setMain(this);
            resultScreenController.results.refresh();
        }
        resultScreenController.showResults(rs);
        changeScene(1);
    }

    void showDetailsScreen(String rname, String lastPage) throws Exception {
        System.out.println("will show for " + rname);
        if(loaders[3] == null){
            loaders[3] = new FXMLLoader();
            loaders[3].setLocation(getClass().getResource("DetailsScreen.fxml"));
            roots[3] = loaders[3].load();
            // Loading the controller
            detailsScreenController = loaders[3].getController();
            detailsScreenController.setMain(this);
        }
        detailsScreenController.initialize(rname, lastPage);
        changeScene(3);
    }

    void showReviewScreen(String rname) throws Exception {
        System.out.println("will show for " + rname);
        if(loaders[4] == null){
            loaders[4] = new FXMLLoader();
            loaders[4].setLocation(getClass().getResource("ReviewScreen.fxml"));
            roots[4] = loaders[4].load();
            // Loading the controller
            reviewScreenController = loaders[4].getController();
            reviewScreenController.setMain(this);
        }
        reviewScreenController.initialize(rname);
        changeScene(4);
    }

    void showUserScreen(String uname, String lastPage, String value) throws Exception {
        if(loaders[5] == null){
            loaders[5] = new FXMLLoader();
            loaders[5].setLocation(getClass().getResource("UserScreen.fxml"));
            roots[5] = loaders[5].load();
            // Loading the controller
            userScreenController = loaders[5].getController();
            userScreenController.setMain(this);
        }
        userScreenController.initialize(uname, lastPage);
        if(lastPage.equals("ReviewScreen"))
            userScreenController.rname = value;
        changeScene(5);
    }

    void showPeopleScreen(String uname, String type) throws Exception {
        if(loaders[6] == null){
            loaders[6] = new FXMLLoader();
            loaders[6].setLocation(getClass().getResource("PeopleScreen.fxml"));
            roots[6] = loaders[6].load();
            // Loading the controller
            peopleScreenController = loaders[6].getController();
            peopleScreenController.setMain(this);
        }
        peopleScreenController.initialize(uname, type);
        changeScene(6);
    }

    void showListScreen(String lastPage, String username) throws Exception {
        if(loaders[7] == null){
            loaders[7] = new FXMLLoader();
            loaders[7].setLocation(getClass().getResource("ListScreen.fxml"));
            roots[7] = loaders[7].load();
            // Loading the controller
            listScreenController = loaders[7].getController();
            listScreenController.setMain(this);
        }
        listScreenController.initialize(lastPage, username);
        changeScene(7);
    }

    void showListDetailsScreen(String listName, String username) throws Exception {
        if(loaders[8] == null){
            loaders[8] = new FXMLLoader();
            loaders[8].setLocation(getClass().getResource("ListDetailsScreen.fxml"));
            roots[8] = loaders[8].load();
            // Loading the controller
            listDetailsScreenController = loaders[8].getController();
            listDetailsScreenController.setMain(this);
        }
        listDetailsScreenController.initialize(listName, username);
        changeScene(8);
    }

    void showCreateListScreen() throws Exception {
        if(loaders[9] == null){
            loaders[9] = new FXMLLoader();
            loaders[9].setLocation(getClass().getResource("CreateListScreen.fxml"));
            roots[9] = loaders[9].load();
            // Loading the controller
            createListScreenController = loaders[9].getController();
            createListScreenController.setMain(this);
        }
        createListScreenController.initialize();
        changeScene(9);
    }

    //this method loads any scene
    public void changeScene(int i) {
        if(scenes[i] == null){
            scenes[i] = new Scene(roots[i],800,800);
        }
        myStage.setResizable(true);
        myStage.setScene(scenes[i]);
        myStage.show();
    }

    void setUser(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
