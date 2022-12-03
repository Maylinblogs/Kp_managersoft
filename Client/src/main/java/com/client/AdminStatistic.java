package com.client;

import com.client.utility.ClientSocket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.enums.Roles;
import org.menegment.models.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminStatistic {

    private int i = 0;

    @FXML
    private CategoryAxis graphic2X = new CategoryAxis();

    @FXML
    private NumberAxis graphic2Y = new NumberAxis();
    @FXML
    private BarChart<String, Number> graphic2User = new BarChart(graphic2X, graphic2Y);

    private ObservableList<XYChart.Series> seriesArrayList = FXCollections.observableArrayList();


    @FXML
    void onUpdateGraphic(ActionEvent event) throws IOException, ClassNotFoundException {
        if (i == 0) {
            graphic2X.setLabel("ADMIN,USER,WORKER");
            graphic2Y.setLabel("Value");
        }
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_USER);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                int count = 0;
                int countw = 0;
                ArrayList<User> users = (ArrayList<User>) ClientSocket.getInstance().getObjectInputStream().readObject();
                for (User u : users) {
                    if (u.getRole_user().equals(Roles.USER)) {
                        count++;
                    }
                    if (u.getRole_user().equals(Roles.WORKER)) {
                        countw++;
                    }
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                XYChart.Series dataSeries1 = new XYChart.Series();
                dataSeries1.setName(date.toString());
                dataSeries1.getData().add(new XYChart.Data("USER", count));
                dataSeries1.getData().add(new XYChart.Data("ADMIN", (users.size() - count) - countw));
                dataSeries1.getData().add(new XYChart.Data("WORKER", countw));
                graphic2User.getData().add(dataSeries1);
                break;
            }
            case NO: {
                break;
            }
        }
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        Stage stage = (Stage) graphic2User.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("AdminContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }
}
