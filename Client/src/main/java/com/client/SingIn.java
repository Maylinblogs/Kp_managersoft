package com.client;


import javafx.scene.control.Label;
import org.menegment.enums.Action;
import org.menegment.enums.Roles;
import org.menegment.models.User;
import com.client.utility.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SingIn {
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label error;

    @FXML
    void SinglIn(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = new User();
        user.setLogin(login.getText());
        user.setPassword(password.getText());
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.LOGIN);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        switch ((Action) ClientSocket.getInstance().getObjectInputStream().readObject()) {
            case OK: {
                user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                if (user.getRole_user().equals(Roles.ADMIN)) {
                    Stage stage = (Stage) login.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(SingIn.class.getResource("AdminContener.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setScene(scene);
                    stage.setTitle("М А!");
                    stage.setResizable(false);
                    stage.show();
                } else {
                    if (user.getRole_user().equals(Roles.WORKER)) {
                        Stage stage = (Stage) login.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(SingIn.class.getResource("WorkerContener.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene);
                        stage.setTitle("Р П!");
                        stage.setResizable(false);
                        stage.show();
                    } else {
                        Stage stage = (Stage) login.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(SingIn.class.getResource("UserContener.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene);
                        stage.setTitle("М П!");
                        stage.setResizable(false);
                        stage.show();
                    }
                }
                ClientSocket.setLocalUser(user);
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }

    @FXML
    void onRegistration(ActionEvent event) throws IOException {
        Stage stage = (Stage) login.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("Registered.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Регистрация!");
        stage.setResizable(false);
        stage.show();
    }
}