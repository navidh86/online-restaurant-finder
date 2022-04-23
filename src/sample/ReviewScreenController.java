package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReviewScreenController {
    public Button backButton;
    public TableView reviewTable;
    public TableColumn usernameColumn;
    public TableColumn commentColumn;
    public TableColumn dateColumn;
    public Button seeUserButton;
    Main main;
    String rname;

    public void setMain(Main main) {
        this.main = main;
    }

    public void seeUser(ActionEvent actionEvent) throws Exception {
        Review review = (Review) reviewTable.getSelectionModel().getSelectedItem();
        if(review != null) {
            System.out.println("show detail for " + review.getUsername());
            main.showUserScreen(review.getUsername(), "ReviewScreen", rname);
        }
    }

    public static class Review {
        private final SimpleStringProperty username;
        private final SimpleStringProperty comment;
        private final SimpleStringProperty date;

        public Review(String uName, String comment, String date) {
            this.username = new SimpleStringProperty(uName);
            this.comment = new SimpleStringProperty(comment);
            this.date = new SimpleStringProperty(date);
        }

        public String getUsername() {
            return username.get();
        }
        public void setUsername(String uName) {
            username.set(uName);
        }

        public String getComment() {
            return comment.get();
        }
        public void setComment(String c) {
            comment.set(c);
        }

        public String getDate() {
            return date.get();
        }
        public void setDate(String d) {
            date.set(d);
        }
    }

    public void initialize(String rname) throws Exception {
        System.out.println("wil show reveiws for " + rname);
        final ObservableList<Review> data;
        PGConnector pgConnector = new PGConnector();
        data = pgConnector.getReviews(rname);
        this.rname = rname;

        usernameColumn.setCellValueFactory(
                new PropertyValueFactory<Review,String>("username")
        );
        commentColumn.setCellValueFactory(
                new PropertyValueFactory<Review,String>("comment")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Review,String>("date")
        );
        reviewTable.setItems(data);
    }

    public void goBack() throws Exception{
        main.showDetailsScreen(rname, "MainScreen");
    }
}
