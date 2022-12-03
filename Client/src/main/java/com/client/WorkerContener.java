package com.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkerContener {

    @FXML
    private Button button;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(WorkerContener.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Авторизация!");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onVacancies(ActionEvent event) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(WorkerContener.class.getResource("VacanciesControllWorker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Ваш Аккаунт!");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onAccount(ActionEvent event) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(WorkerContener.class.getResource("AccountWorker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Управление Заявками!");
        stage.setResizable(false);
        stage.show();
    }


}
