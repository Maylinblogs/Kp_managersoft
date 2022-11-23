package com.client;

import com.client.utility.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.enums.Roles;
import org.menegment.models.User;

import java.io.IOException;

public class Registration {

    @FXML
    private TextField logn;

    @FXML
    private PasswordField newpassword;

    @FXML
    private PasswordField newpassword2;

    @FXML
    private Label error;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) logn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Авторизация!");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onRegister(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        if (newpassword.getText().equals(newpassword2.getText())) {
            User user = new User();
            user.setLogin(logn.getText());
            user.setPassword(newpassword.getText());
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.REGISTER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            switch ((Action) ClientSocket.getInstance().getObjectInputStream().readObject()) {
                case OK: {
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    ClientSocket.setLocalUser(user);
                    if (user.getRole_user().equals(Roles.ADMIN)) {
                        Stage stage = (Stage) logn.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("AdminContener.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene);
                        stage.setTitle("М А!");
                        stage.setResizable(false);
                        stage.show();
                    } else {
                        if (user.getRole_user().equals(Roles.WORKER)) {
                            Stage stage = (Stage) logn.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("WorkerContener.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                            stage.setScene(scene);
                            stage.setTitle("Р П!");
                            stage.setResizable(false);
                            stage.show();
                        } else {
                            Stage stage = (Stage) logn.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("UserContener.fxml"));
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
        } else {
            error.setStyle("-fx-text-fill: red;");
        }
    }
}
