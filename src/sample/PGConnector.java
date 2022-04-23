package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ReviewScreenController.Review;
import java.sql.*;

public class PGConnector {
    private final String url = "jdbc:postgresql://localhost/Restaurant Finder And Rating App Database";
    private final String user = "postgres";
    private final String password = "navid123";
    Connection connection;
    boolean isConnected = false;


    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            isConnected = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public ResultSet findRestaurant(String value) {
        if(!isConnected)
            connection = connect();
        ResultSet rs = null;
        String query = "select basic_search(?,1)";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, value);
            rs = pst.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet findRestaurant(String value, int ordering, int prange,
                                    int visited, boolean liked, boolean listed,
                                    boolean lbpif, boolean wifi, boolean takeout,
                                    boolean delivery, boolean outdoorSeating,
                                    boolean reservation, boolean creditCard,
                                    boolean parking, String username, String userArea) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "Select id from customer where username = ?";
        ResultSet rs1;
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        rs1 = pst.executeQuery();
        rs1.next();
        int uid = rs1.getInt(1);
        query = "Select main_search2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        pst = connection.prepareStatement(query);
        pst.setString(1, value);
        pst.setInt(2, ordering);
        pst.setInt(3, prange);
        pst.setInt(4, visited);
        pst.setBoolean(5, liked);
        pst.setBoolean(6, listed);
        pst.setBoolean(7, lbpif);
        pst.setBoolean(8, wifi);
        pst.setBoolean(9, takeout);
        pst.setBoolean(10, delivery);
        pst.setBoolean(11, outdoorSeating);
        pst.setBoolean(12, reservation);
        pst.setBoolean(13, creditCard);
        pst.setBoolean(14, parking);
        pst.setInt(15, uid);
        pst.setString(16, userArea);
        ResultSet rs2 = pst.executeQuery();
        return rs2;
    }

    public boolean verifyLogin(String username, String password) throws Exception {
        if(!isConnected)
            connection = connect();
        ResultSet rs = null;
        Boolean flag = false;
        String query = "Select count(*) from customer where username = ? and " +
                "password = MD5(?)";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        while(rs.next()) {
            int count = rs.getInt(1);
            if (count == 1)
                flag =  true;
            else flag = false;
            break;
        }
        return flag;
    }

    public boolean createAccount(String fullname, String username, String password) throws Exception {
        if(!isConnected)
            connection = connect();

        boolean flag = false;
        String query = "Select create_user(?,?,?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, fullname);
        pst.setString(3, password);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String ret = rs.getString(1);
            System.out.println(ret);
            if(ret.equals("t"))
                flag = true;
            else flag = false;
        }

        return flag;
    }

    public ResultSet getRestaurantDetails(String rname) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "Select * from restaurant where name = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    public boolean doesLike(String rname, String username) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select count(*) from likes l, customer c, restaurant r where" +
                " l.restaurant_id = r.id and l.user_id = c.id and r.name = ? and c.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        pst.setString(2, username);
        ResultSet rs = pst.executeQuery();
        rs.next();
        if(rs.getInt(1) > 0)
            return true;
        else return false;
    }

    public void customInsert(String tname, String val1, String val2, int val3) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select custom_insert(?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, tname);
        pst.setString(2, val1);
        pst.setString(3, val2);
        pst.setInt(4, val3);
        pst.executeQuery();
    }

    public int getRatingGiven(String rname, String username) throws Exception{
        if(!isConnected)
            connection = connect();
        int rating = 1;
        String query = "Select r.rating from rates r, restaurant rt, customer c " +
                "where r.restaurant_id = rt.id and r.user_id = c.id and " +
                "rt.name = ? and c.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        pst.setString(2, username);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            rating = rs.getInt(1);
        }

       return rating;
    }

    public void addReview(String rname, String username, String comment) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "Select add_review(?,?,?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        pst.setString(2, username);
        pst.setString(3, comment);
        pst.executeQuery();
    }

    public ObservableList<Review> getReviews(String rname) throws Exception{
        if(!isConnected)
            connection = connect();
        ObservableList<Review> data = FXCollections.observableArrayList();
        String query = "Select c.username, rv.comment, rv.date  from customer c,restaurant r, review rv " +
                "where r.id = rv.restaurant_id and r.name = ? and c.id = rv.user_id";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        ResultSet rs = pst.executeQuery();
        while(rs.next()) {
            data.add(new Review(rs.getString(1),rs.getString(2),
                    rs.getString(3)));
        }
        return data;
    }

    public String getUserDetails(String username) throws Exception{
        if(!isConnected)
            connection = connect();
        String details  = "";
        String query = "Select followers from customer c where c.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        rs.next();
        details += rs.getString(1);
        query = "Select count(*) from follows f, customer c where c.username = ? " +
                "and c.id = f.follower_id";
        pst = connection.prepareStatement(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        rs.next();
        details += " " + rs.getString(1);

        return details;
    }

    public boolean doesFollow(String follower, String followee) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select count(*) from follows f, customer c1, customer c2 " +
                "where f.follower_id = c1.id and f.followee_id = c2.id and " +
                "c1.username = ? and c2.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, follower);
        pst.setString(2, followee);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if(count > 0)
            return true;
        else return false;
    }

    public ResultSet getPeople(String username, String type) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "";
        if(type.equals("followers"))
            query = "Select c2.username from customer c1, customer c2, follows f where " +
                    "c1.id = f.followee_id and c2.id = follower_id and c1.username = ?";
        else query = "Select c2.username from customer c1, customer c2, follows f where " +
                "c1.id = f.follower_id and c2.id = followee_id and c1.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    public ResultSet getPeople(String value) throws Exception {
        if(!isConnected)
            connection = connect();
        value = value.toLowerCase();
        String query = "Select username from customer where lower(username) like " +
                "\'%" + value + "%\' or lower(full_name) like \'%" + value + "%\'" ;
        PreparedStatement pst = connection.prepareStatement(query);
        //pst.setString(1, value);
        return pst.executeQuery();
    }

    public ResultSet getLists(String username, boolean isMine) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select name from list l, customer c where l.user_id = c.id " +
                "and c.username = ? ";
        if(!isMine)
            query += "and l.is_private = false";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        return pst.executeQuery();
    }

    public ResultSet getListDetails(String listName, String username) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "Select * from list l, customer c where l.name = ? " +
                "and l.user_id = c.id and c.username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, listName);
        pst.setString(2, username);
        return pst.executeQuery();
    }

    public ResultSet getListedRestaurants(String listName, String username) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select r.name from list l, customer c, restaurant r, restaurant_is_in_list rl " +
                "where l.name = ? and l.user_id = c.id and c.username = ? and rl.list_id = l.id " +
                "and rl.restaurant_id = r.id";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, listName);
        pst.setString(2, username);
        return pst.executeQuery();
    }

    public void changePrivacy(String listName, String username, boolean privacy) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "update list set is_private = ? where name = ? and " +
                "user_id in (Select id from customer where username = ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setBoolean(1, privacy);
        pst.setString(2, listName);
        pst.setString(3, username);
        pst.executeUpdate();
    }

    public ResultSet enlist(String rname, String listName, String username, boolean remove) throws Exception{
        if(!isConnected)
            connection = connect();
        String query = "Select enlist(?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, rname);
        pst.setString(2, listName);
        pst.setString(3, username);
        pst.setBoolean(4, remove);
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    public boolean createList(String lname, String des, boolean privacy, String username) throws Exception {
        if(!isConnected)
            connection = connect();
        String query = "Select create_list(?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, lname);
        pst.setString(2, des);
        pst.setBoolean(3, privacy);
        pst.setString(4, username);
        ResultSet rs = pst.executeQuery();
        rs.next();
        if(rs.getString(1).equals("t"))
            return true;
        else return false;
    }
}
