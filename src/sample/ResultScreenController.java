package sample;

import com.sun.org.apache.xpath.internal.operations.Number;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.sql.ResultSet;
import java.util.List;

public class ResultScreenController {

    Main main;
    public Button detailButton;
    public ListView<String> results;
    public Button backButton;
    PGConnector pgConnector = null;

    void setMain(Main main){
        this.main = main;
    }

    public void goBack() throws Exception{
        main.showMainScreen(false);
    }

    public void findRestaurant(String value) throws  Exception{
        if(pgConnector == null)
            pgConnector = new PGConnector();
        ResultSet rs = pgConnector.findRestaurant(value);
        setValues(rs);
    }

    public void setValues(ResultSet rs) throws Exception{
        results.getItems().clear();
        while(rs.next()){
            String temp = rs.getString(1);
            String[] data = temp.split(",");
            temp = "\"";
            data[1] = data[1].replace("\"", "");
            data[3] = data[3].replace(")", "");
            temp += data[1] + "\"  ";
            temp += "Rating: ";
            Float f = Float.valueOf(data[2]);
            if (f == 0)
                temp += "NA" + "  ";
            else temp += f + "  ";
            temp += "Area: ";
            temp += data[3];
            results.getItems().add(temp);
        }
    }

    public void showResults(ResultSet rs) throws Exception {
        results.getItems().clear();
        while(rs.next()){
            String temp = rs.getString(1);
            String[] data = temp.split(",");
            temp = "\"";
            data[1] = data[1].replace("\"", "");
            data[3] = data[3].replace(")", "");
            temp += data[1] + "\"  ";
            temp += "Rating: ";
            Float f = Float.valueOf(data[2]);
            if (f == 0)
                temp += "NA" + "  ";
            else temp += f + "  ";
            temp += "Area: ";
            temp += data[3];
            results.getItems().add(temp);
        }
    }

    public void showDetails(ActionEvent actionEvent) throws Exception{
        if(results.getSelectionModel().getSelectedItem() != null) {
            String temp = (String)  results.getSelectionModel().getSelectedItem();
            String[] data = temp.split("\"");
            main.showDetailsScreen(data[1], "ResultScreen");
        }
    }
}
