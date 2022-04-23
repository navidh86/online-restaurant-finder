package sample;

import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class LoginScreenController {
    Main main;
    public Label fullNameLabel;
    public TextField fullNameTF;
    public Button loginButton;
    public Label registerLabel;
    public TextField usernameTF;
    public PasswordField passwordTF;
    public Label errorLabel;
    boolean flag = false;
    PGConnector pgConnector = null;

    void setMain(Main main){
        this.main = main;
    }

    void setUp() {
        fullNameLabel.setDisable(true);
        fullNameTF.setDisable(true);
        fullNameTF.setVisible(false);
        fullNameLabel.setVisible(false);
        errorLabel.setVisible(false);
    }

    public void Login() throws Exception{
        if(pgConnector == null)
            pgConnector = new PGConnector();
        if(loginButton.getText().equals("Login")) {
            if(pgConnector.verifyLogin(usernameTF.getText(), passwordTF.getText()) == true) {
                System.out.println("Success");
                passwordTF.clear();
                main.setUser(usernameTF.getText());
                main.showMainScreen(true);
            }
            else {
                System.out.println("Failure");
                errorLabel.setVisible(true);
            }
        }
        else if(!(fullNameTF.getText().equals("") || usernameTF.getText().equals("") || passwordTF.getText().equals(""))){
            if(pgConnector.createAccount(fullNameTF.getText(), usernameTF.getText(), passwordTF.getText()) == true) {
                main.setUser(usernameTF.getText());
                main.showMainScreen(true);
                System.out.println("created account");
                passwordTF.clear();
                usernameTF.clear();
                fullNameTF.clear();
            }
            else System.out.println("Failure");
            loginButton.setText("Login");
            registerLabel.setText("Don't have an account?");
        }
    }

   public void goToRegistration() throws Exception {
        if(registerLabel.getText().equals("Login")) {
            fullNameLabel.setDisable(true);
            fullNameTF.setDisable(true);
            fullNameTF.setVisible(false);
            fullNameLabel.setVisible(false);
            loginButton.setText("Login");
            registerLabel.setText("Don't have an account?");
            errorLabel.setVisible(false);
        }
        else {
            fullNameLabel.setDisable(false);
            fullNameTF.setDisable(false);
            fullNameTF.setVisible(true);
            fullNameLabel.setVisible(true);
            loginButton.setText("Register");
            registerLabel.setText("Login");
            usernameTF.clear();
            passwordTF.clear();
            fullNameTF.clear();
            errorLabel.setVisible(false);
        }
   }

    public void changeColor() throws Exception {
        flag = !flag;
        if(flag)
            registerLabel.setTextFill(Color.BLUE);
        else registerLabel.setTextFill(Color.RED);
    }
}
