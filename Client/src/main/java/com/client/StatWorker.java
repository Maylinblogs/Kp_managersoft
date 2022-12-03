package com.client;

import com.client.utility.ClientSocket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.models.Claim;

import java.io.IOException;
import java.util.ArrayList;

public class StatWorker {


    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    @FXML
    private PieChart pieClaim;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) pieClaim.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(UserClaim.class.getResource("UserContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onUpdate(ActionEvent event) throws IOException, ClassNotFoundException {

        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_CLIAM);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                data = FXCollections.observableArrayList();
                int yes = 0;
                int no = 0;
                int oio = 0;
                ArrayList<Claim> claims = (ArrayList<Claim>) ClientSocket.getInstance().getObjectInputStream().readObject();
                ArrayList<Claim> youClaim = new ArrayList<>();
                for (Claim c : claims) {
                    if (c.getId_worker() == ClientSocket.getLocalUser().getUser_id()) {
                        youClaim.add(c);
                    }
                }
                for (Claim c : youClaim) {
                    if (c.getStatus_claim() == 0) {
                        oio++;
                    } else {
                        if (c.getStatus_claim() == 1) {
                            no++;
                        } else {
                            yes++;
                        }
                    }
                }
                data.add(new PieChart.Data("Принятые", yes));
                data.add(new PieChart.Data("Не принятые", no));
                data.add(new PieChart.Data("не просмотрены ", oio));
                pieClaim.setData(data);
                break;
            }
            case NO: {

                break;
            }
        }
    }

}
