package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.ResultSet;

public class MainScreenController {
    public Button restaurantButton;
    public Button peopleButton;
    public Label searchforLabel;
    public MenuButton areaButton;
    Main main;
    public Button findButton;
    public TextField findField;
    public Button logoutButton;
    public Label usernameLabel;
    public RadioButton sortByRatingButton;
    public RadioButton sortByPopularityButton;
    public RadioButton sortByDistanceButton;
    public MenuButton menuButton;
    public MenuItem m0;
    public MenuItem m1;
    public MenuItem m2;
    public MenuItem m3;
    public MenuItem m4;
    public MenuButton filterButton;
    public RadioMenuItem fm1;
    public RadioMenuItem fm2;
    public RadioMenuItem fm3;
    public RadioMenuItem fm4;
    public RadioMenuItem fm5;
    public RadioMenuItem fm6;
    public RadioMenuItem fm7;
    public RadioMenuItem fm8;
    public RadioMenuItem fm9;
    public RadioMenuItem fm10;
    public RadioMenuItem fm11;
    public RadioMenuItem fm12;
    public Label sortLabel;
    public Button listButton;
    public Button createListButton;
    int sortingMethod = 1;
    int priceRange = 0;
    int visited = 0;
    boolean liked;
    boolean listed;
    boolean lbpif;
    boolean wifi;
    boolean takeout;
    boolean delivery;
    boolean outdoorSeating;
    boolean reservation;
    boolean creditCard;
    boolean parking;
    boolean sfr = true; //search for restaurant?
    public String userArea = "none";

    EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("handling");
            priceRangeSelect(e);
        }
    };

    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            sortingMethodSelect(event);
        }
    };

    EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            filterSelect(event);
        }
    };

    void setMain(Main main){
        this.main = main;
    }

    void initialize() {
        userArea = "none";
        areaButton.setText("Your Location");
        sortByRatingButton.setSelected(true);
        sortByPopularityButton.setSelected(false);
        sortByDistanceButton.setSelected(false);
        sfr = true;
        sortingMethod = 1;
        priceRange = 0;
        visited = 0;
        liked = false;
        listed = false;
        lbpif = false;
        wifi = false;
        takeout = false;
        delivery = false;
        outdoorSeating = false;
        reservation = false;
        creditCard = false;
        parking = false;
        fm1.setSelected(false);
        fm2.setSelected(false);
        fm3.setSelected(false);
        fm4.setSelected(false);
        fm5.setSelected(false);
        fm6.setSelected(false);
        fm7.setSelected(false);
        fm8.setSelected(false);
        fm9.setSelected(false);
        fm10.setSelected(false);
        fm11.setSelected(false);
        fm12.setSelected(false);
        menuButton.setText("Price Range");
        peopleButton.setDisable(false);
        restaurantButton.setDisable(true);
        sortByRatingButton.setDisable(false);
        sortByRatingButton.setVisible(true);
        sortByDistanceButton.setVisible(true);
        sortByDistanceButton.setDisable(false);
        sortByPopularityButton.setDisable(false);
        sortByPopularityButton.setVisible(true);
        areaButton.setDisable(false);
        areaButton.setVisible(true);
        menuButton.setDisable(false);
        menuButton.setVisible(true);
        filterButton.setVisible(true);
        filterButton.setDisable(false);
        sortLabel.setVisible(true);
        restaurantButton.setTextFill(Color.GREEN);
        peopleButton.setTextFill(Color.BLACK);
    }

    void setUp() {
        usernameLabel.setText("Logged in as: " + main.username);
        // add action events to the menuitems
        sortByRatingButton.setOnAction(event2);
        sortByPopularityButton.setOnAction(event2);
        sortByDistanceButton.setOnAction(event2);
        m0.setOnAction(event1);
        m1.setOnAction(event1);
        m2.setOnAction(event1);
        m3.setOnAction(event1);
        m4.setOnAction(event1);
        fm1.setOnAction(event3);
        //fm1.setSelected(false);
        fm2.setOnAction(event3);
        //fm2.setSelected(false);
        fm3.setOnAction(event3);
        //fm3.setSelected(false);
        fm4.setOnAction(event3);
        //fm4.setSelected(false);
        fm5.setOnAction(event3);
        //fm5.setSelected(false);
        fm6.setOnAction(event3);
        //fm6.setSelected(false);
        fm7.setOnAction(event3);
        //fm7.setSelected(false);
        fm8.setOnAction(event3);
        //fm8.setSelected(false);
        fm9.setOnAction(event3);
        //fm9.setSelected(false);
        fm10.setOnAction(event3);
        //fm10.setSelected(false);
        fm11.setOnAction(event3);
        //fm11.setSelected(false);
        fm12.setOnAction(event3);
        //fm12.setSelected(false);
    }

    public void find() throws Exception{
        String text = findField.getText();
        System.out.println(text);
       // main.showResultScreen(text);
    }

    public void find2() throws  Exception {
        PGConnector pg = new PGConnector();
        if(sfr) {
            ResultSet rs = pg.findRestaurant(findField.getText(),sortingMethod,priceRange,
                    visited,liked,listed,lbpif,wifi,takeout,delivery,outdoorSeating,reservation,
                    creditCard,parking,main.username,userArea);
            main.showResultScreen(rs);
        }
        else {
            main.showPeopleScreen(findField.getText(),"search");
            findField.clear();
        }
    }

    public void logOut() throws Exception {
        findField.clear();
        main.showLoginScreen();
    }

    public void sortingMethodSelect(ActionEvent e) {
        if(e.getSource().equals(sortByRatingButton)) {
            sortingMethod = 1;
            sortByRatingButton.setSelected(true);
            sortByPopularityButton.setSelected(false);
            sortByDistanceButton.setSelected(false);
        }
        else if(e.getSource().equals(sortByPopularityButton)) {
            sortingMethod = 2;
            sortByRatingButton.setSelected(false);
            sortByPopularityButton.setSelected(true);
            sortByDistanceButton.setSelected(false);
        }
        else {
            sortingMethod = 3;
            sortByRatingButton.setSelected(false);
            sortByPopularityButton.setSelected(false);
            sortByDistanceButton.setSelected(true);
        }
    }

    public void priceRangeSelect(ActionEvent e) {
        if((e.getSource()).equals(m1)) {
            priceRange = 1;
            menuButton.setText("Low");
        }
        else if(e.getSource().equals(m2)) {
            priceRange = 2;
            menuButton.setText("Medium");
        }
        else if(e.getSource().equals(m3)) {
            priceRange = 3;
            menuButton.setText("High");
        }
        else if(e.getSource().equals(m4)) {
            priceRange = 4;
            menuButton.setText("Very High");
        }
        else {
            priceRange = 0;
            menuButton.setText("Price Range");
        }
    }

    public void filterSelect(ActionEvent e) {
        if(e.getSource().equals(fm1)) {
            visited = (visited == 1)? 0 : 1;
            fm2.setSelected(false);
        }
        else if(e.getSource().equals(fm2)) {
            visited = (visited == 2)? 0 : 2;
            fm1.setSelected(false);
        }
        else if(e.getSource().equals(fm3)) {
            liked = !liked;
        }
        else if(e.getSource().equals(fm4)) {
            listed = !listed;
        }
        else if(e.getSource().equals(fm5)) {
            lbpif = !lbpif;
        }
        else if(e.getSource().equals(fm6)) {
            wifi = !wifi;
        }
        else if(e.getSource().equals(fm7)) {
            takeout = !takeout;
        }
        else if(e.getSource().equals(fm8)) {
            delivery = !delivery;
        }
        else if(e.getSource().equals(fm9)) {
            outdoorSeating = !outdoorSeating;
        }
        else if(e.getSource().equals(fm10)) {
            reservation = !reservation;
        }
        else if(e.getSource().equals(fm11)) {
            creditCard = !creditCard;
        }
        else if(e.getSource().equals(fm12)) {
            parking = !parking;
        }
    }

    public void select(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(peopleButton)) {
            sfr = false;
            peopleButton.setDisable(true);
            restaurantButton.setTextFill(Color.BLACK);
            peopleButton.setTextFill(Color.GREEN);
            restaurantButton.setDisable(false);
            areaButton.setDisable(true);
            areaButton.setVisible(false);
            sortByRatingButton.setDisable(true);
            sortByRatingButton.setVisible(false);
            sortByDistanceButton.setVisible(false);
            sortByDistanceButton.setDisable(true);
            sortByPopularityButton.setDisable(true);
            sortByPopularityButton.setVisible(false);
            menuButton.setDisable(true);
            menuButton.setVisible(false);
            filterButton.setVisible(false);
            filterButton.setDisable(true);
            sortLabel.setVisible(false);
        }
        else {
            restaurantButton.setTextFill(Color.GREEN);
            peopleButton.setTextFill(Color.BLACK);
            sfr = true;
            areaButton.setDisable(false);
            areaButton.setVisible(true);
            peopleButton.setDisable(false);
            restaurantButton.setDisable(true);
            sortByRatingButton.setDisable(false);
            sortByRatingButton.setVisible(true);
            sortByDistanceButton.setVisible(true);
            sortByDistanceButton.setDisable(false);
            sortByPopularityButton.setDisable(false);
            sortByPopularityButton.setVisible(true);
            menuButton.setDisable(false);
            menuButton.setVisible(true);
            filterButton.setVisible(true);
            filterButton.setDisable(false);
            sortLabel.setVisible(true);
        }
    }

    public void showList() throws Exception {
        main.showListScreen("MainScreen", main.username);
    }

    public void createList() throws Exception  {
        main.showCreateListScreen();
    }

    public void areaSelect(ActionEvent actionEvent) {
        areaButton.setText(((MenuItem)actionEvent.getSource()).getText());
        userArea = ((MenuItem)actionEvent.getSource()).getText();
    }
}
