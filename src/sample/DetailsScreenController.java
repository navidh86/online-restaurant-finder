package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.math.BigDecimal;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class DetailsScreenController {
    public Label nameLabel;
    public Label areaLabel;
    public Label ratingLabel;
    public Label contactLabel;
    public Label prangeLabel;
    public Label wifiLabel;
    public Label takeoutLabel;
    public Label deliveryLabel;
    public Label outseatLabel;
    public Label reservationLabel;
    public Label creditCardLabel;
    public Label parkingLabel;
    public Button backButton;
    public Label likeLabel;
    public Label visitLabel;
    public Button likeButton;
    public Button visitButton;
    public Slider ratingSlider;
    public Button rateButton;
    public Button reviewButton;
    public TextArea reviewBox;
    public Button addButton;
    public Button cancelButton;
    public Button showReviewButton;
    public Button listButton;
    public Button addButton2;
    public Button cancelButton2;
    public TextField listField;
    public Label listLabel;
    public Label resultLabel;
    String lastPage;
    PGConnector pgConnector;
    Main main;
    String rname;

    public void setMain(Main main) {
        this.main = main;
    }

    public void goBack(ActionEvent actionEvent) throws Exception{
        if(lastPage.equals("ResultScreen"))
            main.showResultScreen("null");
        else main.showMainScreen(true);
    }

    public void like(ActionEvent actionEvent) throws Exception{
        pgConnector.customInsert("likes", main.username, nameLabel.getText(), 0);
        if(likeButton.getText().equals("Like"))
            likeButton.setText("Unlike");
        else likeButton.setText("Like");
    }

    public void visit(ActionEvent actionEvent) throws Exception {
        if(visitButton.getText().equals("Check In")) {
            pgConnector.customInsert("visits", main.username, nameLabel.getText(), 0);
            visitButton.setText("Checked In");
        }
    }

    public void rate() throws Exception {
        //System.out.println("rated " + ratingSlider.getValue());
        pgConnector.customInsert("rates", main.username, nameLabel.getText(), (int)ratingSlider.getValue());
        rateButton.setText("Rated");
    }

    public void takeReview() {
        rateButton.setVisible(false);
        rateButton.setDisable(true);
        ratingSlider.setVisible(false);
        ratingSlider.setDisable(true);
        reviewBox.setVisible(true);
        reviewBox.setDisable(false);
        addButton.setDisable(false);
        addButton.setVisible(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        showReviewButton.setDisable(true);
        showReviewButton.setVisible(false);
        listButton.setVisible(false);
        listButton.setDisable(true);
    }

    public void addReview() throws Exception{
        String review = reviewBox.getText();
        if(review != null) {
            System.out.println("review found");
            pgConnector.addReview(nameLabel.getText(), main.username, reviewBox.getText());
        }
        reviewBox.clear();
        rateButton.setVisible(true);
        rateButton.setDisable(false);
        ratingSlider.setVisible(true);
        ratingSlider.setDisable(false);
        reviewBox.setVisible(false);
        reviewBox.setDisable(true);
        addButton.setDisable(true);
        addButton.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        showReviewButton.setDisable(false);
        showReviewButton.setVisible(true);
        listButton.setVisible(true);
        listButton.setDisable(false);
    }

    public void cancel() {
        System.out.println("cancelled");
        reviewBox.clear();
        rateButton.setVisible(true);
        rateButton.setDisable(false);
        ratingSlider.setVisible(true);
        ratingSlider.setDisable(false);
        reviewBox.setVisible(false);
        reviewBox.setDisable(true);
        addButton.setDisable(true);
        addButton.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        showReviewButton.setDisable(false);
        showReviewButton.setVisible(true);
        listButton.setVisible(true);
        listButton.setDisable(false);
    }

    public void initialize(String rname, String lastPage) throws Exception{
        if(pgConnector == null)
            pgConnector = new PGConnector();
        this.lastPage = lastPage;
        this.rname = rname;
        listButton.setDisable(false);
        listButton.setVisible(true);
        resultLabel.setVisible(false);
        reviewBox.setDisable(true);
        reviewBox.setVisible(false);
        addButton.setDisable(true);
        addButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
        listField.setVisible(false);
        listField.setDisable(true);
        addButton2.setDisable(true);
        addButton2.setVisible(false);
        cancelButton2.setDisable(true);
        cancelButton2.setVisible(false);
        listLabel.setVisible(false);
        ResultSet rs = pgConnector.getRestaurantDetails(rname);
        while(rs.next()) {
            nameLabel.setText(rname);
            areaLabel.setText("Area: " + rs.getString("area"));
            System.out.println("Testing: " + rs.getString("rating"));
            String rating;
            if(rs.getString("rating") != null) {
                DecimalFormat df = new DecimalFormat("##.##");
                rating = rs.getString("rating");
                rating = df.format(Float.valueOf(rating)) + "";
            }
            else rating = "NA";
            ratingLabel.setText("Rating: " + rating);
            String contact = rs.getString("contact_number");
            if(contact == null)
                contact = "NA";
            contactLabel.setText("Contact No.: " + contact);
            String temp;
            temp = rs.getString("price_range");
            if(temp.equals("1"))
                prangeLabel.setText("Price Range: " + "Low");
            else if(temp.equals("2"))
                prangeLabel.setText("Price Range: " + "Medium");
            else if(temp.equals("3"))
                prangeLabel.setText("Price Range: " + "High");
            else prangeLabel.setText("Price Range: " + "Very High");
            temp = rs.getString("wifi");
            if(temp.equals("t"))
                wifiLabel.setText("Has wifi");
            else wifiLabel.setText("Does not have wifi");
            temp = rs.getString("takeout");
            if(temp.equals("t"))
                takeoutLabel.setText("Has takeout");
            else takeoutLabel.setText("Does not have takeout");
            temp = rs.getString("delivery");
            if(temp.equals("t"))
                deliveryLabel.setText("Has delivery");
            else deliveryLabel.setText("Does not have delivery");
            temp = rs.getString("outdoor_seating");
            if(temp.equals("t"))
                outseatLabel.setText("Has outdoor seating");
            else outseatLabel.setText("Does not have outdoor seating");
            temp = rs.getString("reservation");
            if(temp.equals("t"))
                reservationLabel.setText("Takes reservation");
            else reservationLabel.setText("Does not take reservation");
            temp = rs.getString("credit_card");
            if(temp.equals("t"))
                creditCardLabel.setText("Takes credit cards");
            else creditCardLabel.setText("Does not take credit cards");
            temp = rs.getString("parking");
            if(temp.equals("t"))
                parkingLabel.setText("Has parking");
            else parkingLabel.setText("Does not have parking");
            temp = rs.getString("likes");
            likeLabel.setText("Likes: " + temp);
            temp = rs.getString("visits");
            visitLabel.setText("Visited by " + temp + " people");
        }
        if(pgConnector.doesLike(rname, main.username))
            likeButton.setText("Unlike");
        else likeButton.setText("Like");
        visitButton.setText("Check In");

        ratingSlider.setMin(1);
        ratingSlider.setMax(10);
        ratingSlider.setValue(1);
        ratingSlider.setValue(pgConnector.getRatingGiven(rname, main.username));
        ratingSlider.setShowTickLabels(true);
        ratingSlider.setShowTickMarks(true);
        ratingSlider.setMajorTickUnit(1);
        ratingSlider.setMinorTickCount(0);
        ratingSlider.setBlockIncrement(10);
        ratingSlider.setSnapToTicks(true);
        ratingSlider.setDisable(false);
        ratingSlider.setVisible(true);
        rateButton.setDisable(false);
        rateButton.setVisible(true);
        showReviewButton.setDisable(false);
        showReviewButton.setVisible(true);
    }

    public void showReviews(ActionEvent actionEvent) throws Exception {
        main.showReviewScreen(this.rname);
    }

    public void takeListName(ActionEvent actionEvent) {
        listLabel.setVisible(true);
        listField.setVisible(true);
        listField.setDisable(false);
        addButton2.setDisable(false);
        addButton2.setVisible(true);
        cancelButton2.setVisible(true);
        cancelButton2.setDisable(false);
        listButton.setDisable(true);
        listButton.setVisible(false);
        resultLabel.setVisible(false);
    }

    public void addToList(ActionEvent actionEvent) throws Exception{
        PGConnector pgConnector = new PGConnector();
        if(!listField.getText().equals("")) {
            ResultSet rs = pgConnector.enlist(rname, listField.getText(), main.username, false);
            resultLabel.setVisible(true);
            rs.next();
            if(rs.getString(1).equals("t")) {
                resultLabel.setText("Added");
                resultLabel.setTextFill(Color.GREEN);
            }
            else {
                resultLabel.setText("Failed");
                resultLabel.setTextFill(Color.RED);
            }
        }
        listField.clear();
        listLabel.setVisible(false);
        listField.setVisible(false);
        listField.setDisable(true);
        addButton2.setDisable(true);
        addButton2.setVisible(false);
        cancelButton2.setVisible(false);
        cancelButton2.setDisable(true);
        listButton.setDisable(false);
        listButton.setVisible(true);
    }

    public void cancel2(ActionEvent actionEvent) {
        listField.clear();
        listLabel.setVisible(false);
        listField.setVisible(false);
        listField.setDisable(true);
        addButton2.setDisable(true);
        addButton2.setVisible(false);
        cancelButton2.setVisible(false);
        cancelButton2.setDisable(true);
        listButton.setDisable(false);
        listButton.setVisible(true);
    }
}
